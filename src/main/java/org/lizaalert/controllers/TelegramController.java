package org.lizaalert.controllers;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.builders.CommandBuilder;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.providers.UserProvider;
import org.lizaalert.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("/api")
public class TelegramController {

    @Autowired private StateRepository stateRepository;
    @Autowired private UserProvider userProvider;

    @RequestMapping(path = "/update/{token}", method = RequestMethod.POST)
    public void update(@PathVariable String token, @RequestBody Update update) {
        User user = userProvider.getUser(update.getMessage().getFrom());
        State state = stateRepository.findByParentAndCommand(user.getState(), update.getMessage().getText());
        // TODO if state null use default command

        new CommandBuilder()
                .setUser(user)
                .setState(state)
                .build()
                .execute();
    }

}
