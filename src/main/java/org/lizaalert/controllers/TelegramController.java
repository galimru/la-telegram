package org.lizaalert.controllers;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.builders.CommandBuilder;
import org.lizaalert.commands.AbstractCommand;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.services.MessageService;
import org.lizaalert.services.SessionService;
import org.lizaalert.services.StateService;
import org.lizaalert.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/api")
public class TelegramController {

    @Autowired private UserService userService;
    @Autowired private SessionService sessionService;
    @Autowired private StateService stateService;
    @Autowired private MessageService messageService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/update/{token}", method = RequestMethod.POST)
    public void update(@PathVariable String token, @RequestBody Update update) {
        // TODO check internal token
        User user = userService.getUser(update);
        Session session = sessionService.getSession(user);
        State state = stateService.getState(session, update);

        if (state == null) {
            messageService.sendResponse(session, "message",
                    Collections.singletonMap("text", "Не удалось распознать команду"));
            return;
        }

        AbstractCommand command = new CommandBuilder(state)
                .setSession(session)
                .setUpdate(update)
                .build();

        if (command.execute()) {
            if (state.getTransitionTo() != null) {
                state = state.getTransitionTo();
            }
            session.setState(state);
            sessionService.save(session);
        }
    }

}
