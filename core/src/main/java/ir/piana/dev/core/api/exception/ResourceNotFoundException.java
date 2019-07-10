package ir.piana.dev.core.api.exception;

import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/10/2019 2:08 PM
 **/
public class ResourceNotFoundException extends BaseRuntimeException {
    public ResourceNotFoundException(String messageCode) {
        super(messageCode);
    }

    public ResourceNotFoundException(String messageCode, Map<String, Object> params) {
        super(messageCode, params);
    }

    public ResourceNotFoundException(Throwable cause, String messageCode) {
        super(cause, messageCode);
    }

    public ResourceNotFoundException(Throwable cause, String messageCode, Map<String, Object> params) {
        super(cause, messageCode, params);
    }
}
