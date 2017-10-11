package org.lizaalert.services;

import com.github.galimru.telegram.objects.Message;
import com.github.galimru.telegram.objects.Update;
import com.github.galimru.telegram.objects.User;
import com.github.galimru.telegram.util.TelegramUtil;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.commands.CommandBuilder;
import org.lizaalert.commands.AbstractCommand;
import org.lizaalert.entities.Route;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.State;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RouteService {

    private final Log log = LogFactory.getLog(RouteService.class);

    @Autowired private RouteRepository routeRepository;
    @Autowired private BotanService botanService;

    public Route resolve(Session session, Update update) {
        String command = TelegramUtil.getText(update)
                .orElse(TelegramUtil.getCallbackData(update)
                        .orElse(null));
        State prevState = session.getState();
        // find global route
        Route route = routeRepository.findByCommandAndPrevStateIsNull(command);
        if (route != null) {
            return route;
        }
        // find local route
        route = routeRepository.findByCommandAndPrevState(command, prevState);
        if (route != null) {
            return route;
        }
        // find default route
        route = routeRepository.findByPrevStateAndCommandIsNull(prevState);
        if (route != null) {
            return route;
        }
        if (log.isDebugEnabled()) {
            log.debug(String.format("Can't resolve route for state %s and command %s", prevState, command));
        }
        return null;
    }

    public void moveTo(Session session, Route route, Update update) {
        SessionManager sessionManager = new SessionManager(session);
        if (route.getCommand() != null) {
            apply(sessionManager, route, update);
        } else {
            State prevState = route.getPrevState();
            AbstractCommand prevCommand = new CommandBuilder()
                    .setChatId(session.getChatId())
                    .setSessionManager(sessionManager)
                    .setClassName(prevState.getClassName())
                    .build();
            if (prevCommand.complete(update)) {
                apply(sessionManager, route, update);
            }
        }
    }

    private void apply(SessionManager sessionManager, Route route, Update update) {
        AbstractCommand command = new CommandBuilder()
                .setChatId(sessionManager.getSession().getChatId())
                .setSessionManager(sessionManager)
                .setClassName(route.getNextState().getClassName())
                .build();
        command.execute(update);
        sessionManager.getSession().setState(route.getNextState());
        sessionManager.save();
        botanService.track(sessionManager.getSession().getUser(), "route", ImmutableMap.of(
                "from", TelegramUtil.getFrom(update).orElse(new User()),
                "data", TelegramUtil.getCallbackData(update).orElse(""),
                "text", TelegramUtil.getText(update).orElse(""),
                "state", Optional.of(route.getNextState()).map(State::getName).orElse("")
        ));
    }

}
