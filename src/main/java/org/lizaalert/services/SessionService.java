package org.lizaalert.services;

import org.lizaalert.entities.Session;
import org.lizaalert.entities.User;
import org.lizaalert.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionService {

    @Autowired private SessionRepository sessionRepository;

    public Session getSession(User user) {
        Session session = sessionRepository.findByUser(user);
        if (session == null) {
            session = new Session();
            session.setUser(user);
            sessionRepository.save(session);
        }
        return session;
    }

    public void save(Session session) {
        sessionRepository.save(session);
    }
}
