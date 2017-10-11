package org.lizaalert.services;

import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.ParseMode;
import org.lizaalert.entities.Subscribe;
import org.lizaalert.entities.Topic;
import org.lizaalert.entities.User;
import org.lizaalert.repositories.SubscribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotifyService {

    @Autowired private SubscribeRepository subscribeRepository;
    @Autowired private QueueService queueService;

    public void notify(Topic topic) {
        List<Subscribe> subscribes = subscribeRepository.findByForum(topic.getForum());
        for(Subscribe subscribe : subscribes) {
            User user = subscribe.getUser();
            SendMessage message = new SendMessage()
                    .setChatId(String.valueOf(user.getUserId()))
                    .setParseMode(ParseMode.HTML)
                    .setText(topic.getMessage());
            queueService.asyncCall(message);
        }
    }
}
