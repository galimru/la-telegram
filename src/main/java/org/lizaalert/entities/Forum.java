package org.lizaalert.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LA_FORUM", uniqueConstraints =
@UniqueConstraint(name = "FK_LA_FORUM_FORUM_ID", columnNames = {"FORUM_ID"}))
public class Forum extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -7777592042814814244L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FORUM_ID")
    private Integer forumId;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Forum parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Forum> forums = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forum")
    private List<Topic> topics = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getForumId() {
        return forumId;
    }

    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Forum getParent() {
        return parent;
    }

    public void setParent(Forum parent) {
        this.parent = parent;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
