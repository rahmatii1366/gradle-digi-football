package ir.piana.dev.core.api.func;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface Runnable<E extends Throwable> {

    void run() throws E;

    default Consumer<?, E> toConsumer() {
        return argument -> this.run();
    }
}
