package org.lizaalert.repositories;

import org.lizaalert.entities.Route;
import org.lizaalert.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {
    Route findByCommandAndPrevStateIsNull(String command);
    Route findByCommandAndPrevState(String command, State prevState);
    Route findByPrevStateAndCommandIsNull(State prevState);
}
