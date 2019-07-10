package ir.piana.dev.core.api.swagger;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import ir.piana.dev.core.api.dto.ErrorDto;
import ir.piana.dev.core.api.exception.ApiBaseException;
import ir.piana.dev.core.api.exception.AssertionException;
import ir.piana.dev.core.api.exception.ResourceNotFoundException;
import ir.piana.dev.core.api.exception.TransactionException;
import ir.piana.dev.core.api.helper.ExceptionHelper;
import ir.piana.dev.core.vertx.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.LinkedHashMap;
import java.util.Optional;

import static com.github.phiz71.vertx.swagger.router.SwaggerRouter.CUSTOM_STATUS_CODE_HEADER_KEY;
import static org.springframework.core.NestedExceptionUtils.getRootCause;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 5:52 PM
 **/
public abstract class BaseApiVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(BaseApiVerticle.class);

    protected static final String INTERNAL_ERROR_STATUS_CODE = "500";
    protected static final String NOT_FOUND_STATUS_CODE = "404";
    protected static final String BAD_REQUEST_STATUS_CODE = "400";

    @Autowired
    private ExceptionHelper exceptionHelper;

    protected final void registerConsumer(
            String httpMethod, String serviceId, Callback service) {
        final String method = httpMethod.toUpperCase();
        vertx.eventBus().<JsonObject>consumer(serviceId).handler(message -> {
            logger.debug(String.format("'%s %s' is called", method, serviceId));
            final Long startTime = System.currentTimeMillis();
            try {
                service.execute(message, serviceId, method, startTime);
            } catch (Throwable t) {
                handleException(message, serviceId, method, t, startTime);
            }
        });
    }

    protected final void logCompletion(
            String serviceId, String httpMethod, Long startTime) {
        final Long executionTime = System.currentTimeMillis() - startTime;
        logger.info(String.format("The '%s %s' is executed successfully in '%d' millisecond",
                httpMethod, serviceId, executionTime));
    }

    protected final void handleException(
            Message<JsonObject> message,
            String serviceId, String httpMethod,
            Throwable userCause, Long startTime) {
        userCause = (userCause instanceof HystrixRuntimeException) ? userCause.getCause() : userCause;
        /*
         ** do action later
         */
        final ErrorDto errorDto = new ErrorDto(
                exceptionHelper.getErrorCode(userCause),
                exceptionHelper.getErrorMessage(userCause),
                exceptionHelper.getErrorParams(userCause));

//        final ErrorDto errorDto = new ErrorDto("500", "internal server error", new LinkedHashMap());

        final Throwable mainCause = Optional.ofNullable(getRootCause(userCause)).orElse(userCause);
        final Long executionTime = System.currentTimeMillis() - startTime;
        logger.error(String.format("Error occurred during execution of '%s %s' in '%d' millisecond: ",
                httpMethod, serviceId, executionTime), mainCause);

        final DeliveryOptions options = new DeliveryOptions();
        options.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String statusCode = INTERNAL_ERROR_STATUS_CODE;
        if(userCause instanceof TransactionException) {
            statusCode = INTERNAL_ERROR_STATUS_CODE;
        } else if (userCause instanceof IllegalArgumentException ||
                userCause instanceof AssertionException) {
            statusCode = BAD_REQUEST_STATUS_CODE;
        } else if (userCause instanceof ResourceNotFoundException) {
            statusCode = NOT_FOUND_STATUS_CODE;
        } else if (userCause instanceof ApiBaseException) {
            statusCode = ((ApiBaseException) userCause).getStatusCode().toString();
        }

        options.addHeader(CUSTOM_STATUS_CODE_HEADER_KEY, statusCode);

        message.reply(Json.encode(errorDto), options);
    }

    @FunctionalInterface
    protected interface Callback {
        void execute(
                Message<JsonObject> message,
                String serviceId, String httpMethod, Long startTime)
                throws Exception;
    }
}
