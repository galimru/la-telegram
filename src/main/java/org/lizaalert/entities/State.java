package org.lizaalert.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "LA_STATE")
public class State extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 6356320412144772436L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CLASS_NAME")
    private String className;

    @Column(name = "TRANSITION")
    private Boolean transition;

    @Column(name = "ONE_TIME")
    private Boolean oneTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean getTransition() {
        return transition;
    }

    public void setTransition(Boolean transition) {
        this.transition = transition;
    }

    public Boolean getOneTime() {
        return oneTime;
    }

    public void setOneTime(Boolean oneTime) {
        this.oneTime = oneTime;
    }
}
