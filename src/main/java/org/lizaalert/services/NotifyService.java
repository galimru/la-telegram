package org.lizaalert.services;

import com.github.galimru.telegram.actions.SendMessage;
import com.github.galimru.telegram.model.ForceReply;
import com.github.galimru.telegram.model.ParseMode;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Subscribe;
import org.lizaalert.entities.Topic;
import org.lizaalert.entities.User;
import org.lizaalert.repositories.ForumRepository;
import org.lizaalert.repositories.SubscribeRepository;
import org.lizaalert.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotifyService {

    @Autowired private TelegramService telegramService;
    @Autowired private SubscribeRepository subscribeRepository;
    @Autowired private ForumRepository forumRepository;

    public void notify(Topic topic) {
        List<Subscribe> subscribes = subscribeRepository.findByForum(topic.getForum());
        for(Subscribe subscribe : subscribes) {
            User user = subscribe.getUser();
            SendMessage request = new SendMessage()
                    .setChatId(String.valueOf(user.getUserId()))
                    .setParseMode(ParseMode.HTML)
                    .setText(topic.getMessage());
            telegramService.execute(request);
        }
    }
}
