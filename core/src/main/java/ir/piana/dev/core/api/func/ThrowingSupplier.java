package ir.piana.dev.core.api.func;


import io.reactivex.exceptions.Exceptions;

import java.util.function.Supplier;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface ThrowingSupplier<R, E extends Throwable> extends Supplier<R> {

    R getWithException() throws E;

    @Override
    default R get() {
        try {
            return getWithException();
        } catch (Throwable ex) {
            throw Exceptions.propagate(ex);
        }
    }

    default ThrowingRunnable<E> toThrowingRunnable() {
        return this::getWithException;
    }
}
