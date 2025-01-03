package com.sprint.app.controllertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sprint.app.controller.UserRestController;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Users;
import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

import org.junit.jupiter.api.extension.ExtendWith;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for UserController using Mockito with ExtendWith annotation.
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserRestController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<Users> users = Arrays.asList(new Users(), new Users());
        when(userService.getAllUsers()).thenReturn(users);

        SuccessResponseGet response = userController.getAllUsers();

        assertEquals("success", response.getStatus());
        assertEquals(2, response.getData().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetSpecificUser() {
        Users user = new Users();
        user.setUsername("testUser");
        when(userService.getSpecificUser(1)).thenReturn(user);

        SuccessResponseGet response = userController.getSpecificUser(1);

        assertEquals("success", response.getStatus());
        assertEquals("testUser", ((Users) response.getData().get(0)).getUsername());
        verify(userService, times(1)).getSpecificUser(1);
    }

    @Test
    void testSearchForUserByName() {
        List<Users> users = Arrays.asList(new Users(), new Users());
        when(userService.searchForUserByName("test")).thenReturn(users);

        SuccessResponseGet response = userController.searchForUserByName("test");

        assertEquals("success", response.getStatus());
        assertEquals(2, response.getData().size());
        verify(userService, times(1)).searchForUserByName("test");
    }

    @Test
    void testAddUser() {
        Users user = new Users();

        SuccessResponse response = userController.addUser(user);

        assertEquals("Success", response.getStatus());
        assertEquals("User added Successfully", response.getMessage());
        verify(userService, times(1)).addUser(user);
    }

    @Test
    void testDeleteUser() {
        SuccessResponse response = userController.deleteUser(1);

        assertEquals("success", response.getStatus());
        assertEquals("User removed successfully", response.getMessage());
        verify(userService, times(1)).deleteUser(1);
    }

    @Test
    void testGetAllGroupsofUser() {
        List<Groups> groups = new ArrayList<>();
        when(userService.getAllGroupsofUser(1)).thenReturn(groups);

        SuccessResponseGet response = userController.getAllGroupsofUser(1);

        assertEquals("success", response.getStatus());
        assertEquals(0, response.getData().size());
        verify(userService, times(1)).getAllGroupsofUser(1);
    }
}
