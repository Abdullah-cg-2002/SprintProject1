package com.sprint.app.service;



import com.sprint.app.model.Comments;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.CommentRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.CommentServiceImpl;
import com.sprint.app.exception.PostNotFoundException;
import com.sprint.app.exception.CommentsNotFoundException;
import com.sprint.app.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTesting {

    @Autowired
    private CommentServiceImpl cserv;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    private Comments comment;
    private Posts post;
    private Users user;

    @BeforeEach
    void setUp() {
        // Setup user, post, and comment for tests
        user = new Users();
        user.setUserID(2);
        user.setUsername("user2");
        userRepo.save(user);

        post = new Posts();
        post.setPostID(102);
        post.setUser(user);
        post.setContent("Post content by user2");
        postRepo.save(post);

        comment = new Comments();
        comment.setCommentid(1002);
        comment.setCommentText("new Comment");
        comment.setUsers(user);
        comment.setPost(post);
        comment.setTimestamp(LocalDateTime.now());
        commentRepo.save(comment);
        System.out.println("Comments in the repo: " + commentRepo.findAll());
    }

    @Test
    void testGetAllCmtsForPost_Success() {
       
        int validPostID = 108;  
        Posts post = postRepo.findById(validPostID).orElse(null);
        assertNotNull(post, "Post should exist for the valid ID");
        List<Comments> comments = cserv.getAllCmts(validPostID);
        assertNotNull(comments, "Comments list should not be null");
        assertEquals(1, comments.size(), "There should be exactly one comment for postID 102");  
        assertEquals("Comment by user8 on user8's post", comments.get(0).getCommentText(), "The comment text should be 'Comment by user8 on user8's post'");
    }


    @Test
    void testGetAllCmtsForPost_PostNotFound() {
      
        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            cserv.getAllCmts(9999); // Non-existent postID
        });

        assertEquals("Post not found with ID: 9999", exception.getMessage());
    }

    @Test
    void testGetCmtsBYId_Success() {
        
        Comments foundComment = cserv.getCmtsBYId(comment.getCommentid());

        // Assert the result
        assertNotNull(foundComment);
        assertEquals("new Comment", foundComment.getCommentText());
    }

    @Test
    void testGetCmtsBYId_CommentNotFound() {
        
        CommentsNotFoundException exception = assertThrows(CommentsNotFoundException.class, () -> {
            cserv.getCmtsBYId(9999); // Non-existent commentID
        });

        assertEquals("Comment not found with ID: 9999", exception.getMessage());
    }

    @Test
    void testCreateCmt_Success() {
       
        Comments newComment = new Comments();
        newComment.setCommentText("New test comment");
        newComment.setUsers(user);
        newComment.setPost(post);
        newComment.setTimestamp(LocalDateTime.now());

      
        cserv.createCmt(newComment);

        
        Comments savedComment = commentRepo.findById(newComment.getCommentid()).orElse(null);
        assertNotNull(savedComment);
        assertEquals("New test comment", savedComment.getCommentText());
    }

    @Test
    void testCreateCmt_PostNotFound() {
      
        Comments newComment = new Comments();
        newComment.setCommentText("Invalid post comment");
        newComment.setUsers(user);
        newComment.setPost(new Posts()); // Invalid post

        
        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            cserv.createCmt(newComment);
        });

        assertEquals("Post with ID 0 not found.", exception.getMessage());
    }

    @Test
    void testCreateCmt_UserNotFound() {
        
        Comments newComment = new Comments();
        newComment.setCommentText("Invalid user comment");
        newComment.setUsers(new Users()); // Invalid user
        newComment.setPost(post);

       
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            cserv.createCmt(newComment);
        });

        assertEquals("User with ID 0 not found.", exception.getMessage());
    }

    @Test
    void testDeleteCmtById_Success() {
        
        cserv.deleteCmtById(comment.getCommentid());

       
        assertFalse(commentRepo.findById(comment.getCommentid()).isPresent());
    }

    @Test
    void testDeleteCmtById_CommentNotFound() {
        
        CommentsNotFoundException exception = assertThrows(CommentsNotFoundException.class, () -> {
            cserv.deleteCmtById(9999); // Non-existent commentID
        });

        assertEquals("Comment not found with ID: 9999", exception.getMessage());
    }

    @Test
    void testAddCmtSpecificPost_Success() {
        Comments newComment = new Comments();
        newComment.setCommentText("Comment for specific post");
        newComment.setUsers(user);
        newComment.setPost(post);
        newComment.setTimestamp(LocalDateTime.now());

       
        String result = cserv.addCmtSpecificPost(post.getPostID(), newComment);

        
        assertEquals("Comment added successfully", result);

       
        Comments savedComment = commentRepo.findById(newComment.getCommentid()).orElse(null);
        assertNotNull(savedComment);
        assertEquals("Comment for specific post", savedComment.getCommentText());
    }

    @Test
    void testAddCmtSpecificPost_PostNotFound() {
        // Call the method with a non-existing postID
        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
            cserv.addCmtSpecificPost(9999, comment);
        });

        assertEquals("PostID does not exist: 9999", exception.getMessage());
    }

    @Test
    void testDeleteCmtSpecificPost_Success() {
       
       cserv.deleteCmtSpecificPost(post.getPostID(), comment.getCommentid());

     
        assertFalse(commentRepo.findById(comment.getCommentid()).isPresent());
    }

    @Test
    void testDeleteCmtSpecificPost_PostNotFound() {
       
        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> {
           cserv.deleteCmtSpecificPost(9999, comment.getCommentid());
        });

        assertEquals("No post found with ID 9999", exception.getMessage());
    }
    
    
}
