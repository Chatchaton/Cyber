package ui.utils;

public interface ThrowingFunction<T, R> {
    R apply(T arg) throws Exception;
}

