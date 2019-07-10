package ir.piana.dev.core.api.helper;

import freemarker.template.Template;
import ir.piana.dev.core.api.exception.ApiBaseException;
import ir.piana.dev.core.api.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/10/2019 2:45 PM
 **/
@Component
public class ExceptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    public static final String DEFAULT_ERROR_CODE = "ir.piana.dev.core.default-error";
    public static final String DEFAULT_BUSINESS_ERROR_CODE = "ir.piana.dev.core.default-business-error";

    public String getErrorCode(Throwable error) {
        if (error instanceof ApiBaseException) {
            return ((ApiBaseException) error).getMessageCode();
        }
        if (error instanceof BaseException) {
            final String errorCode = ((BaseException) error).getMessageCode();
            return (errorCode != null) ? errorCode : DEFAULT_BUSINESS_ERROR_CODE;
        }
        return DEFAULT_ERROR_CODE;
    }

    public String getErrorMessage(Throwable error) {
        if (error instanceof ApiBaseException) return error.getMessage();
        final Map<String, Object> params = (error instanceof BaseException) ?
                ((BaseException) error).getParams() : Collections.emptyMap();
        return getMessage(getErrorCode(error), params);
    }

    public String getMessage(String messageCode, Map<String, Object> params) {
        try {
            final String message = messageCode;

            Map<String, Object> messageParams = new HashMap<>(params);
            messageParams.put("instance", this);

//            Template messageTemplate = new Template(message, new StringReader(message), freemarkerConfig);
//            String content = FreeMarkerTemplateUtils.processTemplateIntoString(messageTemplate, messageParams);
//            if (!StringUtils.isEmpty(content)) return content;

        } catch (Throwable t) {
            // Do nothing.
        }
//        logger.warn(String.format("No message is found for the messageCode '%s'", messageCode));
        return messageCode;
    }

    public Map<String, Object> getErrorParams(Throwable error) {
        if (error instanceof ApiBaseException) return ((ApiBaseException) error).getParams();
        return (error instanceof BaseException) ? ((BaseException) error).getParams() : Collections.emptyMap();
    }
}
