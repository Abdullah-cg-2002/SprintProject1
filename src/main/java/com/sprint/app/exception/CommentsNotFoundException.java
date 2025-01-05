package com.sprint.app.exception;

public class CommentsNotFoundException extends RuntimeException{
      public CommentsNotFoundException(String message)
      {
      super(message);
      }
}
