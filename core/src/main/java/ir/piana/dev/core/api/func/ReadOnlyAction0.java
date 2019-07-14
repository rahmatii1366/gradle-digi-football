package ir.piana.dev.core.api.func;

import ir.piana.dev.core.api.helper.TransactionHelper;
import rx.functions.Action0;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface ReadOnlyAction0<E extends Throwable> extends Action0 {

    void executeReadOnly() throws E;

    @Override
    default void call() {
        TransactionHelper.getBean().executeReadOnly(this::executeReadOnly);
    }
}
