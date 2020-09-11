package com.revature.exceptions;

public class ResourcePersistenceException extends RuntimeException {

    public ResourcePersistenceException(){
        super("Resource not Persisted");
    }
    public ResourcePersistenceException(String msg){
        super(msg);
    }
}
