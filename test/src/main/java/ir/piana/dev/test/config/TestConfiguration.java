package ir.piana.dev.test.config;

import com.hazelcast.config.Config;
import ir.piana.dev.core.config.CommonConfiguration;
import ir.piana.dev.core.config.PianaConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/18/2019 2:20 PM
 **/
@PianaConfiguration
public class TestConfiguration extends CommonConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Config hazelCastConfig() {
        Config customerHazelcastConfig = new Config();

        customerHazelcastConfig.setInstanceName("TraderHazelcastService");

        customerHazelcastConfig.getGroupConfig().setName("TraderGroup");
        customerHazelcastConfig.getGroupConfig().setPassword("trader");

        customerHazelcastConfig.getNetworkConfig().setPortAutoIncrement(true);
        customerHazelcastConfig.getNetworkConfig().setPort(10555);
        customerHazelcastConfig.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(true).setMulticastTimeoutSeconds(2);

        /*ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
        managementCenterConfig.setEnabled(true);
        managementCenterConfig.setUrl("http://192.168.1.6:8080/mancenter"); // TODO: 10/17/18 check with hossein what to do with this.are we need this?
        customerHazelcastConfig.setManagementCenterConfig(managementCenterConfig);*/

        customerHazelcastConfig.setProperty("hazelcast.logging.type", "slf4j");

        return customerHazelcastConfig;
    }
}
