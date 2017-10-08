package org.lizaalert.exceptions;

public class UnknownApplicationToken extends RuntimeException {
    private static final long serialVersionUID = -3143861200777534299L;

    public UnknownApplicationToken(String message) {
        super(message);
    }
}
