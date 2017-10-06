package org.lizaalert.repositories;

import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Subscribe;
import org.lizaalert.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubscribeRepository extends JpaRepository<Subscribe, UUID> {
    Subscribe findByUserAndForum(User user, Forum forum);
    @EntityGraph(attributePaths = {"user"})
    List<Subscribe> findByForum(Forum forum);
}
