package org.lizaalert.builders;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.commands.AbstractCommand;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.exceptions.CommandNotFoundException;

import java.lang.reflect.InvocationTargetException;

public class CommandBuilder {

    private final Log log = LogFactory.getLog(CommandBuilder.class);

    private User user;
    private State state;
    private String text;

    public CommandBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public CommandBuilder setState(State state) {
        this.state = state;
        return this;
    }

    public CommandBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public AbstractCommand build() {
        String className = state.getClassName();
        AbstractCommand command = null;
        try {
            command = (AbstractCommand) Class.forName(className)
                    .getConstructor(User.class, State.class, String.class)
                    .newInstance(new Object[]{user, state, text});
        } catch (ClassNotFoundException
                | InstantiationException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException e) {
            throw new CommandNotFoundException(String.format("Command with class name %s not found", className), e);
        }
        return command;
    }
}
