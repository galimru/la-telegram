package org.lizaalert.managers;

import com.github.galimru.telegram.objects.Update;
import com.github.galimru.telegram.util.TelegramUtil;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.commands.AbstractCommand;
import org.lizaalert.entities.Route;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.State;
import org.lizaalert.entities.Status;
import org.lizaalert.repositories.RouteRepository;

public class StateManager {

    private final Log log = LogFactory.getLog(StateManager.class);

    private SessionManager sessionManager;
    private AttributeManager attributeManager;

    private RouteRepository routeRepository;

    public StateManager(Session session) {
        this.sessionManager = new SessionManager(session);
        this.attributeManager = new AttributeManager(session.getUser());
        this.routeRepository = ContextProvider.getBean(RouteRepository.class);
    }

    public void handleUpdate(Update update) {
        State rootState = findRootState(update);
        if (rootState != null) {
            execute(rootState, update);
            save();
            return;
        }
        moveTo(sessionManager.getSession().getState(), update);
        save();
    }

    private void save() {
        sessionManager.save();
        attributeManager.save();
    }

    private void moveTo(State state, Update update) {
        if (sessionManager.getStatus() == Status.EXECUTION) {
            execute(state, update);
            if (BooleanUtils.isTrue(state.getTransition())) {
                moveTo(state, update);
            }
            return;
        }
        if (sessionManager.getStatus() == Status.AWAITING) {
            handle(state, update);
        }
        if (sessionManager.getStatus() == Status.COMPLETED) {
            State nextState = findState(state, update);
            if (nextState != null) {
                if (BooleanUtils.isTrue(nextState.getOneTime())) {
                    executeOneTime(nextState, update);
                } else {
                    sessionManager.setState(nextState, Status.EXECUTION);
                    moveTo(nextState, update);
                }
            }
        }
    }

    private void execute(State state, Update update) {
        AbstractCommand command = getCommand(state);
        command.execute(update);
        sessionManager.setState(state, Status.AWAITING);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Executed command %s", command.getClass().getSimpleName()));
        }
    }

    private void executeOneTime(State state, Update update) {
        AbstractCommand command = getCommand(state);
        command.execute(update);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Executed one time command %s", command.getClass().getSimpleName()));
        }
    }

    private void handle(State state, Update update) {
        AbstractCommand command = getCommand(state);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Received update for command %s", command.getClass().getSimpleName()));
        }
        if (command.onReceive(update)) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Completed command %s", command.getClass().getSimpleName()));
            }
            sessionManager.setState(state, Status.COMPLETED);
        }
    }

    private AbstractCommand getCommand(State state) {
        return new CommandBuilder()
                .setChatId(sessionManager.getSession().getChatId())
                .setSessionManager(sessionManager)
                .setAttributeManager(attributeManager)
                .setClassName(state.getClassName())
                .build();
    }

    private State findRootState(Update update) {
        String command = TelegramUtil.getText(update)
                .orElse(TelegramUtil.getCallbackData(update)
                        .orElse(null));
        Route route = routeRepository.findByCommandAndPrevStateIsNull(command);
        if (route != null) {
            return route.getNextState();
        }
        return null;
    }

    private State findState(State prevState, Update update) {
        String command = TelegramUtil.getText(update)
                .orElse(TelegramUtil.getCallbackData(update)
                        .orElse(null));
        Route route = routeRepository.findByCommandAndPrevState(command, prevState);
        if (route != null) {
            return route.getNextState();
        }
        route = routeRepository.findByPrevStateAndCommandIsNull(prevState);
        if (route != null) {
            return route.getNextState();
        }
        return null;
    }
}
