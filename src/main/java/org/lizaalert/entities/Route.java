package org.lizaalert.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LA_ROUTE")
public class Route extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 2027284464431039773L;

    @Column(name = "COMMAND")
    private String command;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREV_STATE_ID")
    private State prevState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEXT_STATE_ID")
    private State nextState;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public State getPrevState() {
        return prevState;
    }

    public void setPrevState(State prevState) {
        this.prevState = prevState;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }
}
