package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.dto.PostsDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts;
import com.sprint.app.services.LikesService;
import com.sprint.app.services.PostService;
import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

/**
 * REST controller for managing posts in the application.
 * This class handles HTTP requests related to post operations such as creating, retrieving, updating, and deleting posts.
 * It also manages likes related to posts.
 */
@RestController
@RequestMapping("/api")
public class PostRestController {

    @Autowired
    private PostService ps;
    
    @Autowired
    private UserService us;
    
    @Autowired
    private LikesService ls;

    private static final Logger logger = LoggerFactory.getLogger(PostRestController.class);

    /**
     * Adds a new post.
     * 
     * Logs the request to add a post and the action performed.
     * 
     * @param post The DTO containing the post details to be added.
     */
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse addPost(@Valid @RequestBody PostsDTO post) {
        logger.info("Request to add a new post with title: {}", post.getContent());

        SuccessResponse sr = new SuccessResponse();
        ps.savepost(post);  // Save the post

        sr.setStatus("Success");
        sr.setMessage("Post with title '" + post.getContent() + "' created successfully");  // Setting the response message

        logger.info("Post with title '{}' created successfully.", post.getContent());
        return sr;
    }



    /**
     * Retrieves a post by its ID.
     * 
     * Logs the postId received and the action performed.
     * 
     * @param postId The ID of the post to retrieve.
     * @return The post with the specified ID.
     */
    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponseGet getPostById(
            @PathVariable("postId") @Positive(message = "Post ID must be positive") int postId) {
        logger.info("Request to fetch post with ID: {}", postId);

        SuccessResponseGet srg = new SuccessResponseGet();
        Posts post = ps.getByPostID(postId);
        List<Object> postList = new ArrayList<>();
        postList.add(post);  // Add the post to the response data

        srg.setStatus("Success");
        srg.setData(postList);

        logger.info("Returning post with ID: {}", postId);
        return srg;
    }


    /**
     * Retrieves all posts.
     * 
     * Logs the action of fetching all posts.
     * 
     * @return A list of all posts.
     */
    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponseGet getAllPosts() {
        logger.info("Request to fetch all posts received.");

        SuccessResponseGet srg = new SuccessResponseGet();
        List<Posts> posts = ps.getAllPosts();
        List<Object> postsList = new ArrayList<>(posts);  // Add the posts list to the response data

        srg.setStatus("Success");
        srg.setData(postsList);

        logger.info("Returning a list of {} posts.", posts.size());
        return srg;
    }


    /**
     * Updates an existing post.
     * 
     * Logs the request to update a post and the action performed.
     * 
     * @param postId The ID of the post to be updated.
     * @param post The updated post details.
     */
    @PutMapping("/post/update/{postId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SuccessResponse updatePost(
            @PathVariable("postId") @Positive(message = "Post ID must be positive") int postId,
            @Valid @RequestBody Posts post) {
        logger.info("Request to update post with ID: {}", postId);

        SuccessResponse sr = new SuccessResponse();
        ps.updatePosts(postId, post);       

        // Set response data
        sr.setStatus("Success");
        sr.setMessage("Post with ID: " + postId + " updated successfully.");


        logger.info("Post with ID: {} updated successfully.", postId);
        return sr;
    }


    /**
     * Deletes a post.
     * 
     * Logs the request to delete a post and the action performed.
     * 
     * @param postId The ID of the post to be deleted.
     */
    @DeleteMapping("/post/delete/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse deletePost(@PathVariable("postId") @Positive(message = "Post ID must be positive") int postId) {
        logger.info("Request to delete post with ID: {}", postId);

        SuccessResponse sr = new SuccessResponse();
        ps.deletePosts(postId);

        // Set response data
        
        sr.setStatus("Success");
        sr.setMessage("Post with ID: " + postId + " deleted successfully.");

        logger.info("Post with ID: {} deleted successfully.", postId);
        return sr;
    }


    /**
     * Adds a like to a post.
     * 
     * Logs the postId and userId received, as well as the action performed.
     * 
     * @param postId The ID of the post to like.
     * @param userId The ID of the user liking the post.
     */
    @PostMapping("/posts/{postId}/likes/add/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse addLike(
            @PathVariable("postId") @Positive(message = "Post ID must be positive") int postId,
            @PathVariable("userId") @Positive(message = "User ID must be positive") int userId) {
        logger.info("Request to add like to post with ID: {} from user with ID: {}", postId, userId);

        SuccessResponse sr= new SuccessResponse();
        us.addLikeToPost(postId, userId);

        // Set response data
        sr.setStatus("Success");
        sr.setMessage("Like added to post by userID: " + userId);

        logger.info("Like added to post with ID: {} from user with ID: {}", postId, userId);
        return sr;
    }


    /**
     * Retrieves likes for a specific post.
     * 
     * Logs the postId received and the action performed.
     * 
     * @param postId The ID of the post for which likes are being retrieved.
     * @return A list of likes for the specified post.
     */
  
    @GetMapping("/posts/{postId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponseGet getLikesByPostId(
    		@PathVariable("postId") @Positive(message = "Post ID should be positive")int postId) {
    	logger.info("Retriving the likes by using post ID",postId);


       SuccessResponseGet srg = new SuccessResponseGet();
       List<Likes> likes = ps.getLikesByPostId(postId);
       List<Object> likesList = new ArrayList<>(likes);
       srg.setStatus("Success");
       srg.setData(likesList);
       
       logger.info("Returning the likes");
	   return srg;
    	
    
    }
   
    
    
    /**
     * Removes a like from a post.
     * 
     * Logs the postId and likesId received, as well as the action performed.
     * 
     * @param postId The ID of the post from which the like is being removed.
     * @param likesId The ID of the like to remove.
     */
    @DeleteMapping("/posts/{postId}/likes/remove/{likesId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse removeLike(
            @PathVariable("postId") @Positive(message = "Post ID must be positive") int postId,
            @PathVariable("likesId") @Positive(message = "Likes ID must be positive") int likesId) {
        logger.info("Request to remove like with ID: {} from post with ID: {}", likesId, postId);

        SuccessResponse sr = new SuccessResponse();
        us.removeLikeFromPost(postId, likesId);

        sr.setStatus("Success");
        sr.setMessage("Like removed from post");

        logger.info("Like with ID: {} removed from post with ID: {}", likesId, postId);
        return sr;
    }


    /**
     * Retrieves all likes on posts created by a specific user.
     * 
     * Logs the userId received and the action performed.
     * 
     * @param userId The ID of the user for whom likes are being retrieved.
     * @return A list of likes on posts created by the user.
     */
    @GetMapping("/user/{userId}/posts/likes")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponseGet getAllLikesOnPostsByUserId(
            @PathVariable("userId") @Positive(message = "User ID must be positive") int userId) {
        logger.info("Request to fetch all likes on posts by user with ID: {}", userId);

        SuccessResponseGet srg = new SuccessResponseGet();
        List<Likes> likes = ls.getLikeByspecfuser(userId);
        List<Object> likesList = new ArrayList<>(likes);  // Add the likes list to the response data

        srg.setStatus("Success");
        srg.setData(likesList);

        logger.info("Returning {} likes for posts created by user with ID: {}", likes.size(), userId);
        return srg;
    }

}



    
