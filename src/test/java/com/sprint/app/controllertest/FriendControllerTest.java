package com.sprint.app.controllertest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.sprint.app.controller.FriendRestController;
import com.sprint.app.model.Friends;
import com.sprint.app.services.FriendService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

/**
 * Unit tests for FriendController using Mockito.
 */
class FriendControllerTest {

    @InjectMocks
    private FriendRestController friendController;

    @Mock
    private FriendService friendService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFrdsUsr_Success() {
        int userID = 1;
        Set<Friends> friends = new HashSet<>();

        when(friendService.getAllFrnds(userID)).thenReturn(friends);

        SuccessResponseGet response = friendController.getFrdsUsr(userID);

        assertEquals("success", response.getStatus());
        assertEquals(0, response.getData().size());
        verify(friendService, times(1)).getAllFrnds(userID);
    }

    @Test
    void testAddFrnd_Success() {
        int userID = 1;
        int friendID = 4;
        String message = "Friend added successfully.";

        when(friendService.addFrnd(userID, friendID)).thenReturn(message);

        SuccessResponse response = friendController.addFrnd(userID, friendID);

        assertEquals("success", response.getStatus());
        assertEquals(message, response.getMessage());
        verify(friendService, times(1)).addFrnd(userID, friendID);
    }

    @Test
    void testAddFrnd_Failure() {
        int userID = 1;
        int friendID = 4;

        when(friendService.addFrnd(userID, friendID)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Friend cannot be added"));

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            friendController.addFrnd(userID, friendID);
        });

        assertEquals("400 BAD_REQUEST \"Friend cannot be added\"", exception.getMessage());
    }

    @Test
    void testDeleteFrd_Success() {
        int userID = 1;
        int friendID = 2;
        String message = "Friend deleted successfully.";

        when(friendService.deleteFrnd(userID, friendID)).thenReturn(message);

        SuccessResponse response = friendController.deleteFrd(userID, friendID);

        assertEquals("success", response.getStatus());
        assertEquals(message, response.getMessage());
        verify(friendService, times(1)).deleteFrnd(userID, friendID);
    }

    @Test
    void testDeleteFrd_Failure() {
        int userID = 1;
        int friendID = 2;

        when(friendService.deleteFrnd(userID, friendID)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend not found"));

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            friendController.deleteFrd(userID, friendID);
        });

        assertEquals("404 NOT_FOUND \"Friend not found\"", exception.getMessage());
    }
}
