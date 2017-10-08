package org.lizaalert.repositories;

import org.lizaalert.entities.Session;
import org.lizaalert.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    @EntityGraph(attributePaths = {"user", "params", "state"})
    Session findByUser(User user);
}
