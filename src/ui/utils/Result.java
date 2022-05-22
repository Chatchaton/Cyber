package ui.utils;

import java.util.Objects;
import java.util.function.Function;

public abstract class Result<T> {

    public static <T> Result<T> run(Function0<T> fun) {
        try {
            return success(fun.apply());
        } catch (Exception e) {
            return failure(e);
        }
    }

    public static <T> Result<T> tryRun(Function0<Result<T>> fun) {
        try {
            return fun.apply();
        } catch (Exception e) {
            return failure(e);
        }
    }

    public T get() {
        var e = new IllegalAccessException();
        throw new RuntimeException(e);
    }

    public T getOrNull() {
        return null;
    }

    public T getOrElse(T value) {
        return value;
    }

    public T getOrElse(Function0<T> fun) {
        return fun.apply();
    }

    public abstract <R> Result<R> map(Function<T, R> mapper);

    public abstract <R> Result<R> flatMap(Function<T, Result<R>> mapper);

    public Result<T> recover(Function<Exception, T> fun) {
        if (this instanceof Failure failure) {
            return Result.run(() -> fun.apply(failure.exception()));
        } else {
            return this;
        }
    }

    public Result<T> recover(Function0<T> fun) {
        return this.recover(e -> fun.apply());
    }

    public Result<T> tryRecover(Function<Exception, Result<T>> fun) {
        if (this instanceof Failure failure) {
            return Result.tryRun(() -> fun.apply(failure.exception()));
        } else {
            return this;
        }
    }

    public Result<T> tryRecover(Function0<Result<T>> fun) {
        return this.tryRecover(e -> fun.apply());
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

        @Override
        public T get() {
            return value;
        }

        @Override
        public T getOrNull() {
            return value;
        }

        @Override
        public T getOrElse(T value) {
            return value;
        }

        @Override
        public T getOrElse(Function0<T> fun) {
            return value;
        }

        @Override
        public <R> Result<R> map(Function<T, R> mapper) {
            return Result.run(() -> mapper.apply(value));
        }

        @Override
        public <R> Result<R> flatMap(Function<T, Result<R>> mapper) {
            try {
                return mapper.apply(value);
            } catch (Exception e) {
                return failure(e);
            }
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

        @Override
        public Failure map(Function mapper) {
            return this;
        }

        @Override
        public Failure flatMap(Function mapper) {
            return this;
        }
    }
}
