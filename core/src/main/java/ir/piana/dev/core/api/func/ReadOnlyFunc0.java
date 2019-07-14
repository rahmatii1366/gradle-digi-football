package ir.piana.dev.core.api.func;

import ir.piana.dev.core.api.helper.TransactionHelper;

import java.util.concurrent.Callable;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface ReadOnlyFunc0<R, E extends Throwable> extends Callable<R> {

    R executeReadOnly() throws E;

    @Override
    default R call() {
        return TransactionHelper.getBean().executeReadOnly(this::executeReadOnly);
    }
}
