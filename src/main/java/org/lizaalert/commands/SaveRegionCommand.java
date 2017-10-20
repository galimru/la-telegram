package org.lizaalert.commands;

import com.github.galimru.telegram.objects.Update;
import org.lizaalert.managers.SessionManager;

public class SaveRegionCommand extends AbstractCommand {

    public SaveRegionCommand(String chatId, SessionManager sessionManager) {
        super(chatId, sessionManager);
    }

    @Override
    public void execute(Update update) {
        String forumId = sessionManager.get("forumId");
        attributeManager.put("regionId", forumId);
    }
}
