package ir.piana.dev.core.api.func;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface Function<T, R, E extends Throwable> {

    R apply(T t) throws E;

    default Consumer<T, E> toConsumer() {
        return this::apply;
    }
}
