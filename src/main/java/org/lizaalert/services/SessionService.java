package org.lizaalert.services;

import com.github.galimru.telegram.objects.Chat;
import com.github.galimru.telegram.objects.Message;
import com.github.galimru.telegram.objects.Update;
import com.github.galimru.telegram.util.TelegramUtil;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.User;
import org.lizaalert.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SessionService {

    @Autowired private SessionRepository sessionRepository;

    public Session getSession(User user, Update update) {
        Session session = sessionRepository.findByUser(user);
        if (session == null) {
            session = new Session();
            session.setUser(user);
            Optional<Integer> chatId = TelegramUtil.getMessage(update)
                    .map(Message::getChat)
                    .map(Chat::getId);
            if (chatId.isPresent()) {
                session.setChatId(String.valueOf(chatId.get()));
            }
            sessionRepository.save(session);
        }
        return session;
    }

}
