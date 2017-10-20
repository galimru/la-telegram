package org.lizaalert.repositories;

import org.lizaalert.entities.Attribute;
import org.lizaalert.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttributeRepository extends JpaRepository<Attribute, UUID> {
    Long deleteByUserAndKey(User user, String key);
}
