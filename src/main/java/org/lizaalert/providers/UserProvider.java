package org.lizaalert.providers;

import org.lizaalert.entities.User;
import org.lizaalert.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProvider {

    @Autowired private UserRepository userRepository;

    public User getUser(com.github.galimru.telegram.model.User telegramUser) {
        User user = userRepository.findByUserId(telegramUser.getId());
        if (user == null) {
            user = new User();
            user.setUserId(telegramUser.getId());
            user.setUsername(telegramUser.getUsername());
            user.setFirstName(telegramUser.getFirstName());
            user.setLastName(telegramUser.getLastName());
            user.setChatId(String.valueOf(telegramUser.getId()));
            user.setState(null);
            userRepository.save(user);
        }
        return user;
    }
}
