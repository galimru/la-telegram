package org.lizaalert.repositories;

import org.lizaalert.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StateRepository extends JpaRepository<State, UUID> {
    State findByParentAndCommand(State parent, String command);
}
