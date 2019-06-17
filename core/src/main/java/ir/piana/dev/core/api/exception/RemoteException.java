package ir.piana.dev.core.api.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 5:51 PM
 **/
public class RemoteException extends Exception implements RemoteBaseException {
    private final String messageCode;
    private final Map<String, Object> params = new HashMap<>();

    public RemoteException(String messageCode) {
        super(messageCode);
        this.messageCode = messageCode;
    }

    public RemoteException(String messageCode, Map<String, Object> params) {
        super(messageCode);
        this.messageCode = messageCode;
        this.params.putAll(params);
    }

    public RemoteException(Throwable cause, String messageCode) {
        super(messageCode, cause);
        this.messageCode = messageCode;
    }

    public RemoteException(Throwable cause, String messageCode, Map<String, Object> params) {
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