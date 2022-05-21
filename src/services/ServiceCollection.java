package services;

import java.util.HashMap;
import java.util.Map;

public class ServiceCollection {

    private final Map<Class<?>, Object> services = new HashMap<>();

    public <T> T getInstance(Class<T> type) {
        return (T) services.get(type);
    }

    public <T> void setSingleton(Class<T> type, T instance) {
        services.put(type, instance);
    }

}
