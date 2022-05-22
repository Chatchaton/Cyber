package ui.property;

import java.util.ArrayList;
import java.util.List;

public class SimpleEvent<T> implements Event<T> {
    private final List<EventListener<T>> listeners = new ArrayList<>();

    public Subscription addListener(EventListener<T> listener) {
        listeners.add(listener);
        return () -> {
            removeListener(listener);
        };
    }

    public void removeListener(EventListener<T> listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(T arg) {
        listeners.forEach(e -> e.apply(arg));
    }
}
