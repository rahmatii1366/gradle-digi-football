package ir.piana.dev.core.api.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/10/2019 1:46 PM
 **/
public class BaseRuntimeException extends RuntimeException implements PianaException {
    private final String messageCode;
    private final Map<String, Object> params = new HashMap<>();

    public BaseRuntimeException(String messageCode) {
        this(messageCode, Collections.emptyMap());
    }

    public BaseRuntimeException(String messageCode, Map<String, Object> params) {
        super(messageCode);
        this.messageCode = messageCode;
        this.params.putAll(params);
    }

    public BaseRuntimeException(Throwable cause, String messageCode) {
        this(cause, messageCode, Collections.emptyMap());
    }

    public BaseRuntimeException(Throwable cause, String messageCode, Map<String, Object> params) {
        super(messageCode, cause);
        this.messageCode = messageCode;
        this.params.putAll(params);
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }
}
