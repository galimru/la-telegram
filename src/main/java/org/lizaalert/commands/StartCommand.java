package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.providers.SessionManager;

public class StartCommand extends AbstractCommand {

    public StartCommand(SessionManager sessionManager, Update update) {
        super(sessionManager, update);
    }

    @Override
    public boolean execute() {
        sendResponse("welcome");
        sessionManager.clear();
        return true;
    }

}
