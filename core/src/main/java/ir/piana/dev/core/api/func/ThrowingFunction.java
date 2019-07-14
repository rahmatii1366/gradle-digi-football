package ir.piana.dev.core.api.func;

import io.reactivex.exceptions.Exceptions;

import java.util.function.Function;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> extends Function<T, R> {

    R applyWithException(T t) throws E;

    @Override
    default R apply(T t) {
        try {
            return applyWithException(t);
        } catch (Throwable ex) {
            throw Exceptions.propagate(ex);
        }
    }

    default ThrowingConsumer<T, E> toThrowingConsumer() {
        return this::applyWithException;
    }
}
