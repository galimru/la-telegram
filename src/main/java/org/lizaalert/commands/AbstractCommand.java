package org.lizaalert.commands;

import com.github.galimru.telegram.actions.SendMessage;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.services.QueueService;
import org.lizaalert.services.TemplateService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractCommand {

    private static final String CHAT_ID_PARAM = "chatId";

    protected User user;
    protected State state;

    private TemplateService templateService;
    private QueueService queueService;

    public AbstractCommand(User user, State state) {
        this.user = user;
        this.state = state;
        this.templateService = ContextProvider.getBean(TemplateService.class);
        this.queueService = ContextProvider.getBean(QueueService.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    protected void sendTemplate(UUID templateId, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(CHAT_ID_PARAM, user.getChatId());
        SendMessage message = templateService.createMessage(templateId, params);
        queueService.pushMessage(message);
    }

    public abstract void execute();
}
