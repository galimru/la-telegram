package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.services.MessageService;

import java.util.Collections;
import java.util.Map;

public abstract class AbstractCommand {

    protected SessionManager sessionManager;
    protected MessageService messageService;

    public AbstractCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.messageService = ContextProvider.getBean(MessageService.class);
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public abstract void execute(Update update);

    public boolean complete(Update update) {
        return true;
    }

    protected void sendResponse(String templateId) {
        sendResponse(templateId, null);
    }

    protected void sendResponse(String templateId, String key, Object value) {
        sendResponse(templateId, Collections.singletonMap(key, value));
    }

    protected void sendResponse(String templateId, Map<String, Object> parameters) {
        messageService.sendResponse(sessionManager.getSession(), templateId, parameters);
    }

}
