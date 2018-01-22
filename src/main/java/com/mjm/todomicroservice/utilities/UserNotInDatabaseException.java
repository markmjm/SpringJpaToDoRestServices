package com.mjm.todomicroservice.utilities;

public class UserNotInDatabaseException extends Exception{

    public UserNotInDatabaseException(String message) {
        super(message);
    }
}
