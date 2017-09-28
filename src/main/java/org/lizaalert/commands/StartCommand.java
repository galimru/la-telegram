package org.lizaalert.commands;

import org.lizaalert.entities.State;
import org.lizaalert.entities.User;

import java.util.HashMap;
import java.util.UUID;

public class StartCommand extends AbstractCommand {

    public StartCommand(User user, State state, String text) {
        super(user, state, text);
    }

    @Override
    public void execute() {
        sendTemplate(UUID.fromString("cd5a8d1d-daf8-43a8-b478-7b58901885c2"), new HashMap<>());
    }

}
