package ui.property;

import java.util.ArrayList;
import java.util.List;

public class Property<T> implements ReadonlyProperty<T> {


    private final List<ValueChangedListener<T>> valueChangedListeners = new ArrayList<>();
    private T _value;

    public Property() {
    }

    public Property(T initialValue) {
        _value = initialValue;
    }

    @Override
    public boolean addListener(ValueChangedListener<T> listener) {
        return valueChangedListeners.add(listener);
    }

    @Override
    public boolean removeListener(ValueChangedListener<T> listener) {
        return valueChangedListeners.remove(listener);
    }

    @Override
    public T value() {
        return _value;
    }

    public void setValue(T v) {
        var oldValue = _value;
        _value = v;
        var event = new ValueChangedEvent<>(oldValue, _value, this);
        valueChangedListeners.forEach(e -> e.call(event));
    }

    public void setValueSilent(T v) {
        _value = v;
    }

}
