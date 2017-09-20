package org.lizaalert.configs;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AMQPConfiguration {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.vhost}")
    private String vhost;

    @Bean
    public CachingConnectionFactory getConnectionFactory() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        String url = System.getenv("CLOUDAMQP_URL");
        if (url != null) {
            URI uri = new URI(url);
            host = uri.getHost();
            username = uri.getUserInfo().split(":")[0];
            password = uri.getUserInfo().split(":")[1];
            vhost = uri.getPath().substring(1);
        }
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setRequestedHeartBeat(30);
        connectionFactory.setConnectionTimeout(30000);
        return connectionFactory;
    }

}
