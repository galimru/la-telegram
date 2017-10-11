package org.lizaalert.services;

import com.github.galimru.telegram.methods.AbstractMethod;
import org.lizaalert.entities.Topic;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueService {

    public static final String TELEGRAM_NOTIFICATION_QUEUE = "TelegramNotificationQueue";
    public static final String TELEGRAM_CALL_QUEUE = "TelegramCallQueue";

    @Autowired private RabbitTemplate rabbitTemplate;
    @Autowired private NotifyService notifyService;
    @Autowired private TelegramService telegramService;

    public void asyncNotify(Topic topic) {
        rabbitTemplate.convertAndSend(TELEGRAM_NOTIFICATION_QUEUE, topic);
    }

    @RabbitListener(queues = TELEGRAM_NOTIFICATION_QUEUE)
    public void receiveNotify(Topic topic) {
        notifyService.notify(topic);
    }

    public void asyncCall(AbstractMethod message) {
        rabbitTemplate.convertAndSend(TELEGRAM_CALL_QUEUE, message);
    }

    @RabbitListener(queues = TELEGRAM_CALL_QUEUE)
    public void receiveCall(AbstractMethod method) {
        telegramService.execute(method);
    }
}
