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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FORUM_ID")
    private Integer forumId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forum")
    private List<Topic> topics = new ArrayList<>();

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

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

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
