package org.lizaalert.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.commands.AbstractCommand;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;

import java.lang.reflect.InvocationTargetException;

public class CommandBuilder {

    private final Log log = LogFactory.getLog(CommandBuilder.class);

    private User user;
    private State state;

    public CommandBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public CommandBuilder setState(State state) {
        this.state = state;
        return this;
    }

    public AbstractCommand build() {
        String className = state.getClassName();
        AbstractCommand command = null;
        try {
            command = (AbstractCommand) Class.forName(className)
                    .getConstructor(User.class, State.class)
                    .newInstance(new Object[] {user, state});
        } catch (ClassNotFoundException
                | InstantiationException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException e) {
            log.error(String.format("Exception on building command object from class name %s", className), e);
        }
        return command;
    }
}
