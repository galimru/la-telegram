package org.lizaalert.repositories;

import org.lizaalert.entities.Route;
import org.lizaalert.entities.State;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {
    @EntityGraph(attributePaths = {"prevState", "nextState"})
    Route findByCommandAndPrevStateIsNull(String command);
    @EntityGraph(attributePaths = {"prevState", "nextState"})
    Route findByCommandAndPrevState(String command, State prevState);
    @EntityGraph(attributePaths = {"prevState", "nextState"})
    Route findByPrevStateAndCommandIsNull(State prevState);
}
