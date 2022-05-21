package ui.property;

public interface Property<T> extends ReadonlyProperty<T> {
    void setValue(T v) throws IllegalAccessException;

    void setValueSilent(T v);
}
