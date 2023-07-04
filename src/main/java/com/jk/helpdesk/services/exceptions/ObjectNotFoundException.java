package com.jk.helpdesk.services.exceptions;

import java.io.Serial;

public class ObjectNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }
}
