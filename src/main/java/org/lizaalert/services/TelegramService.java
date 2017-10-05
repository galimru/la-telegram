package org.lizaalert.services;

import com.github.galimru.telegram.actions.GenericRequest;
import com.github.galimru.telegram.actions.SendMessage;
import com.github.galimru.telegram.model.Update;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramService {

    private final Log log = LogFactory.getLog(TelegramService.class);

    private static final String SEND_MESSAGE_METHOD = "sendMessage";

    @Value("${org.lizaalert.telegram.token}")
    private String telegramToken;

    @Value("${org.lizaalert.telegram.url}")
    private String telegramUrl;

    @Autowired private RestTemplate restTemplate;

    public void execute(GenericRequest request) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("Send request to telegram, request %s", request));
        }
        try {
            if (request instanceof SendMessage) {
                sendMessage((SendMessage) request);
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error on sending request to telegram", e);
            if (log.isDebugEnabled()) {
                log.debug("Response: " +  e.getResponseBodyAsString());
            }
        }
    }

    private void sendMessage(SendMessage request) throws HttpStatusCodeException {
        String url = String.format(telegramUrl, telegramToken, SEND_MESSAGE_METHOD);
        restTemplate.postForLocation(url, request);
    }

}
