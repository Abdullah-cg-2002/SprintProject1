package com.sprint.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.LikeRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTesting {

    @Autowired
    private LikeRepo lr; // Repository for Likes

    @Autowired
    private PostRepo pr; // Repository for Posts

    @Autowired
    private UserRepo ur; // Repository for Users

    @Autowired
    private UserServiceImpl userServiceImpl; // The service you are testing

    @BeforeEach
    void setUp() {
        // Setup user and post for tests
        user = new Users();
        user.setUserID(1);
        user.setUsername("user1");
        userRepo.save(user);

        post = new Posts();
        post.setPostID(101);
        post.setUser(user);
        post.setContent("Post content by user1");
        postRepo.save(post);
    }

    @Test
    void testAddLikeToPost() {
        // Set up the Post and User for this test
        Posts post = new Posts();
        post.setContent("Content of the Test Post");
        pr.save(post); // Save post to database

        Users user = new Users();
        user.setUsername("TestUser");
        user.setEmail("testuser@example.com");
        ur.save(user); // Save user to database

        // Act: Add a like to the post by the user
        userServiceImpl.addLikeToPost(post.getPostID(), user.getUserID());

        // Assert: Verify the like is added to the database
        Likes like = lr.findAll().stream()
                .filter(l -> l.getPosts().getPostID() == post.getPostID() && l.getUser().getUserID() == user.getUserID())
                .findFirst()
                .orElse(null);

        assertNotNull(like, "The like should exist in the database after being added");
    }

    @Test
    void testRemoveLikeFromPost() {
        // Set up the Post and User for this test
        Posts post = new Posts();
        post.setContent("Content of the Test Post");
        pr.save(post); // Save post to database

        Users user = new Users();
        user.setUsername("TestUser");
        user.setEmail("testuser@example.com");
        ur.save(user); // Save user to database

        // Add a like to the post
        userServiceImpl.addLikeToPost(post.getPostID(), user.getUserID());

        // Retrieve the like that was just added
        Likes like = lr.findAll().stream()
                .filter(l -> l.getPosts().getPostID() == post.getPostID() && l.getUser().getUserID() == user.getUserID())
                .findFirst()
                .orElse(null);

        assertNotNull(like, "The like should exist in the database before deletion");

        // Act: Remove the like from the post
        userServiceImpl.removeLikeFromPost(post.getPostID(), like.getLikesID());

        // Assert: Verify the like is removed from the database
        Likes removedLike = lr.findById(like.getLikesID()).orElse(null);
        assertNull(removedLike, "The like should be removed from the database after deletion");
    }

    @Test
    @Transactional
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
    @Transactional
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
