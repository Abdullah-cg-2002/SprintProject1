package com.sprint.app.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sprint.app.errorresponse.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CommentsByPostIdNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCommentsByPostIdNotFoundException(CommentsByPostIdNotFoundException exception)
	{
		ErrorResponse error= new ErrorResponse();
		error.setMessage(exception.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CommentsNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCommentsNotFoundException(CommentsNotFoundException exception)
	{
		ErrorResponse error = new ErrorResponse();
		error.setMessage(exception.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FriendNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFriendNotFoundException(FriendNotFoundException exception)
	{
		ErrorResponse error = new ErrorResponse();
		error.setMessage(exception.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoPendingFriendsFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoPendingFriendsFoundException(NoPendingFriendsFoundException exception)
	{
		ErrorResponse error = new ErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setMessage(exception.getMessage());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException exception)
	{
		ErrorResponse error = new ErrorResponse();
		error.setMessage(exception.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception)
	{
		ErrorResponse error = new ErrorResponse();
		error.setMessage(exception.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
}
