package org.lizaalert.services;

import com.github.galimru.telegram.actions.SendMessage;
import com.github.galimru.telegram.model.ForceReply;
import com.github.galimru.telegram.model.ParseMode;
import org.lizaalert.entities.Topic;
import org.lizaalert.entities.User;
import org.lizaalert.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotifyService {

    @Autowired private TelegramService telegramService;
    @Autowired private UserRepository userRepository;

    public void notify(Topic topic) {
        List<User> users = userRepository.findByForumId(topic.getForum().getForumId());
        for(User user : users) {
            SendMessage request = new SendMessage()
                    .setChatId(user.getChatId())
                    .setParseMode(ParseMode.HTML)
                    .setText(topic.getMessage())
                    .setReplyMarkup(new ForceReply()
                            .setForceReply(true));
            telegramService.execute(request);
        }
    }
}
