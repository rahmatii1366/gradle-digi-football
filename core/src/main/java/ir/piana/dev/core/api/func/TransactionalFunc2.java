package ir.piana.dev.core.api.func;

import io.reactivex.functions.BiFunction;
import ir.piana.dev.core.api.helper.TransactionHelper;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface TransactionalFunc2<T1, T2, R, E extends Throwable> extends BiFunction<T1, T2, R>, TransactionalFunc<R> {

    R executeInTransaction(T1 t1, T2 t2) throws E;

    @Override
    default R apply(T1 t1, T2 t2) {
        return TransactionHelper.getBean().doWithTransaction(transaction -> checkError(executeInTransaction(t1, t2), transaction));
    }
}
