package org.lizaalert;

import org.lizaalert.services.QueueService;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class LizaalertApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LizaalertApplication.class);
    }

    @Bean
    public Queue getTelegramMessageQueue() {
        return new Queue(QueueService.TELEGRAM_MESSAGE_QUEUE);
    }

    @Bean
    public Queue getTelegramNotificationQueue() {
        return new Queue(QueueService.TELEGRAM_NOTIFICATION_QUEUE);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
