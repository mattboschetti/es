package com.mattboschetti.sandbox.es;

import java.lang.reflect.InvocationTargetException;

public class ESException extends RuntimeException {
    public ESException(String msg, IllegalAccessException e) {
        super(msg, e);
    }

    public ESException(InvocationTargetException e) {
        super(e);
    }
}
