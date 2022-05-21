package ui.utils;

import java.util.Objects;

public abstract class Result<T> {

    public static <T> Result<T> Run(Function0<T> fun) {
        try {
            return success(fun.apply());
        } catch (Exception e) {
            return failure(e);
        }
    }

    public static <T> Success<T> success(T value) {
        return new Success<>(value);
    }

    public static Failure failure(Exception exception) {
        return new Failure(exception);
    }

    public static final class Success<T> extends Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }

        public T value() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Success) obj;
            return Objects.equals(this.value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Success[" +
                "value=" + value + ']';
        }
    }

    public static final class Failure extends Result {
        private final Exception exception;

        public Failure(Exception exception) {
            this.exception = exception;
        }

        public Exception exception() {
            return exception;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Failure) obj;
            return Objects.equals(this.exception, that.exception);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exception);
        }

        @Override
        public String toString() {
            return "Failure[" +
                "exception=" + exception + ']';
        }
    }
}
