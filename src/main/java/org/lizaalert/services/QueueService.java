package org.lizaalert.services;

import com.github.galimru.telegram.actions.GenericRequest;
import com.github.galimru.telegram.actions.SendMessage;
import org.lizaalert.entities.Topic;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueService {

    public static final String TELEGRAM_NOTIFICATION_QUEUE = "TelegramNotificationQueue";
    public static final String TELEGRAM_MESSAGE_QUEUE = "TelegramMessageQueue";

    @Autowired private RabbitTemplate rabbitTemplate;
    @Autowired private NotifyService notifyService;
    @Autowired private TelegramService telegramService;

    public void pushNotification(Topic topic) {
        rabbitTemplate.convertAndSend(TELEGRAM_NOTIFICATION_QUEUE, topic);
    }

    @RabbitListener(queues = TELEGRAM_NOTIFICATION_QUEUE)
    public void receiveNotification(Topic topic) {
        notifyService.notify(topic);
    }

    public void pushMessage(SendMessage message) {
        rabbitTemplate.convertAndSend(TELEGRAM_MESSAGE_QUEUE, message);
    }

    @RabbitListener(queues = TELEGRAM_MESSAGE_QUEUE)
    public void receiveMessage(SendMessage message) {
        telegramService.execute(message);
    }
}
