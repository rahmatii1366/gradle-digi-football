package ir.piana.dev.core.api.exception;

import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 5:50 PM
 **/
public interface PianaException {
    String getMessageCode();
    Map<String, Object> getParams();
}