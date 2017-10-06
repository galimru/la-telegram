package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.managers.SessionManager;

public class StartCommand extends AbstractCommand {

    public StartCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void execute(Update update) {
        sendResponse("welcome");
        sessionManager.clear();
    }

}
