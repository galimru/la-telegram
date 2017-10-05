package org.lizaalert.services;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.User;
import org.lizaalert.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired private UserRepository userRepository;

    public User getUser(Update update) {
        com.github.galimru.telegram.model.User telegramUser = update.getCallbackQuery() != null
                ? update.getCallbackQuery().getFrom()
                : update.getMessage().getFrom();
        User user = userRepository.findByUserId(telegramUser.getId());
        if (user == null) {
            user = new User();
            user.setUserId(telegramUser.getId());
            user.setUsername(telegramUser.getUsername());
            user.setFirstName(telegramUser.getFirstName());
            user.setLastName(telegramUser.getLastName());
            userRepository.save(user);
        }
        return user;
    }
}
