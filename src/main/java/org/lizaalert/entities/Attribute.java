package org.lizaalert.entities;

import javax.persistence.*;

@Entity
@Table(name = "LA_ATTRIBUTE", uniqueConstraints =
@UniqueConstraint(name = "LA_ATTRIBUTE_PARAM_KEY", columnNames = {"USER_ID", "KEY"}))
public class Attribute extends AbstractEntity {
    private static final long serialVersionUID = 8322446999193510059L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE", length = 512)
    private String value;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
