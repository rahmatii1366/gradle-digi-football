package ir.piana.dev.core.api.func;

import io.reactivex.functions.BiConsumer;
import ir.piana.dev.core.api.helper.TransactionHelper;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:44 PM
 **/
@FunctionalInterface
public interface ReadOnlyAction2<T1, T2, E extends Throwable> extends BiConsumer<T1, T2> {

    void executeReadOnly(T1 t1, T2 t2) throws E;

    @Override
    default void accept(T1 t1, T2 t2) {
        TransactionHelper.getBean().executeReadOnly(() -> executeReadOnly(t1, t2));
    }
}
