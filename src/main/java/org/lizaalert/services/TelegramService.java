package org.lizaalert.services;

import com.github.galimru.telegram.model.SendMessageRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramService {

    private final Log log = LogFactory.getLog(ForumChecker.class);

    private static final String SEND_MESSAGE_METHOD = "sendMessage";

    @Value("${org.lizaalert.telegram.token}")
    private String telegramToken;

    @Value("${org.lizaalert.telegram.url}")
    private String telegramUrl;

    @Autowired private RestTemplate restTemplate;

    public void sendMessage(SendMessageRequest request) {
        log.debug(String.format("Send message to chat, message %s", request));
        String url = String.format(telegramUrl, telegramToken, SEND_MESSAGE_METHOD);
        try {
            restTemplate.postForLocation(url, request);
        } catch (HttpStatusCodeException e) {
            if (e instanceof HttpClientErrorException) {
                log.debug(e.getResponseBodyAsString());
            }
            log.error("Error on sending message", e);
        }
    }

    public void sendPhoto() {

    }
}
