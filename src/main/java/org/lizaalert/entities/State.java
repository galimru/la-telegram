package org.lizaalert.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Table(name = "LA_STATE", uniqueConstraints =
@UniqueConstraint(name = "FK_LA_STATE_CLASS_NAME", columnNames = {"CLASS_NAME"}))
public class State extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 6356320412144772436L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CLASS_NAME")
    private String className;

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
}
