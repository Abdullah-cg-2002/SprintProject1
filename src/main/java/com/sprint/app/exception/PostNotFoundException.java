package com.sprint.app.exception;

public class PostNotFoundException extends RuntimeException{
   public PostNotFoundException (String message)
   {
	   super(message);
   }
}
