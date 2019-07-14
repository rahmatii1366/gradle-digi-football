package ir.piana.dev.core.api.func;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface Supplier<R, E extends Throwable> {

    R get() throws E;

    default Runnable<E> toRunnable() {
        return this::get;
    }
}
