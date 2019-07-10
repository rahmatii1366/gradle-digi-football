package ir.piana.dev.core.api.exception;

import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/10/2019 2:15 PM
 **/
public class AssertionException extends BaseRuntimeException {
    public AssertionException(String messageCode) {
        super(messageCode);
    }

    public AssertionException(String messageCode, Map<String, Object> params) {
        super(messageCode, params);
    }

    public AssertionException(Throwable cause, String messageCode) {
        super(cause, messageCode);
    }

    public AssertionException(Throwable cause, String messageCode, Map<String, Object> params) {
        super(cause, messageCode, params);
    }
}
