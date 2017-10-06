package org.lizaalert.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LA_SUBSCRIBE", uniqueConstraints =
@UniqueConstraint(name = "FK_LA_SUBSCRIBE_USER_ID_FORUM_ID", columnNames = {"USER_ID", "FORUM_ID"}))
public class Subscribe extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1597118252582174492L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORUM_ID")
    private Forum forum;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
}
