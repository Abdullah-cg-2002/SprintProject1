package com.sprint.app.exception;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import com.sprint.app.exception.CommentsByPostIdNotFoundException;
import com.sprint.app.exception.CommentsNotFoundException;
import com.sprint.app.errorresponse.ErrorResponse;
import com.sprint.app.exception.FriendNotFoundException;
import com.sprint.app.exception.GlobalExceptionHandler;
import com.sprint.app.exception.NoPendingFriendsFoundException;
import com.sprint.app.exception.PostNotFoundException;
import com.sprint.app.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
 
class GlobalExceptionTest {
 
    @InjectMocks

    private GlobalExceptionHandler globalExceptionHandler;
 
    @Mock

    private PostNotFoundException postNotFoundException;

    @Mock

    private NoPendingFriendsFoundException noPendingFriendsFoundException;

    @Mock

    private UserNotFoundException userNotFoundException;
 
    @Mock

    private CommentsByPostIdNotFoundException commentsByPostIdNotFoundException;

    @Mock

    private CommentsNotFoundException commentsNotFoundException;

    @Mock

    private FriendNotFoundException friendNotFoundException;
 
    @BeforeEach

    void setUp() {

        

        globalExceptionHandler = new GlobalExceptionHandler();

    }
 
   


    @Test

    void testHandlePostNotFoundException() {

        

        PostNotFoundException exception = new PostNotFoundException("Post not found");

       

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handlePostNotFoundException(exception);

        
        assertNotNull(response);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertEquals("Post not found", response.getBody().getMessage());

        assertNotNull(response.getBody().getTimestamp());

    }
 
   

    @Test

    void testHandleNoPendingFriendsFoundException() {

      
        NoPendingFriendsFoundException exception = new NoPendingFriendsFoundException("No pending friends found");
 
        

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNoPendingFriendsFoundException(exception);
 
       

        assertNotNull(response);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertEquals("No pending friends found", response.getBody().getMessage());

        assertNotNull(response.getBody().getTimestamp());

    }
 
   

    @Test

    void testHandleUserNotFoundException() {

       

        UserNotFoundException exception = new UserNotFoundException("User not found");
 
      
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUserNotFoundException(exception);
 
       

        assertNotNull(response);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertEquals("User not found", response.getBody().getMessage());

        assertNotNull(response.getBody().getTimestamp());

    }
 
  
    @Test

    void testHandleCommentsByPostIdNotFoundException() {

       

        CommentsByPostIdNotFoundException exception = new CommentsByPostIdNotFoundException("Comments for post not found");
 
        

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleCommentsByPostIdNotFoundException(exception);
 
       

        assertNotNull(response);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertEquals("Comments for post not found", response.getBody().getMessage());

        assertNotNull(response.getBody().getTimestamp());

    }
 
   

    @Test

    void testHandleCommentsNotFoundException() {

       

        CommentsNotFoundException exception = new CommentsNotFoundException("Comments not found");
 
       

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleCommentsNotFoundException(exception);
 
       

        assertNotNull(response);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertEquals("Comments not found", response.getBody().getMessage());

        assertNotNull(response.getBody().getTimestamp());

    }
 
   

    @Test

    void testHandleFriendNotFoundException() {

       
        FriendNotFoundException exception = new FriendNotFoundException("Friend not found");
 
     

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleFriendNotFoundException(exception);
 
       

        assertNotNull(response);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertEquals("Friend not found", response.getBody().getMessage());

        assertNotNull(response.getBody().getTimestamp());

    }

}

 