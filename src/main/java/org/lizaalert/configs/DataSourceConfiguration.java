package org.lizaalert.configs;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceConfiguration {

    private static final String PROTOCOL = "jdbc:postgresql";

    @Bean
    public DataSource getDataSource(DataSourceProperties properties) throws URISyntaxException {
        String databaseUrl = System.getenv("DATABASE_URL");

        String url = properties.getUrl();
        String username = properties.getUsername();
        String password = properties.getPassword();

        if (databaseUrl != null) {
            URI uri = new URI(databaseUrl);
            url = String.format("%s://%s:%d%s", PROTOCOL, uri.getHost(), uri.getPort(), uri.getPath());
            username = uri.getUserInfo().split(":")[0];
            password = uri.getUserInfo().split(":")[1];
        }

        DataSourceBuilder builder = DataSourceBuilder.create();
        if (!StringUtils.isEmpty(url)) {
            builder.url(url);
        }
        if (!StringUtils.isEmpty(username)) {
            builder.username(username);
        }
        if (!StringUtils.isEmpty(password)) {
            builder.password(password);
        }
        return builder.build();
    }

}
