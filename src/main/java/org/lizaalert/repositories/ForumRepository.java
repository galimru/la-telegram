package org.lizaalert.repositories;

import org.lizaalert.entities.Category;
import org.lizaalert.entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ForumRepository extends JpaRepository<Forum, UUID> {
    List<Forum> findByCategory(Category category);
}
