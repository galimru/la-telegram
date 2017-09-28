package org.lizaalert.repositories;

import org.lizaalert.entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ForumRepository extends JpaRepository<Forum, UUID> {
    @Query("select f from Forum f where f.enabled = true and f.forums is empty")
    List<Forum> findAllEnabled();
    List<Forum> findBy();
}
