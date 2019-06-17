package ir.piana.dev.user;

import io.vertx.core.Vertx;
import ir.piana.dev.user.server.api.service.MainApiVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 6:08 PM
 **/
@SpringBootApplication(scanBasePackages = "ir.piana.dev")
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private Vertx vertx;

    @Autowired
    private MainApiVerticle mainApiVerticle;

    public static void main(String[] args) {
        System.setProperty("spring.application.name", MainApiVerticle.SERVICE_NAME);
        SpringApplication.run(UserService.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        vertx.deployVerticle(mainApiVerticle, event -> {
            if (event.succeeded()) {
            } else {
                logger.error(
                        "FATAL_ERROR",
                        "There is a fatal error in deploying customer admin verticle:",
                        event.cause());
            }
        });
    }

    @EventListener(ContextClosedEvent.class)
    public void shutDown() {
        vertx.undeploy(mainApiVerticle.deploymentID(), event -> {
                    if (event.failed()) {
                        logger.error("FATAL_ERROR",
                                "There is a fatal error in undeploying customer admin verticle:",
                                event.cause());
                        throw new RuntimeException(
                                "com.bitex.trader.admin.undeploy-verticle",
                                event.cause());
                    }
                }
        );
    }
}