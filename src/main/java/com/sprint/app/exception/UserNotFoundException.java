package com.sprint.app.exception;

public class UserNotFoundException extends RuntimeException {
     public UserNotFoundException(String message)
     {
    	 super(message);
     }
}
