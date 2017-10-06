package org.lizaalert.repositories;

import org.lizaalert.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUserId(Integer userId);
}
