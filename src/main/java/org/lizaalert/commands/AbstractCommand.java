package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.Session;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.services.MessageService;

import java.util.Collections;
import java.util.Map;

public abstract class AbstractCommand {

    protected Session session;
    protected Update update;

    protected MessageService messageService;

    public AbstractCommand(Session session, Update update) {
        this.session = session;
        this.update = update;
        this.messageService = ContextProvider.getBean(MessageService.class);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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
        messageService.sendResponse(session, templateId, parameters);
    }

    public abstract boolean execute();
}
