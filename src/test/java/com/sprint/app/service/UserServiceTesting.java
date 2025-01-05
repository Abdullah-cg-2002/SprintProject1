package com.sprint.app.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.sprint.app.model.Notifications;
import com.sprint.app.model.Users;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.UserServiceImpl;
import com.sprint.app.success.SuccessResponseGet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest // This ensures Spring Boot context is loaded for integration tests
@Transactional // Rolls back each test to keep database state consistent
class UserServiceTesting {

	@Autowired
    private UserRepo userRepository; // Autowire the actual repository

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository); // Initialize the service with the actual repo
    }
    

    @Test
    void testUpdateUser_Success() {
        // Setup data for testing
        Users existingUser = new Users();
        existingUser.setUsername("ExistingUser");
        existingUser.setEmail("existingusers@example.com");
        existingUser.setPassword("password");

        userRepository.save(existingUser); // Save the existing user to the MySQL test database

        // Create updated user
        Users updatedUser = new Users();
        updatedUser.setUsername("UpdatedUser1");
        updatedUser.setEmail("updatedusers1@example.com");
        updatedUser.setPassword("newpassword1");

        // Perform the update
        String result = userService.updateUser(existingUser.getUserID(), updatedUser);

        // Retrieve the updated user and verify the result
        Users retrievedUser = userRepository.findById(existingUser.getUserID()).orElseThrow();
        assertEquals("UpdatedUser1", retrievedUser.getUsername());
        assertEquals("updatedusers1@example.com", retrievedUser.getEmail());
        assertEquals("newpassword1", retrievedUser.getPassword());

        // Assert the response
        assertEquals("User updated successfully", result);
    }

    @Test
    void testUpdateUser_EmailExists() {
        // Create the first user with an email
        Users user1 = new Users();
        user1.setUsername("User1");
        user1.setEmail("user@example.com");
        user1.setPassword("password");
        userRepository.save(user1);  // Save the first user

        // Create a second user with the same email
        Users user2 = new Users();
        user2.setUsername("User2");
        user2.setEmail("user@example.com");  // Same email as user1
        user2.setPassword("password");
        userRepository.save(user2);  // Save the second user

        // Attempt to update user2's email to the same email as user1
        user2.setEmail("user@example.com");

        // Assert that a RuntimeException is thrown due to the email conflict
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(user2.getUserID(), user2);
        });

        // Assert the exception message is as expected
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void testGetNotificationByUserID_Success() {
        // Setup user and notifications
        Users user = new Users();
        user.setUsername("User1");
        user.setEmail("user1@example.com");
        Notifications notification1 = new Notifications();
        notification1.setContent("Message 1");
        Notifications notification2 = new Notifications();
        notification2.setContent("Message 2");

        // Save user and notifications in the database
        user.getNotification().add(notification1);
        user.getNotification().add(notification2);
        userRepository.save(user);

        SuccessResponseGet response = userService.getNotificationByUserID(user.getUserID());

        // Assert successful response
        assertEquals("success", response.getStatus());
        assertEquals(2, response.getData().size());
    }

    @Test
    void testGetNotificationByUserID_NoNotifications() {
        // Setup user without notifications
        Users user = new Users();
        user.setUsername("User2");
        user.setEmail("user2@example.com");
        userRepository.save(user);

        // Try to get notifications
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getNotificationByUserID(user.getUserID());
        });

        // Assert the exception is thrown
        assertEquals("Notification not found for the given user ID", exception.getMessage());
    }
}
