package ir.piana.dev.core.api.exception;

import java.util.Collections;
import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/10/2019 1:28 PM
 **/
public class ApiBaseException extends BaseException {
    private static final int STATUS_CODE = 0;
    private Integer statusCode;

    public ApiBaseException(String messageCode) {
        this(STATUS_CODE, messageCode, Collections.emptyMap());
    }

    public ApiBaseException(String messageCode, Map<String, Object> params) {
        this(STATUS_CODE, messageCode, params);
    }

    public ApiBaseException(Integer statusCode, String messageCode) {
        this(statusCode, messageCode, Collections.emptyMap());
    }

    public ApiBaseException(Integer statusCode, String messageCode, Map<String, Object> params) {
        super(messageCode, params);
        this.statusCode = statusCode;
    }

    public ApiBaseException(Throwable cause, String messageCode) {
        this(STATUS_CODE, cause, messageCode, Collections.emptyMap());
    }

    public ApiBaseException(Integer statusCode, Throwable cause, String messageCode) {
        this(statusCode, cause, messageCode, Collections.emptyMap());
    }

    public ApiBaseException(Throwable cause, String messageCode, Map<String, Object> params) {
        this(STATUS_CODE, cause, messageCode, params);
    }

    public ApiBaseException(Integer statusCode, Throwable cause, String messageCode, Map<String, Object> params) {
        super(cause, messageCode, params);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
