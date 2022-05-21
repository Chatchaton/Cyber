package ui.property;


public interface ReadonlyProperty<T> {


    Subscription addListener(ValueChangedListener<T> listener);

    void removeListener(ValueChangedListener<T> listener);

    T value();
}

