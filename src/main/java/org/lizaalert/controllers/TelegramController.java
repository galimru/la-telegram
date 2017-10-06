package org.lizaalert.controllers;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.Route;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.User;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.services.*;
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
    @Autowired private RouteService routeService;
    @Autowired private MessageService messageService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/update/{token}", method = RequestMethod.POST)
    public void update(@PathVariable String token, @RequestBody Update update) {
        if (!"123".equals(token)) {
            // TODO check internal token
            // return;
        }

        User user = userService.getUser(update);
        Session session = sessionService.getSession(user);

        Route route = routeService.resolve(session, update);
        if (route != null) {
            routeService.moveTo(session, route, update);
        } else {
            messageService.sendResponse(session, "message",
                    Collections.singletonMap("text", "Извините, я вас не понял"));
        }

        //routeService.handleUpdate(new SessionManager(session), update);
    }

}
