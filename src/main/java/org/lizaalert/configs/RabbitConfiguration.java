package org.lizaalert.configs;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitConfiguration {

    @Bean
    public CachingConnectionFactory getConnectionFactory(RabbitProperties properties) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        String cloudamqpUrl = System.getenv("CLOUDAMQP_URL");

        String host = properties.getHost();
        String username = properties.getUsername();
        String password = properties.getPassword();
        String virtualHost = properties.getVirtualHost();

        if (cloudamqpUrl != null) {
            URI uri = new URI(cloudamqpUrl);
            host = uri.getHost();
            username = uri.getUserInfo().split(":")[0];
            password = uri.getUserInfo().split(":")[1];
            virtualHost = uri.getPath().substring(1);
        }

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        if (!StringUtils.isEmpty(host)) {
            connectionFactory.setHost(host);
        }
        if (!StringUtils.isEmpty(username)) {
            connectionFactory.setUsername(username);
        }
        if (!StringUtils.isEmpty(password)) {
            connectionFactory.setPassword(password);
        }
        if (!StringUtils.isEmpty(virtualHost)) {
            connectionFactory.setVirtualHost(virtualHost);
        }
        return connectionFactory;
    }

}
