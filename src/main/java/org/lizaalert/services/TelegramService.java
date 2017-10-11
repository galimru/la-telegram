package org.lizaalert.services;

import com.github.galimru.telegram.methods.AbstractMethod;
import com.google.common.util.concurrent.RateLimiter;
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

    private static final double REQUESTS_PER_SECOND = 30;

    @Value("${org.lizaalert.telegram.token}")
    private String telegramToken;

    @Value("${org.lizaalert.telegram.url}")
    private String telegramUrl;

    @Autowired private RestTemplate restTemplate;

    private RateLimiter rateLimiter = RateLimiter.create(REQUESTS_PER_SECOND);

    public void execute(AbstractMethod method) {
        rateLimiter.acquire();
        if (log.isDebugEnabled()) {
            log.debug(String.format("Executing telegram method %s: %s",
                    method.getMethodName(), method.toString()));
        }
        try {
            String url = String.format(telegramUrl, telegramToken, method.getMethodName());
            restTemplate.postForLocation(url, method);
        } catch (HttpStatusCodeException e) {
            log.error("Error on sending request to telegram", e);
            if (log.isDebugEnabled()) {
                log.debug("Error response: " +  e.getResponseBodyAsString());
            }
        }
    }

}
