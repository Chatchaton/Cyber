package ui.property;

public interface Event<T> {

    Subscription addListener(EventListener<T> listener);

    void removeListener(EventListener<T> listener);
}
