package org.lizaalert.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.botan.sdk.Botan;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.lizaalert.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class BotanService {

    private final Log log = LogFactory.getLog(BotanService.class);

    @Value("${org.lizaalert.botan.token}")
    private String botanToken;

    public void track(User user, String name, Object message) {
        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {
            httpClient.start();
            Botan botan = new Botan(httpClient, new ObjectMapper());
            botan.track(botanToken, String.valueOf(user.getUserId()), message, name).get();
        } catch (IOException | InterruptedException | ExecutionException e) {
            log.error("Error on sending message to botan", e);
        }
    }

}
