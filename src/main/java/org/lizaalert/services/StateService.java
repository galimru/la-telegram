package org.lizaalert.services;

import com.github.galimru.telegram.model.Update;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.State;
import org.lizaalert.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateService {

    private final Log log = LogFactory.getLog(StateService.class);

    @Autowired private StateRepository stateRepository;

    public State getState(Session session, Update update) {
        String command = update.getCallbackQuery() != null
                ? update.getCallbackQuery().getMessage().getText()
                : update.getMessage().getText();
        State state = stateRepository.findByParentAndCommand(session.getState(), command);
        if (state != null) {
            return state;
        }
        state = stateRepository.findByCommandAndParentIsNull(command);
        if (state != null) {
            return state;
        }
        state = stateRepository.findByParentAndCommandIsNull(session.getState());
        if (state != null) {
            return state;
        }
        if (log.isDebugEnabled()) {
            log.debug(String.format("Can't resolve state for session %s and command %s",
                    session.getId().toString(), command));
        }
        return null;
    }

}
