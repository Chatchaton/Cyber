package ui.property;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SimpleProperty<T> implements Property<T> {

    private Binding<T> binding;

    @Override
    public void setValue(T v) throws IllegalAccessException {
        if (this.binding instanceof SimpleBinding<T> binding) {
            binding.property().setValue(v);
        } else if (this.binding instanceof SimpleProperty.MappedBinding<T> binding) {
            throw new IllegalAccessException();
        } else {
            var oldValue = _value;
            _value = v;
            var event = new ValueChangedEvent<>(oldValue, _value, this);
            notifyListeners(event);
        }
    }

    public void bind(Property<T> property) {
        this.unbind();
        this.binding = new SimpleBinding<>(property, property.addListener(this::onBoundPropertyChanged));
    }

    private final List<ValueChangedListener<T>> valueChangedListeners = new ArrayList<>();
    private T _value;

    public <A> void bind(Property<A> property, Function<A, T> mapper) {
        bind(property, mapper, false);
    }

    public SimpleProperty() {
    }

    public SimpleProperty(T initialValue) {
        _value = initialValue;
    }

    @Override
    public Subscription addListener(ValueChangedListener<T> listener) {
        valueChangedListeners.add(listener);
        return new Subscription() {
            @Override
            public void unsubscribe() {
                valueChangedListeners.remove(listener);
            }
        };
    }

    @Override
    public void removeListener(ValueChangedListener<T> listener) {
        valueChangedListeners.remove(listener);
    }

    @Override
    public T value() {
        return _value;
    }

    public <A> void bind(Property<A> property, Function<A, T> mapper, boolean mapNull) {
        this.unbind();
        this.binding = new MappedBinding<>(property, property.addListener(e -> {
            var oldValue = this._value;
            var mappedValue = e.newValue() != null || mapNull ? mapper.apply(e.newValue()) : null;
            var newEvent = new ValueChangedEvent<>(oldValue, mappedValue, this);
            this.setValueSilent(mappedValue);
            this.onBoundPropertyChanged(newEvent);
        }));
    }

    private void notifyListeners(ValueChangedEvent<T> event) {
        valueChangedListeners.forEach(e -> e.call(event));
    }

    @Override
    public void setValueSilent(T v) {
        _value = v;
    }

    public void unbind() {
        if (this.binding != null) {
            this.binding.subscription().unsubscribe();
            this.binding = null;
        }
    }

    private void onBoundPropertyChanged(ValueChangedEvent<T> event) {
        setValueSilent(event.newValue());
        var newEvent = new ValueChangedEvent<T>(event.oldValue(), event.newValue(), this);
        this.notifyListeners(newEvent);
    }

    private interface Binding<T> {
        Subscription subscription();
    }

    private record SimpleBinding<T>(Property<T> property, Subscription subscription) implements Binding<T> {
    }

    private record MappedBinding<T>(ReadonlyProperty<?> property, Subscription subscription) implements Binding<T> {
    }

}
