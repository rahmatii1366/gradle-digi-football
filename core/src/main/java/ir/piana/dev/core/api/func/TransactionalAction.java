package ir.piana.dev.core.api.func;

import io.reactivex.functions.Action;
import ir.piana.dev.core.api.helper.TransactionHelper;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface TransactionalAction<E extends Throwable> extends Action {

    void executeInTransaction() throws E;

    @Override
    default void run() {
        TransactionHelper.getBean().executeInTransaction(this::executeInTransaction);
    }
}
