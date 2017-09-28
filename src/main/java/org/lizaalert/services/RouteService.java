package org.lizaalert.services;

import com.github.galimru.telegram.model.Update;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.repositories.StateRepository;
import org.lizaalert.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteService {

    private final Log log = LogFactory.getLog(RouteService.class);

    @Autowired private UserRepository userRepository;
    @Autowired private StateRepository stateRepository;

    public State resolve(User user, Update update) {
        String command = update.getMessage().getText();
        State state = stateRepository.findByParentAndCommand(user.getState(), command);
        if (state != null) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("State found by command %s in user context", command));
            }
            return state;
        }
        state = stateRepository.findByCommandAndParentIsNull(command);
        if (state != null) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("State found by command %s in global context", command));
            }
            return state;
        }
        state = stateRepository.findByParentAndCommandIsNull(user.getState());
        if (state != null) {
            if (log.isDebugEnabled()) {
                log.debug("State found in user context as default");
            }
            return state;
        }
        if (log.isDebugEnabled()) {
            log.debug(String.format("Can't resolve state for user %d and command %s",
                    user.getUserId(), command));
        }
        return null;
    }

    public void complete(User user, State state) {
        user.setState(state);
        userRepository.save(user);
    }

}
