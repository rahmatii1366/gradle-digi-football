package ir.piana.dev.core.api.func;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface Consumer<T, E extends Throwable> {

    void accept(T t) throws E;

}