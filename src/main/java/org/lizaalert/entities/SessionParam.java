package org.lizaalert.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LA_SESSION_PARAM", uniqueConstraints =
@UniqueConstraint(name = "FK_LA_SESSION_PARAM_SESSION_ID_KEY", columnNames = {"SESSION_ID", "KEY"}))
public class SessionParam extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 3579185219074555595L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_ID")
    private Session session;

    @Column(name = "KEY")
    private String key;

    @Lob
    @Column(name = "VALUE")
    private String value;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
