package org.lizaalert.managers;

import org.lizaalert.entities.Attribute;
import org.lizaalert.entities.User;
import org.lizaalert.repositories.AttributeRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AttributeManager {

    private User user;
    private Map<String, String> attributes;

    private Set<String> forUpdate = new HashSet<>();
    private Set<String> forRemove = new HashSet<>();

    private AttributeRepository attributeRepository;

    public AttributeManager(User user) {
        this.user = user;
        this.attributes = user.getAttributes().stream()
                .collect(Collectors.toMap(Attribute::getKey, Attribute::getValue));
        this.attributeRepository = ContextProvider.getBean(AttributeRepository.class);
    }

    public User getUser() {
        return user;
    }

    public void put(String key, String value) {
        attributes.put(key, value);
        forUpdate.add(key);
        forRemove.remove(key);
    }

    public void put(String key, Object value) {
        put(key, value != null ? value.toString() : null);
    }

    public String get(String key) {
        return attributes.get(key);
    }

    public String remove(String key) {
        String value = attributes.remove(key);
        forRemove.add(key);
        forUpdate.remove(key);
        return value;
    }

    public void clear() {
        attributes.clear();
        forUpdate.clear();
        forRemove.addAll(user.getAttributes().stream()
                .map(Attribute::getKey)
                .collect(Collectors.toList()));
    }

    public void save() {
        for(String key : forRemove) {
            attributeRepository.deleteByUserAndKey(user, key);
        }
        for(String key : forUpdate) {
            String value = attributes.get(key);
            Attribute attribute = user.getAttributes().stream()
                    .filter(i -> key.equalsIgnoreCase(i.getKey()))
                    .findFirst()
                    .orElse(new Attribute());
            attribute.setUser(user);
            attribute.setKey(key);
            attribute.setValue(value);
            attributeRepository.save(attribute);
        }
    }
}
