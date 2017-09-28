package org.lizaalert.controllers;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.builders.CommandBuilder;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.providers.UserProvider;
import org.lizaalert.repositories.StateRepository;
import org.lizaalert.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class TelegramController {

    @Autowired private StateRepository stateRepository;
    @Autowired private UserProvider userProvider;
    @Autowired private RouteService routeService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/update/{token}", method = RequestMethod.POST)
    public void update(@PathVariable String token, @RequestBody Update update) {
        User user = userProvider.getUser(update.getMessage().getFrom());
        State state = routeService.resolve(user, update);

        if (state == null) {
            // TODO if state null send user message command not found
            return;
        }

        new CommandBuilder()
                .setUser(user)
                .setState(state)
                .build()
                .execute();

        routeService.complete(user, state);
    }

}
