package org.lizaalert.commands;

import com.github.galimru.telegram.methods.AbstractMethod;
import com.github.galimru.telegram.objects.Update;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.services.QueueService;
import org.lizaalert.services.TemplateService;

import java.util.Map;

public abstract class AbstractCommand {

    protected String chatId;
    protected SessionManager sessionManager;
    protected QueueService queueService;
    protected TemplateService templateService;

    public AbstractCommand(String chatId, SessionManager sessionManager) {
        this.chatId = chatId;
        this.sessionManager = sessionManager;
        this.queueService = ContextProvider.getBean(QueueService.class);
        this.templateService = ContextProvider.getBean(TemplateService.class);
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

    protected void call(AbstractMethod method) {
        queueService.asyncCall(method);
    }

    protected <T extends AbstractMethod> T fromTemplate(String templateName, Class<T> templateClass,
                                          Map<String, Object> params) {
        return templateService.fromTemplate(templateName, templateClass, params);
    }
}
