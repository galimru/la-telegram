package org.lizaalert.exceptions;

public class CommandNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3442318827486401941L;

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
