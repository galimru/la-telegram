package org.lizaalert.services;

import com.github.galimru.telegram.model.ForceReply;
import com.github.galimru.telegram.model.ParseMode;
import com.github.galimru.telegram.model.SendMessageRequest;
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
            SendMessageRequest request = new SendMessageRequest();
            request.setChatId(user.getChatId());
            request.setParseMode(ParseMode.HTML);
            request.setText(topic.getMessage());
            ForceReply reply = new ForceReply();
            reply.setForceReply(true);
            request.setReplyMarkup(reply);
            telegramService.sendMessage(request);
        }
    }
}
