package org.lizaalert.controllers;

import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.Message;
import com.github.galimru.telegram.objects.Update;
import com.github.galimru.telegram.util.TelegramUtil;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.entities.Route;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.exceptions.UnknownApplicationToken;
import org.lizaalert.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api")
public class TelegramController {

    private final Log log = LogFactory.getLog(TelegramController.class);

    @Value("${org.lizaalert.app.token}")
    private String appToken;

    @Autowired private UserService userService;
    @Autowired private SessionService sessionService;
    @Autowired private RouteService routeService;
    @Autowired private QueueService queueService;
    @Autowired private BotanService botanService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/update/{token}", method = RequestMethod.POST)
    public void update(@PathVariable String token, @RequestBody Update update) {
        if (!appToken.equals(token)) {
            throw new UnknownApplicationToken(String.format("Unknown application token: %s", token));
        }

        User user = userService.getUser(update);
        Session session = sessionService.getSession(user, update);

        Route route = routeService.resolve(session, update);
        if (route != null) {
            routeService.moveTo(session, route, update);
        } else {
            queueService.asyncCall(new SendMessage()
                    .setChatId(session.getChatId())
                    .setText("Извините, я вас не понял"));
            botanService.track(user, "unknown", ImmutableMap.of(
                    "from", TelegramUtil.getFrom(update).orElse(new com.github.galimru.telegram.objects.User()),
                    "data", TelegramUtil.getCallbackData(update).orElse(""),
                    "text", TelegramUtil.getText(update).orElse(""),
                    "state", Optional.of(session.getState()).map(State::getName).orElse("")
            ));
        }
    }

}
