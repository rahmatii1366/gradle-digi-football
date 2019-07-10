package ir.piana.dev.core.api.exception;

import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/10/2019 2:08 PM
 **/
public class IllegalArgumentException extends BaseRuntimeException {
    public IllegalArgumentException(String messageCode) {
        super(messageCode);
    }

    public IllegalArgumentException(String messageCode, Map<String, Object> params) {
        super(messageCode, params);
    }

    public IllegalArgumentException(Throwable cause, String messageCode) {
        super(cause, messageCode);
    }

    public IllegalArgumentException(Throwable cause, String messageCode, Map<String, Object> params) {
        super(cause, messageCode, params);
    }
}
