package com.sprint.app.errorhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sprint.app.errorresponse.ErrorResponse;

@ControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleQuesListException(RuntimeException exe)
	{
		ErrorResponse err= new ErrorResponse();
		err.setTimestamp(LocalDateTime.now());
		err.setStatus( HttpStatus.NOT_FOUND);
		err.setMessage( exe.getMessage());
		return new ResponseEntity<ErrorResponse>(err,HttpStatus.NOT_FOUND);
	}

}
