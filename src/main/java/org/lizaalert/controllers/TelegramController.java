package org.lizaalert.controllers;

import com.github.galimru.telegram.model.Message;
import com.github.galimru.telegram.model.Update;
import com.google.common.collect.ImmutableMap;
import io.botan.sdk.Botan;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.entities.Route;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.User;
import org.lizaalert.exceptions.UnknownApplicationToken;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/api")
public class TelegramController {

    private final Log log = LogFactory.getLog(TelegramController.class);

    @Value("${org.lizaalert.app.token}")
    private String appToken;

    @Autowired private UserService userService;
    @Autowired private SessionService sessionService;
    @Autowired private RouteService routeService;
    @Autowired private MessageService messageService;
    @Autowired private BotanService botanService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/update/{token}", method = RequestMethod.POST)
    public void update(@PathVariable String token, @RequestBody Update update) {
        if (!appToken.equals(token)) {
            throw new UnknownApplicationToken(String.format("Unknown application token: %s", token));
        }

        User user = userService.getUser(update);
        Session session = sessionService.getSession(user);

        Route route = routeService.resolve(session, update);
        if (route != null) {
            routeService.moveTo(session, route, update);
        } else {
            messageService.sendResponse(session, "message",
                    Collections.singletonMap("text", "Извините, я вас не понял"));
            Message message = update.getCallbackQuery() != null
                    ? update.getCallbackQuery().getMessage()
                    : update.getMessage();
            botanService.track(session.getUser(), "unknown", ImmutableMap.of(
                    "text", message.getText(),
                    "state", session.getState() != null ? session.getState().getName() : "<null>",
                    "message", message)
            );
        }
    }

}
