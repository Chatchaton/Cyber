package ui.property;

public interface Property<T> extends ReadonlyProperty<T> {
    void setValue(T v);

    void forceSetLocalValue(T v);

    void setValueSilent(T v);
}
