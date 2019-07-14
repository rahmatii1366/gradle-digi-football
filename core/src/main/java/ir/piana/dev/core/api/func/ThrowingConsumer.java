package ir.piana.dev.core.api.func;

import io.reactivex.exceptions.Exceptions;

import java.util.function.Consumer;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> extends Consumer<T> {

    void acceptWithException(T t) throws E;

    @Override
    default void accept(T t) {
        try {
            acceptWithException(t);
        } catch (Throwable ex) {
            throw Exceptions.propagate(ex);
        }
    }
}
