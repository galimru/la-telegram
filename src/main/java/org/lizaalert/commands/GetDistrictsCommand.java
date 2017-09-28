package org.lizaalert.commands;

import org.lizaalert.entities.Forum;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.repositories.ForumRepository;

import java.util.*;

public class GetDistrictsCommand extends AbstractCommand {

    public GetDistrictsCommand(User user, State state, String text) {
        super(user, state, text);
    }

    @Override
    public void execute() {
        sendTemplate(UUID.fromString("d8f767fd-5da4-4857-88b8-a27159171839"), Collections.singletonMap("text", text));
    }

}
