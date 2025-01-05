package com.sprint.app.service;

import com.sprint.app.dto.PostsDTO;
import com.sprint.app.exception.PostException;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.LikeRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PostServiceTesting {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LikeRepo likeRepo;

    @Test
    void testSavePost_Success() {
        // Setting up the test user
        Users testUser = new Users();
        testUser.setID(1);
        testUser.setUsername("testUser");
        userRepo.save(testUser);

        // Setting up a PostDTO for saving
        PostsDTO postDTO = new PostsDTO();
        postDTO.setContent("This is a test post.");
        postDTO.setUser(testUser);

        // Call the service to save a post
        Posts savedPost = postService.savepost(postDTO);

        // Verify the post was saved correctly
        assertNotNull(savedPost, "The post should be saved.");
        assertEquals("This is a test post.", savedPost.getContent(), "The content of the post should match.");
        assertEquals(testUser.getUserID(), savedPost.getUser().getUserID(), "The user ID should match.");
    }

    @Test
    void testGetByPostID_Success() {
        // Setting up the test user
        Users testUser = new Users();
        testUser.setID(1);
        testUser.setUsername("testUser");
        userRepo.save(testUser);

        // Setting up a PostDTO for saving
        PostsDTO postDTO = new PostsDTO();
        postDTO.setContent("This is a test post.");
        postDTO.setUser(testUser);
        
        // Save a post
        Posts savedPost = postService.savepost(postDTO);

        // Retrieve the post by ID
        Posts retrievedPost = postService.getByPostID(savedPost.getPostID());

        // Verify that the post was retrieved correctly
        assertNotNull(retrievedPost, "The retrieved post should not be null.");
        assertEquals(savedPost.getPostID(), retrievedPost.getPostID(), "The post ID should match.");
    }

    @Test
    void testGetByPostID_PostNotFound() {
        // Setting up the test user
        Users testUser = new Users();
        testUser.setID(1);
        testUser.setUsername("testUser");
        userRepo.save(testUser);

        // Setting up a PostDTO for saving
        PostsDTO postDTO = new PostsDTO();
        postDTO.setContent("This is a test post.");
        postDTO.setUser(testUser);

        // Try to retrieve a non-existent post
        assertThrows(PostException.class, () -> {
            postService.getByPostID(9999);  // A post ID that doesn't exist
        });
    }

    @Test
    void testUpdatePosts_Success() {
        // Setting up the test user
        Users testUser = new Users();
        testUser.setID(1);
        testUser.setUsername("testUser");
        userRepo.save(testUser);

        // Setting up a PostDTO for saving
        PostsDTO postDTO = new PostsDTO();
        postDTO.setContent("This is a test post.");
        postDTO.setUser(testUser);
        
        // Save the post
        Posts savedPost = postService.savepost(postDTO);

        // Update the post content
        savedPost.setContent("Updated post content.");
        Posts updatedPost = postService.updatePosts(savedPost.getPostID(), savedPost);

        // Verify the post was updated correctly
        assertNotNull(updatedPost, "The post should be updated.");
        assertEquals("Updated post content.", updatedPost.getContent(), "The content of the post should be updated.");
    }

    @Test
    void testDeletePosts_Success() {
        // Setting up the test user
        Users testUser = new Users();
        testUser.setID(1);
        testUser.setUsername("testUser");
        userRepo.save(testUser);

        // Setting up a PostDTO for saving
        PostsDTO postDTO = new PostsDTO();
        postDTO.setContent("This is a test post.");
        postDTO.setUser(testUser);
        
        // Save a post
        Posts savedPost = postService.savepost(postDTO);

        // Delete the post
        postService.deletePosts(savedPost.getPostID());

        // Verify that the post was deleted
        assertFalse(postRepo.findById(savedPost.getPostID()).isPresent(), "The post should be deleted.");
    }

    @Test
    void testDeletePosts_PostNotFound() {
        // Setting up the test user
        Users testUser = new Users();
        testUser.setID(1);
        testUser.setUsername("testUser");
        userRepo.save(testUser);

        // Setting up a PostDTO for saving
        PostsDTO postDTO = new PostsDTO();
        postDTO.setContent("This is a test post.");
        postDTO.setUser(testUser);

        // Try to delete a non-existent post
        assertThrows(PostException.class, () -> {
            postService.deletePosts(9999);  // A post ID that doesn't exist
        });
    }

    @Test
    void testGetLikesByPostId_Success() {
        // Setting up the test user
        Users testUser = new Users();
        testUser.setID(1);
        testUser.setUsername("testUser");
        userRepo.save(testUser);

        // Setting up a PostDTO for saving
        PostsDTO postDTO = new PostsDTO();
        postDTO.setContent("This is a test post.");
        postDTO.setUser(testUser);

        // Save a post
        Posts savedPost = postService.savepost(postDTO);

        // Add a like (you would need to create like entities in the setup as well)

        // Get likes for the saved post
        List<Likes> likes = postService.getLikesByPostId(savedPost.getPostID());

        // Verify the likes list is not empty (this assumes you have like entities associated with posts)
        assertNotNull(likes, "Likes list should not be null");
        assertTrue(likes.isEmpty(), "Likes list should be empty initially");
    }

    @Test
    void testGetAllPosts_Success() {
        // Setting up the test user
        Users testUser = new Users();
        testUser.setID(1);
        testUser.setUsername("testUser");
        userRepo.save(testUser);

        // Setting up a PostDTO for saving
        PostsDTO postDTO = new PostsDTO();
        postDTO.setContent("This is a test post.");
        postDTO.setUser(testUser);

        // Get all posts
        List<Posts> allPosts = postService.getAllPosts();

        // Verify that posts are retrieved correctly
        assertNotNull(allPosts, "Posts list should not be null.");
        assertTrue(allPosts.size() > 0, "There should be at least one post.");
    }

    @Test
    void testGetAllPosts_PostsNotFound() {
        // Setting up the test user
        Users testUser = new Users();
        testUser.setID(1);
        testUser.setUsername("testUser");
        userRepo.save(testUser);

        // Setting up a PostDTO for saving
        PostsDTO postDTO = new PostsDTO();
        postDTO.setContent("This is a test post.");
        postDTO.setUser(testUser);
        
        // Delete all posts
        postRepo.deleteAll();

        // Try to get all posts when none exist
        assertThrows(PostException.class, () -> {
            postService.getAllPosts();  // No posts available
        });
    }
}
