package org.lizaalert.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LA_SESSION", uniqueConstraints =
@UniqueConstraint(name = "FK_LA_SESSION_USER_ID", columnNames = {"USER_ID"}))
public class Session extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 6115355066332554966L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_ID")
    private State state;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "session",
            cascade = CascadeType.ALL)
    private List<SessionParam> params = new ArrayList<>();

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

    public List<SessionParam> getParams() {
        return params;
    }

    public void setParams(List<SessionParam> params) {
        this.params = params;
    }
}
