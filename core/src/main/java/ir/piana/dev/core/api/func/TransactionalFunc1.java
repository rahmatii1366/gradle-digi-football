package ir.piana.dev.core.api.func;

import io.reactivex.functions.Function;
import ir.piana.dev.core.api.helper.TransactionHelper;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface TransactionalFunc1<T, R, E extends Throwable> extends Function<T, R>, TransactionalFunc<R> {

    R executeInTransaction(T t) throws E;

    @Override
    default R apply(T t) {
        return TransactionHelper.getBean().doWithTransaction(transaction -> checkError(executeInTransaction(t), transaction));
    }
}
