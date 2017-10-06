package org.lizaalert.managers;

import org.lizaalert.entities.Session;
import org.lizaalert.entities.SessionParam;
import org.lizaalert.repositories.SessionRepository;
import org.lizaalert.services.SessionService;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionManager {

    private Session session;
    private Map<String, String> params;

    private Set<String> forUpdate = new HashSet<>();
    private Set<String> forRemove = new HashSet<>();

    public SessionManager(Session session) {
        this.session = session;
        this.params = session.getParams().stream()
                .collect(Collectors.toMap(SessionParam::getKey, SessionParam::getValue));
    }

    public Session getSession() {
        return session;
    }

    public void put(String key, String value) {
        params.put(key, value);
        forUpdate.add(key);
        forRemove.remove(key);
    }

    public String get(String key) {
        return params.get(key);
    }

    public String remove(String key) {
        String value = params.remove(key);
        forRemove.add(key);
        forUpdate.remove(key);
        return value;
    }

    public void clear() {
        forUpdate.clear();
        forRemove.addAll(session.getParams().stream()
                .map(SessionParam::getKey)
                .collect(Collectors.toList()));
    }

    public void save() {
        updateParams();
        SessionRepository sessionRepository = ContextProvider.getBean(SessionRepository.class);
        sessionRepository.save(session);
    }

    protected void updateParams() {
        for(String key : forRemove) {
            session.getParams()
                    .removeIf(p -> key.equalsIgnoreCase(p.getKey()));
        }
        for(String key : forUpdate) {
            String value = params.get(key);
            Optional<SessionParam> param = session.getParams().stream()
                    .filter(p -> key.equalsIgnoreCase(p.getKey())).findFirst();
            if (param.isPresent()) {
                param.get().setValue(value);
            } else {
                param = Optional.of(new SessionParam());
                param.get().setSession(session);
                param.get().setKey(key);
                param.get().setValue(value);
                session.getParams().add(param.get());
            }
        }
    }
}
