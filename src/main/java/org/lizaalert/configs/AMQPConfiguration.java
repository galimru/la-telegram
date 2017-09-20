package org.lizaalert.configs;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AMQPConfiguration {

    @Bean
    public ConnectionFactory getConnectionFactory() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        String url = System.getenv("CLOUDAMQP_URL");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(url);
        return connectionFactory;
    }

}
