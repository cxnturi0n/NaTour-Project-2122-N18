package org.natour.exceptions;

import com.google.gson.Gson;

public class PersistenceException extends Exception {
    public PersistenceException(String message) {
        super(message);
    }
}