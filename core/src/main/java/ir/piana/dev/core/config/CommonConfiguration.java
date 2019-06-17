package ir.piana.dev.core.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaMapper;
import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 5:53 PM
 **/
public abstract class CommonConfiguration {

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    public ObjectMapper objectMapper() {
        JodaMapper jodaMapper = new JodaMapper();
        jodaMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        return jodaMapper;
    }
}
