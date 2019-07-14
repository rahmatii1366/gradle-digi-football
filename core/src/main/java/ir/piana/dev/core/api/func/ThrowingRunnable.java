package ir.piana.dev.core.api.func;


import io.reactivex.exceptions.Exceptions;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface ThrowingRunnable<E extends Throwable> extends Runnable {

    void runWithException() throws E;

    @Override
    default void run() {
        try {
            runWithException();
        } catch (Throwable ex) {
            throw Exceptions.propagate(ex);
        }
    }

    default ThrowingConsumer<?, E> toThrowingConsumer() {
        return argument -> this.runWithException();
    }
}
