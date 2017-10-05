package org.lizaalert.builders;

import com.github.galimru.telegram.model.Update;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lizaalert.commands.AbstractCommand;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.exceptions.CommandNotFoundException;

import java.lang.reflect.InvocationTargetException;

public class CommandBuilder {

    private State state;
    private Session session;
    private Update update;

    public CommandBuilder(State state) {
        this.state = state;
    }

    public CommandBuilder setSession(Session session) {
        this.session = session;
        return this;
    }

    public CommandBuilder setUpdate(Update update) {
        this.update = update;
        return this;
    }

    public AbstractCommand build() {
        String className = state.getClassName();
        AbstractCommand command;
        try {
            command = (AbstractCommand) Class.forName(className)
                    .getConstructor(Session.class, Update.class)
                    .newInstance(new Object[]{session, update});
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
