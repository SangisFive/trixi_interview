package com.sangis.corejava.infrastructure.persistence;

public class PersistenceException extends Exception{
    public PersistenceException(String message, Throwable cause){
        super(message, cause);
    }
}
