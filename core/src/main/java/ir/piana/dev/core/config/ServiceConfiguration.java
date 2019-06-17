package ir.piana.dev.core.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 5:53 PM
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
@EnableAsync
@EnableCaching
@EnableRedisRepositories(basePackages = "ir.piana.dev.**.core.redis.repository")
@EnableJpaRepositories(basePackages = "ir.piana.dev.**.core.repository")
@EntityScan("ir.piana.dev")
@EnableTransactionManagement
@EnableConfigurationProperties
@ConfigurationProperties
@EnableScheduling
@EnableBatchProcessing
public @interface ServiceConfiguration {
}
