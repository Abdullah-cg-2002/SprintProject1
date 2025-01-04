package com.sprint.app.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.LikeRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.LikesServiceImpl;

import java.util.List;

@SpringBootTest
public class LikesServiceTesting {

    @Autowired
    private LikesServiceImpl likesService;

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    private Users testUser;
    private Posts testPost;

    // Initializing User and Post objects before each test
    @BeforeEach
    public void setUp() {
        // Create and save a test user
        testUser = new Users();
        testUser.setUsername("testUser");
        testUser.setPassword("password123");
        testUser = userRepo.save(testUser);

        // Create and save a test post
        testPost = new Posts();
        testPost.setContent("This is a test post.");
        testPost.setUser(testUser);
        testPost = postRepo.save(testPost);
    }

    @Test
    public void testSavelike() {
        // Test saving a like
        Likes savedLike = likesService.savelike(0, testPost.getPostID(), testUser.getUserID());
        assertNotNull(savedLike);
        assertEquals(testUser.getUserID(), savedLike.getUser().getUserID());
        assertEquals(testPost.getPostID(), savedLike.getPosts().getPostID());
    }

    @Test
    public void testGetLikeByPostID() {
        // Add a like first
        likesService.savelike(0, testPost.getPostID(), testUser.getUserID());

        // Test retrieving likes by Post ID
        List<Likes> likesList = likesService.getLikeByPostID(testPost.getPostID());
        assertNotNull(likesList);
        assertEquals(1, likesList.size());
        assertEquals(testPost.getPostID(), likesList.get(0).getPosts().getPostID());
    }

    @Test
    public void testGetLikeByspecfuser() {
        // Add a like
        likesService.savelike(0, testPost.getPostID(), testUser.getUserID());

        // Verify the like is saved in the database
        List<Likes> likesList = likesService.getLikeByspecfuser(testUser.getUserID());
        assertNotNull(likesList);
        assertEquals(1, likesList.size(), "There should be 1 like for the user.");
        assertEquals(testUser.getUserID(), likesList.get(0).getUser().getUserID());
    }

    @Test
    public void testGetByLikeID() {
        // Add a like first
        Likes savedLike = likesService.savelike(0, testPost.getPostID(), testUser.getUserID());

        // Test retrieving a like by its ID
        Likes like = likesService.getByLikeID(savedLike.getLikesID());
        assertNotNull(like);
        assertEquals(savedLike.getLikesID(), like.getLikesID());
    }

    @Test
    public void testDeleteLikes() {
        // Add a like first
        Likes savedLike = likesService.savelike(0, testPost.getPostID(), testUser.getUserID());

        // Test deleting a like
        likesService.deleteLikes(savedLike.getLikesID());

        // Verify the like is deleted
        assertThrows(RuntimeException.class, () -> likesService.getByLikeID(savedLike.getLikesID()));
    }

    @Test
    public void testGetLikesByUser() {
        // Add a like first
        likesService.savelike(0, testPost.getPostID(), testUser.getUserID());

        // Test retrieving likes by user
        List<Likes> allLikes = likesService.getLikesByUser(testUser);
        assertNotNull(allLikes);
        assertEquals(1, allLikes.size());
        assertEquals(testUser.getUserID(), allLikes.get(0).getUser().getUserID());
    }




    @Test
    public void testGetLikeByPostIDWithNoLikes() {
        // Test getting likes for a post that has no likes
        assertThrows(RuntimeException.class, () -> likesService.getLikeByPostID(999)); // assuming post 999 does not exist
    }
}
