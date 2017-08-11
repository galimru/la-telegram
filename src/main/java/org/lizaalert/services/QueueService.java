package org.lizaalert.services;

import org.lizaalert.entities.Topic;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueService {

    public static final String TELEGRAM_NOTIFICATION_QUEUE = "TelegramNotificationQueue";

    @Autowired private RabbitTemplate rabbitTemplate;
    @Autowired private NotifyService notifyService;

    public void push(Topic topic) {
        rabbitTemplate.convertAndSend(TELEGRAM_NOTIFICATION_QUEUE, topic);
    }

    @RabbitListener(queues = TELEGRAM_NOTIFICATION_QUEUE)
    public void receiveNotification(Topic topic) {
        notifyService.notify(topic);
    }
}
