package org.lizaalert.services;

import com.github.galimru.telegram.model.Update;
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

@Component
public class RouteService {

    private final Log log = LogFactory.getLog(RouteService.class);

    @Autowired private RouteRepository routeRepository;

    public Route resolve(Session session, Update update) {
        String command = update.getCallbackQuery() != null
                ? update.getCallbackQuery().getData()
                : update.getMessage().getText();
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
            log.debug(String.format("Can't resolve route for state %s and command %s",
                    prevState != null ? prevState.getId().toString() : "<null>",
                    command != null ? command : "<null>"));
        }
        return null;
    }

    public void moveTo(Session session, Route route, Update update) {
        SessionManager sessionManager = new SessionManager(session);
        State nextState = route.getNextState();
        if (route.getCommand() != null) {
            // execute next state as usual (normal command)
            AbstractCommand command = new CommandBuilder()
                    .setSessionManager(sessionManager)
                    .setClassName(nextState.getClassName())
                    .build();
            command.execute(update);
            sessionManager.getSession().setState(route.getNextState());
            sessionManager.save();
        } else {
            // execute complete on prev state
            // execute next state as usual (default command)
            State prevState = route.getPrevState();
            AbstractCommand prevCommand = new CommandBuilder()
                    .setSessionManager(sessionManager)
                    .setClassName(prevState.getClassName())
                    .build();
            if (prevCommand.complete(update)) {
                AbstractCommand command = new CommandBuilder()
                        .setSessionManager(sessionManager)
                        .setClassName(nextState.getClassName())
                        .build();
                command.execute(update);
                sessionManager.getSession().setState(route.getNextState());
                sessionManager.save();
            }
        }
    }

}
