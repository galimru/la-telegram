package org.lizaalert.commands;

import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.Update;
import com.google.common.collect.ImmutableMap;
import org.lizaalert.managers.SessionManager;

public class StartCommand extends AbstractCommand {

    public StartCommand(String chatId, SessionManager sessionManager) {
        super(chatId, sessionManager);
    }

    @Override
    public void execute(Update update) {
        sessionManager.clear();
        call(fromTemplate("welcome", SendMessage.class, ImmutableMap.of(
                "chat_id", chatId
        )));
    }

}
