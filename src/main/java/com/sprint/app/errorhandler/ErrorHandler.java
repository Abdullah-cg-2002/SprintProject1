package com.sprint.app.errorhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sprint.app.errorresponse.ErrorResponse;
import com.sprint.app.exception.FriendException;
import com.sprint.app.exception.MessageException;
import com.sprint.app.exception.UserException;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleUserException(UserException exe)
	{
		ErrorResponse err= new ErrorResponse();
		err.setTimestamp(LocalDateTime.now());
		err.setStatus(HttpStatus.NOT_FOUND);
		err.setMessage(exe.getMessage());
		return new ResponseEntity<ErrorResponse>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleMessageException(MessageException exe)
	{
		ErrorResponse err= new ErrorResponse();
		err.setTimestamp(LocalDateTime.now());
		err.setStatus(HttpStatus.NOT_FOUND);
		err.setMessage(exe.getMessage());
		return new ResponseEntity<ErrorResponse>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleFriendException(FriendException exe)
	{
		ErrorResponse err= new ErrorResponse();
		err.setTimestamp(LocalDateTime.now());
		err.setStatus(HttpStatus.NOT_FOUND);
		err.setMessage(exe.getMessage());
		return new ResponseEntity<ErrorResponse>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleControllerException(RuntimeException exe)
	{
		ErrorResponse err= new ErrorResponse();
		err.setTimestamp(LocalDateTime.now());
		err.setStatus(HttpStatus.NOT_FOUND);
		err.setMessage(exe.getMessage());
		return new ResponseEntity<ErrorResponse>(err,HttpStatus.NOT_FOUND);
	}

}
