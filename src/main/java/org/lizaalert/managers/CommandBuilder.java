package org.lizaalert.managers;

import com.google.common.base.Preconditions;
import org.lizaalert.commands.AbstractCommand;
import org.lizaalert.exceptions.CommandNotFoundException;

import java.lang.reflect.InvocationTargetException;

public class CommandBuilder {

    private String chatId;
    private SessionManager sessionManager;
    private AttributeManager attributeManager;
    private String className;

    public CommandBuilder() {
    }

    public CommandBuilder setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public CommandBuilder setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        return this;
    }

    public CommandBuilder setAttributeManager(AttributeManager attributeManager) {
        this.attributeManager = attributeManager;
        return this;
    }

    public CommandBuilder setClassName(String className) {
        this.className = className;
        return this;
    }

    public AbstractCommand build() {
        Preconditions.checkNotNull(className);
        Preconditions.checkNotNull(sessionManager);
        AbstractCommand command;
        try {
            command = (AbstractCommand) Class.forName(className)
                    .getConstructor(String.class, SessionManager.class)
                    .newInstance(new Object[]{chatId, sessionManager});
        } catch (ClassNotFoundException
                | InstantiationException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException e) {
            throw new CommandNotFoundException(String.format("Command with class name %s not found", className), e);
        }
        if (attributeManager != null) {
            command.setAttributeManager(attributeManager);
        }
        return command;
    }
}
