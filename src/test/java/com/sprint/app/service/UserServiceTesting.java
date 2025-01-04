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
}
