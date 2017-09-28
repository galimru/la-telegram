package org.lizaalert.services;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.providers.UserProvider;
import org.lizaalert.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteService {

    @Autowired private StateRepository stateRepository;

    public State resolve(User user, Update update) {
        // try to find local state
        State state = stateRepository.findByParentAndCommand(user.getState(), update.getMessage().getText());
        if (state == null) {
            // try to find global state
            state = stateRepository.findByParentAndCommand(null, update.getMessage().getText());
        }
        if (state == null) {
            // try to find default local state
            state = stateRepository.findByParentAndCommand(user.getState(), null);
        }
        return state;
    }

}
