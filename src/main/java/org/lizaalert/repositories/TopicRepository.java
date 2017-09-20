package org.lizaalert.repositories;

import org.lizaalert.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    Topic findByTopicId(Integer topicId);
}
