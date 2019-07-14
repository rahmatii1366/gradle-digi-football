package ir.piana.dev.core.api.func;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
public interface TransactionalFunc<R> {

    default R checkError(R result, TransactionStatus transaction) {
        try {
            if (result instanceof Completable) {
                ((Completable) result).test().assertNoErrors();
            } else if (result instanceof Single) {
                ((Single) result).test().assertNoErrors();
            } else if (result instanceof Observable) {
                ((Observable) result).test().assertNoErrors();
            }
        } catch (AssertionError e) {
            transaction.setRollbackOnly();
        }
        return result;
    }
}
