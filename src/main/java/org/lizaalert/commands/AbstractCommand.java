package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.providers.SessionManager;
import org.lizaalert.services.MessageService;

import java.util.Collections;
import java.util.Map;

public abstract class AbstractCommand {

    protected SessionManager sessionManager;
    protected Update update;

    protected MessageService messageService;

    public AbstractCommand(SessionManager sessionManager, Update update) {
        this.sessionManager = sessionManager;
        this.update = update;
        this.messageService = ContextProvider.getBean(MessageService.class);
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
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

    public abstract boolean execute();
}
