package ir.piana.dev.core.api.exception;

import java.util.Collections;
import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/10/2019 1:28 PM
 **/
public class ApiRuntimeBaseException extends BaseRuntimeException {
    private static final int STATUS_CODE = 0;
    private Integer statusCode;

    public ApiRuntimeBaseException(String messageCode) {
        this(STATUS_CODE, messageCode, Collections.emptyMap());
    }

    public ApiRuntimeBaseException(String messageCode, Map<String, Object> params) {
        this(STATUS_CODE, messageCode, params);
    }

    public ApiRuntimeBaseException(Integer statusCode, String messageCode) {
        this(statusCode, messageCode, Collections.emptyMap());
    }

    public ApiRuntimeBaseException(Integer statusCode, String messageCode, Map<String, Object> params) {
        super(messageCode, params);
        this.statusCode = statusCode;
    }

    public ApiRuntimeBaseException(Throwable cause, String messageCode) {
        this(STATUS_CODE, cause, messageCode, Collections.emptyMap());
    }

    public ApiRuntimeBaseException(Integer statusCode, Throwable cause, String messageCode) {
        this(statusCode, cause, messageCode, Collections.emptyMap());
    }

    public ApiRuntimeBaseException(Throwable cause, String messageCode, Map<String, Object> params) {
        this(STATUS_CODE, cause, messageCode, params);
    }

    public ApiRuntimeBaseException(Integer statusCode, Throwable cause, String messageCode, Map<String, Object> params) {
        super(cause, messageCode, params);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
