package com.sprint.app.service;

import com.sprint.app.exception.UserNotFoundException;
import com.sprint.app.model.Comments;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.serviceimpl.UserServiceImpl;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.repo.PostRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTesting {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    private Users user;
    private Posts post;

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
    void testGetAllPostsUsr_Success() {
        int validUserID = 7;
        List<Posts> posts = userService.getAllPostsUsr(validUserID);
        assertNotNull(posts, "Posts list should not be null");

        // Ensure the list is not empty before accessing its elements

        // If it's not empty, you can safely check the content
        assertEquals(1, posts.size(), "There should be exactly one post for user with ID 1");
        assertEquals("Post content by user7", posts.get(0).getContent(), "The post content should match.");
    }


    @Test
    void testGetAllPostsUsr_UserNotFound() {
       
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.getAllPostsUsr(9999);  
        });

       
        assertEquals("User not found with ID: 9999", thrown.getMessage());
    }


    @Test
    void testGetAllCmtsPst_Success() {
        
        Comments comment = new Comments();
        comment.setCommentText("This is a comment");
        comment.setPost(post);
        comment.setUsers(user);
        

       
        List<Comments> comments = userService.getAllCmtsPst(2); // User ID 2
        assertNotNull(comments, "Comments list should not be null");
        assertEquals(1, comments.size(), "There should be exactly one comment for user with ID 2");
        assertEquals("updated comment for the postID 102", comments.get(0).getCommentText(), "The comment text should match.");
    }

    @Test
    void testGetAllCmtsPst_UserNotFound() {
    	 UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
             userService.getAllCmtsPst(1987);
         });

        
         assertEquals("User not found with ID: 1987", thrown.getMessage());
     }
    

    @Test
    void testGetPendingFrndReq_Success() {
        // Simulate pending friend requests
        // Assuming you have a way to add friend requests in your setup.

        // Call method to get pending friend requests for a user
        List<Object> pendingRequests = userService.getPendingFrndReq(2); // User ID 2
        assertNotNull(pendingRequests, "Pending friend requests should not be null");
        // Add specific assertions based on your data for pending requests
    }

    @Test
    void testGetPendingFrndReq_UserNotFound() {
    	 UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
             userService.getPendingFrndReq(1000);
         });

        
         assertEquals("User not found with ID: 1000", thrown.getMessage());
     }
    

    @Test
    void testGetAllPostsUsr_UserWithNoPosts() {
        Users newUser = new Users();
        newUser.setUserID(3);
        newUser.setUsername("user3");
        userRepo.save(newUser);

        List<Posts> posts = userService.getAllPostsUsr(3);
        assertNotNull(posts, "Posts list should not be null");
        assertEquals(0, posts.size(), "There should be no posts for user with ID 3");
    }

    @Test
    void testGetAllCmtsPst_NoComments() {
        List<Comments> comments = userService.getAllCmtsPst(1); // User with no comments
        assertNotNull(comments, "Comments list should not be null");
        assertEquals(0, comments.size(), "There should be no comments for the user with ID 65");
    }
}
