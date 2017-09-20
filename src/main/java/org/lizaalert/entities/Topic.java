package org.lizaalert.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LA_TOPIC", uniqueConstraints =
@UniqueConstraint(name = "FK_LA_TOPIC_TOPIC_ID", columnNames = {"TOPIC_ID"}))
public class Topic extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -9203199177537293906L;

    public static final int MESSAGE_LENGTH = 4096;
    public static final int IMAGE_URL_LENGTH = 2048;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "MESSAGE", length = MESSAGE_LENGTH)
    private String message;

    @Column(name = "TOPIC_ID")
    private Integer topicId;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "IMAGE_URL", length = IMAGE_URL_LENGTH)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FORUM_ID")
    private Forum forum;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
}
