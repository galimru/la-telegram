package org.lizaalert.repositories;

import org.lizaalert.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select distinct u from User u join u.forums f where f.forumId = ?1")
    List<User> findByForumId(Integer forumId);
}
