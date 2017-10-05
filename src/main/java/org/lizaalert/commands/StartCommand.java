package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.Session;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class StartCommand extends AbstractCommand {

    public StartCommand(Session session, Update update) {
        super(session, update);
    }

    @Override
    public boolean execute() {
        sendResponse("welcome");
        return true;
    }

}
