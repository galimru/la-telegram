package org.lizaalert.controllers;

import com.github.galimru.telegram.objects.Update;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.User;
import org.lizaalert.exceptions.UnknownApplicationToken;
import org.lizaalert.managers.StateManager;
import org.lizaalert.services.SessionService;
import org.lizaalert.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class TelegramController {

    private final Log log = LogFactory.getLog(TelegramController.class);

    @Value("${org.lizaalert.app.token}")
    private String appToken;

    @Autowired private UserService userService;
    @Autowired private SessionService sessionService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/update/{token}", method = RequestMethod.POST)
    public void update(@PathVariable String token, @RequestBody Update update) {
        if (!appToken.equals(token)) {
            throw new UnknownApplicationToken(String.format("Unknown application token: %s", token));
        }

        User user = userService.getUser(update);
        Session session = sessionService.getSession(user, update);
        new StateManager(session).handleUpdate(update);
    }

}
