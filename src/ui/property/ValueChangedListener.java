package ui.property;

public interface ValueChangedListener<T> {
    void call(ValueChangedEvent<T> arg);
}
