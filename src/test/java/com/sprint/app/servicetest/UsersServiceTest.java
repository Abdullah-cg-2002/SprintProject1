package com.sprint.app.servicetest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.sprint.app.exception.UserException;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Users;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.*;
@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private UserRepo ur;
   
    
    @Test
    void testGetAllUsers() {
        List<Users> result = userService.getAllUsers();
        assertEquals(65, result.size());
    }

    @Test
    void testGetSpecUser_UserFound() {
      
        userService.getSpecificUser(1);
        assertEquals("user1", userService.getSpecificUser(1).getUsername());
    }

    @Test
    void testGetSpecUser_UserNotFound() {
        Exception exception = assertThrows(UserException.class, () -> {
            userService.getSpecificUser(65);
        });
        assertEquals("userid not found", exception.getMessage());
    }

    @Test
    void testSearchForUserByName_UserFound() {
        List<Users> result = userService.searchForUserByName("aadhi");
        assertEquals(2, result.size());
    }

    @Test
    void testSearchForUserByName_UserNotFound() {
        Exception exception = assertThrows(UserException.class, () -> {
            userService.searchForUserByName("testuser");
        });
        assertEquals("Username not found", exception.getMessage());
    }

    @Test
    void testAddUser_InvalidEmail() {
    	Users user = new Users();
        user.setEmail("invalid-email");
        user.setPassword("validPassword123!");
        Exception exception = assertThrows(UserException.class, () -> {
            userService.addUser(user);
        });
        assertEquals("Email not Valid", exception.getMessage());
    }
 
    @Test
    void testAddUser_InvalidPassword() {
    	Users user = new Users();
    	user.setEmail("aadhi1@gmail.com");
        user.setPassword("---"); 
    	Exception exception = assertThrows(UserException.class, () -> {
            userService.addUser(user);
        });
        assertEquals("Password not valid", exception.getMessage());
    }

    @Test
    @Transactional
    void testDeleteUser() {
        userService.deleteUser(217);
        assertTrue(ur.findById(217).isEmpty());
    }

    @Test
    void testGetAllGroupsofUser_UserFound() {
        List<Groups> result = userService.getAllGroupsofUser(1);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllGroupsofUser_UserNotFound() {
        Exception exception = assertThrows(UserException.class, () -> {
            userService.getAllGroupsofUser(65);
        });
        assertEquals("User not found", exception.getMessage());
    }
}
