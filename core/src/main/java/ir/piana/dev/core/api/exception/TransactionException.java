package ir.piana.dev.core.api.exception;

import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/10/2019 2:08 PM
 **/
public class TransactionException extends BaseRuntimeException {
    public TransactionException(String messageCode) {
        super(messageCode);
    }

    public TransactionException(String messageCode, Map<String, Object> params) {
        super(messageCode, params);
    }

    public TransactionException(Throwable cause, String messageCode) {
        super(cause, messageCode);
    }

    public TransactionException(Throwable cause, String messageCode, Map<String, Object> params) {
        super(cause, messageCode, params);
    }
}
