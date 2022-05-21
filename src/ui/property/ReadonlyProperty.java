package ui.property;


public interface ReadonlyProperty<T> {


    boolean addListener(ValueChangedListener<T> listener);

    boolean removeListener(ValueChangedListener<T> listener);

    T value();
}
