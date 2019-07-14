package ir.piana.dev.core.api.helper;

import ir.piana.dev.core.api.exception.ApiBaseException;
import ir.piana.dev.core.api.exception.ApiRuntimeBaseException;
import ir.piana.dev.core.api.exception.BaseRuntimeException;
import ir.piana.dev.core.api.exception.TransactionException;
import ir.piana.dev.core.api.func.Consumer;
import ir.piana.dev.core.api.func.Function;
import ir.piana.dev.core.api.func.Runnable;
import ir.piana.dev.core.api.func.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 3:52 PM
 **/
@Component
public class TransactionHelper {
    private final static Logger logger = LoggerFactory.getLogger(TransactionHelper.class);

    private static final String ACTION_ERROR_CODE = "com.bitex.common.action";
    private static final String ACTION_ERROR_MESSAGE = "Error occurred in execution of the action";
    private static final String FALLBACK_ERROR_CODE = "com.bitex.common.fallback";
    private static final String FALLBACK_ERROR_MESSAGE = "Error occurred in handling failure";

    private static TransactionHelper BEAN;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public static TransactionHelper getBean() {
        return BEAN;
    }

    @PostConstruct
    private void setBean() {
        TransactionHelper.BEAN = this;
    }

    public <E extends Throwable> void executeInTransaction(Runnable<E> action) {
        executeInTransaction(REQUIRES_NEW, action);
    }

    public <R, E extends Throwable> R executeInTransaction(Supplier<R, E> action) {
        return executeInTransaction(REQUIRES_NEW, action);
    }

    public <E extends Throwable> void executeInTransaction(Propagation propagation, Runnable<E> action) {
        doWithTransaction(propagation, toFunction(action));
    }

    public <R, E extends Throwable> R executeInTransaction(Propagation propagation, Supplier<R, E> action) {
        return doWithTransaction(propagation, toFunction(action));
    }

    public <E1 extends Throwable, E2 extends Throwable> void executeInTransaction(Runnable<E1> action, Consumer<Throwable, E2> fallback) {
        executeInTransaction(REQUIRES_NEW, action, REQUIRES_NEW, fallback);
    }

    public <R, E1 extends Throwable, E2 extends Throwable> R executeInTransaction(Runnable<E1> action, Function<Throwable, R, E2> fallback) {
        return executeInTransaction(REQUIRES_NEW, action, REQUIRES_NEW, fallback);
    }

    public <R, E1 extends Throwable, E2 extends Throwable> R executeInTransaction(Supplier<R, E1> action, Consumer<Throwable, E2> fallback) {
        return executeInTransaction(REQUIRES_NEW, action, REQUIRES_NEW, fallback);
    }

    public <E1 extends Throwable, E2 extends Throwable> Object executeInTransaction(Supplier<?, E1> action, Function<Throwable, ?, E2> fallback) {
        return executeInTransaction(REQUIRES_NEW, action, REQUIRES_NEW, fallback);
    }

    public <R, E1 extends Throwable, E2 extends Throwable> R executeInTransaction(Function<TransactionStatus, R, E1> action, Consumer<Throwable, E2> fallback) {
        return executeInTransaction(REQUIRES_NEW, action, REQUIRES_NEW, fallback);
    }

    public <E1 extends Throwable, E2 extends Throwable> Object executeInTransaction(Function<TransactionStatus, ?, E1> action, Function<Throwable, ?, E2> fallback) {
        return executeInTransaction(REQUIRES_NEW, action, REQUIRES_NEW, fallback);
    }

    public <E1 extends Throwable, E2 extends Throwable> void executeInTransaction(Propagation actionPropagation, Runnable<E1> action, Propagation fallbackPropagation, Consumer<Throwable, E2> fallback) {
        executeInTransaction(actionPropagation, toFunction(action), fallbackPropagation, toFunction(fallback));
    }

    @SuppressWarnings("unchecked")
    public <R, E1 extends Throwable, E2 extends Throwable> R executeInTransaction(Propagation actionPropagation, Runnable<E1> action, Propagation fallbackPropagation, Function<Throwable, R, E2> fallback) {
        return (R) executeInTransaction(actionPropagation, toFunction(action), fallbackPropagation, fallback);
    }

    @SuppressWarnings("unchecked")
    public <R, E1 extends Throwable, E2 extends Throwable> R executeInTransaction(Propagation actionPropagation, Supplier<R, E1> action, Propagation fallbackPropagation, Consumer<Throwable, E2> fallback) {
        return (R) executeInTransaction(actionPropagation, action, fallbackPropagation, toFunction(fallback));
    }

    public <E1 extends Throwable, E2 extends Throwable> Object executeInTransaction(Propagation actionPropagation, Supplier<?, E1> action, Propagation fallbackPropagation, Function<Throwable, ?, E2> fallback) {
        return executeInTransaction(actionPropagation, toFunction(action), fallbackPropagation, fallback);
    }

    @SuppressWarnings("unchecked")
    public <R, E1 extends Throwable, E2 extends Throwable> R executeInTransaction(Propagation actionPropagation, Function<TransactionStatus, R, E1> action, Propagation fallbackPropagation, Consumer<Throwable, E2> fallback) {
        return (R) executeInTransaction(actionPropagation, action, fallbackPropagation, toFunction(fallback));
    }

    public <E1 extends Throwable, E2 extends Throwable> Object executeInTransaction(Propagation actionPropagation, Function<TransactionStatus, ?, E1> action, Propagation fallbackPropagation, Function<Throwable, ?, E2> fallback) {
        final Object actionResult = executeAction(actionPropagation, action);
        if (!(actionResult instanceof Throwable)) return actionResult;

        final Object fallbackResult = executeFallback(fallbackPropagation, fallback, (Throwable) actionResult);
        if (fallbackResult instanceof Throwable) {
            logger.error(FALLBACK_ERROR_MESSAGE, (Throwable) fallbackResult);
            propagateException((Throwable) fallbackResult, FALLBACK_ERROR_CODE);
        }
        return fallbackResult;
    }

    public <R, E extends Throwable> R doWithTransaction(Function<TransactionStatus, R, E> action) {
        return doWithTransaction(REQUIRES_NEW, action);
    }

    @SuppressWarnings("unchecked")
    public <R, E extends Throwable> R doWithTransaction(
            Propagation propagation, Function<TransactionStatus, R, E> action) {
        final Object actionResult = executeAction(propagation, action);
        if (actionResult instanceof Throwable) {
            logger.error(ACTION_ERROR_MESSAGE, (Throwable) actionResult);
            propagateException((Throwable) actionResult, ACTION_ERROR_CODE);
        }
        return (R) actionResult;
    }

    public <E extends Throwable> void executeReadOnly(Runnable<E> action) {
        executeReadOnly(toSupplier(action));
    }

    @SuppressWarnings("unchecked")
    public <R, E extends Throwable> R executeReadOnly(Supplier<R, E> action) {
        final Object actionResult = readOnlyAction(action);
        if (actionResult instanceof Throwable) {
            logger.error(ACTION_ERROR_MESSAGE, actionResult);
            propagateException((Throwable) actionResult, ACTION_ERROR_CODE);
        }
        return (R) actionResult;
    }

    public <E1 extends Throwable, E2 extends Throwable> void executeReadOnly(Runnable<E1> action, Consumer<Throwable, E2> fallback) {
        executeReadOnly(toSupplier(action), toFunction(fallback));
    }

    @SuppressWarnings("unchecked")
    public <R, E1 extends Throwable, E2 extends Throwable> R executeReadOnly(Runnable<E1> action, Function<Throwable, R, E2> fallback) {
        return (R) executeReadOnly(toSupplier(action), fallback);
    }

    @SuppressWarnings("unchecked")
    public <R, E1 extends Throwable, E2 extends Throwable> R executeReadOnly(Supplier<R, E1> action, Consumer<Throwable, E2> fallback) {
        return (R) executeReadOnly(action, toFunction(fallback));
    }

    @SuppressWarnings("unchecked")
    public <E1 extends Throwable, E2 extends Throwable> Object executeReadOnly(Supplier<?, E1> action, Function<Throwable, ?, E2> fallback) {
        final Object actionResult = readOnlyAction(action);
        if (!(actionResult instanceof Throwable)) return actionResult;

        final Object fallbackResult = readOnlyFallback(fallback, (Throwable) actionResult);
        if (fallbackResult instanceof Throwable) {
            logger.error(FALLBACK_ERROR_MESSAGE, (Throwable) fallbackResult);
            propagateException((Throwable) fallbackResult, FALLBACK_ERROR_CODE);
        }
        return fallbackResult;
    }

    private <E extends Throwable> Object executeAction(
            Propagation propagation, Function<TransactionStatus, ?, E> action) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(
                transactionManager);
        transactionTemplate.setPropagationBehavior(propagation.value());
        return transactionTemplate.execute(transaction ->
                safeExecute(transaction, action, transaction));
    }

    private <E extends Throwable> Object executeFallback(Propagation propagation, Function<Throwable, ?, E> fallback, Throwable cause) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(propagation.value());
        return transactionTemplate.execute(transaction -> safeExecute(transaction, fallback, cause));
    }

    private <T, E extends Throwable> Object safeExecute(
            TransactionStatus transaction,
            Function<T, ?, E> function,
            T functionArgument) {
        try {
            final Object result = function.apply(functionArgument);
            transaction.flush();
            return result;
        } catch (Throwable t) {
            transaction.setRollbackOnly();
            return t;
        }
    }

    private <E extends Throwable> Object readOnlyAction(Supplier<?, E> action) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(REQUIRES_NEW.value());
        transactionTemplate.setReadOnly(true);

        return transactionTemplate.execute(transaction -> {
            try {
                return action.get();
            } catch (Throwable t) {
                return t;
            } finally {
                transaction.setRollbackOnly();
            }
        });
    }

    private <E extends Throwable> Object readOnlyFallback(Function<Throwable, ?, E> fallback, Throwable cause) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(REQUIRES_NEW.value());
        transactionTemplate.setReadOnly(true);

        return transactionTemplate.execute(transaction -> {
            try {
                return fallback.apply(cause);
            } catch (Throwable t) {
                return t;
            } finally {
                transaction.setRollbackOnly();
            }
        });
    }

    private void propagateException(Throwable cause, String defaultErrorCode) {
        if (cause instanceof BaseRuntimeException) {
            throw (BaseRuntimeException) cause;
        }
        if (cause instanceof ApiBaseException) {
            final ApiBaseException remoteException = (ApiBaseException) cause;
            throw new ApiRuntimeBaseException(remoteException, remoteException.getMessageCode());
        }
        if (cause instanceof ApiBaseException) {
            final ApiBaseException baseException = (ApiBaseException) cause;
            throw new TransactionException(baseException, baseException.getMessageCode(), baseException.getParams());
        }
        throw new TransactionException(cause, defaultErrorCode);
    }

    private <E extends Throwable> Supplier<?, E> toSupplier(Runnable<E> runnable) {
        return () -> {
            runnable.run();
            return null;
        };
    }

    private <E extends Throwable> Function<TransactionStatus, ?, E> toFunction(Runnable<E> runnable) {
        return argument -> {
            runnable.run();
            return null;
        };
    }

    private <R, E extends Throwable> Function<TransactionStatus, R, E> toFunction(Supplier<R, E> supplier) {
        return argument -> supplier.get();
    }

    private <T, E extends Throwable> Function<T, ?, E> toFunction(Consumer<T, E> consumer) {
        return argument -> {
            consumer.accept(argument);
            return null;
        };
    }
}
