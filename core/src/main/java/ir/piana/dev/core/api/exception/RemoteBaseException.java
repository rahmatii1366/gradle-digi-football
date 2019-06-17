package ir.piana.dev.core.api.exception;

import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 5:51 PM
 **/
public interface RemoteBaseException extends PianaException {
    String getMessageCode();
    Map<String, Object> getParams();
}