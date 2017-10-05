package org.lizaalert.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LA_STATE", uniqueConstraints =
@UniqueConstraint(name = "FK_LA_STATE_PARENT_ID_COMMAND", columnNames = {"PARENT_ID", "COMMAND"}))
public class State extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 6356320412144772436L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private State parent;

    @Column(name = "COMMAND")
    private String command;

    @Column(name = "CLASS_NAME")
    private String className;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSITION_TO")
    private State transitionTo;

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public State getTransitionTo() {
        return transitionTo;
    }

    public void setTransitionTo(State transitionTo) {
        this.transitionTo = transitionTo;
    }
}
