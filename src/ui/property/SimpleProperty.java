package ui.property;

import java.util.ArrayList;
import java.util.List;

public class SimpleProperty<T> implements Property<T> {
    private final List<ValueChangedListener<T>> valueChangedListeners = new ArrayList<>();
    private T _value;
    private Subscription boundPropertySubscription;
    private Property<T> boundProperty;

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

    @Override
    public void setValue(T v) {
        if (this.boundProperty != null) {
            this.boundProperty.setValue(v);
        } else {
            var oldValue = _value;
            _value = v;
            var event = new ValueChangedEvent<>(oldValue, _value, this);
            notifyListeners(event);
        }
    }

    private void notifyListeners(ValueChangedEvent<T> event) {
        valueChangedListeners.forEach(e -> e.call(event));
    }

    @Override
    public void setValueSilent(T v) {
        _value = v;
    }

    public void bind(Property<T> property) {
        this.unbind();
        this.boundProperty = property;
        this.boundPropertySubscription = property.addListener(this::onBoundPropertyChanged);
    }

    public void unbind() {
        if (this.boundProperty != null) {
            this.boundPropertySubscription.unsubscribe();
            this.boundPropertySubscription = null;
            this.boundProperty = null;
        }
    }

    private void onBoundPropertyChanged(ValueChangedEvent<T> event) {
        setValueSilent(event.newValue());
        this.notifyListeners(event);
    }

}
