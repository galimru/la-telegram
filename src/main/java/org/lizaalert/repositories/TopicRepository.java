package org.lizaalert.repositories;

import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    Topic findByTopicId(Integer topicId);
    Page<Topic> findByForumAndActiveIsTrueOrderByCreatedAt(Forum forum, Pageable pageable);
}
