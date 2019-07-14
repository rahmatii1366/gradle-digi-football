package ir.piana.dev.core.api.func;

import ir.piana.dev.core.api.helper.TransactionHelper;
import rx.functions.Action1;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface ReadOnlyAction1<T, E extends Throwable> extends Action1<T> {

    void executeReadOnly(T t) throws E;

    @Override
    default void call(T t) {
        TransactionHelper.getBean().executeReadOnly(() -> executeReadOnly(t));
    }
}
