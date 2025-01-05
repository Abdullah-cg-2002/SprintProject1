package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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

import com.sprint.app.dto.CommentDTO;
import com.sprint.app.model.Comments;
import com.sprint.app.model.Messages;
import com.sprint.app.services.CommentService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

/**
 * REST Controller for handling operations related to Comments in the application.
 * Provides endpoints for creating, updating, retrieving, and deleting comments.
 */

@RestController
@RequestMapping("/api/")
public class CommentRestController {
	
	@Autowired
	private CommentService cserv;
	
	 private static final Logger logger = LoggerFactory.getLogger(CommentRestController.class);
	
	/**
     * Retrieves all comments for a specific post.
     * 
     * @param postID the ID of the post to fetch comments for
     * @return a {@link SuccessResponseGet} containing the list of comments for the post
     */
	@GetMapping("posts/{postID}/comments")
	public SuccessResponseGet getCmtsofPost(@PathVariable int postID)
	{
		 logger.info("Received request to fetch comments for post with ID: {}", postID);
		SuccessResponseGet srg=new SuccessResponseGet();
		List<Object> lobj=new ArrayList<>();
		lobj.addAll(cserv.getAllCmts(postID));
		srg.setStatus("Success");
		srg.setData(lobj);
		logger.info("Returning response with {} comments for post with ID: {}", lobj.size(), postID);
		return srg;
	}

	
	/**
     * Retrieves all comments from the system.
     * 
     * @return a {@link SuccessResponseGet} containing all comments in the system
     */
	
	
	@GetMapping("comments")
	public SuccessResponseGet getAllCmts()
	{
		 logger.info("Received request to fetch all comments from the system");
		SuccessResponseGet srg=new SuccessResponseGet();
		List<Object> lobj=new ArrayList<>();
		lobj.addAll(cserv.getAllCmts());
		srg.setStatus("Success");
		srg.setData(lobj);
		logger.info("Returning response with {} comments from the system", lobj.size());
		return srg;
	}
	
	
	/**
	 * Retrieves a specific comment by its ID.
	 * 
	 * This method takes a comment ID as a path variable and returns the comment associated with that ID.
	 * The response is wrapped in a {@link SuccessResponseGet} object containing the comment data.
	 * 
	 * @param commentID the ID of the comment to retrieve
	 * @return a {@link SuccessResponseGet} containing the status of the operation and the comment data
	 */
	@GetMapping("comments/{commentID}")
	public SuccessResponseGet getCmtsById(@PathVariable int commentID)
	{
		 logger.info("Received request to fetch comment with ID: {}", commentID);
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> lobj = new ArrayList<>();
		lobj.add(cserv.getCmtsBYId(commentID));
		srg.setStatus("Success");
		srg.setData(lobj);
		 logger.info("Returning response with comment ID: {}", commentID);
		return srg;
	
	}
	
	
	 /**
     * Updates an existing comment by its ID.
     * 
     * @param commentID the ID of the comment to update
     * @param cmts the {@link Comments} object containing the updated information
     * @return a {@link SuccessResponse} indicating the result of the update operation
     */
	
	@PutMapping("/comments/{commentID}")
	public SuccessResponse updateCmts(@PathVariable int commentID,@Valid @RequestBody Comments cmts)
	{
		logger.info("Request to update the comment by commentID {}",commentID);
		cserv.updateCmt(commentID, cmts);
		SuccessResponse sr = new SuccessResponse();
		sr.setMessage("Comment updated Successfully");
		sr.setStatus("Success");
		logger.info("Updated the Comment Successfully by CommentID",commentID);
		return sr;
	}
	
	/**
     * Deletes a comment by its ID.
     * 
     * @param commentID the ID of the comment to delete
     * @return a {@link SuccessResponse} indicating the result of the delete operation
     */
	
	@DeleteMapping("comments/{commentID}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SuccessResponse delectCmts(@PathVariable int commentID)
	{
		logger.info("Received request to delete comment with ID: {}", commentID);
		cserv.deleteCmtById(commentID);
		SuccessResponse sr = new SuccessResponse();
		sr.setMessage("Comment is Successfully Deleted");
		sr.setStatus("Success");
		logger.info("Comment with ID: {} deleted successfully", commentID);
        
		return sr;
	}
	
	 /**
     * Deletes a comment from a specific post by its ID.
     * 
     * @param commentID the ID of the comment to delete
     * @param postID the ID of the post to delete the comment from
     * @return a {@link SuccessResponse} indicating the result of the delete operation
     */
	@DeleteMapping("posts/{postID}/comments/{commentID}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SuccessResponse deleteCmtSpecificPost(@PathVariable int commentID ,@PathVariable int postID)
	{
		logger.info("Received request to delete comment with ID: {} from post with ID: {}", commentID, postID);
		cserv.deleteCmtSpecificPost(postID, commentID);
		SuccessResponse sr=new SuccessResponse();
		sr.setMessage("Comment is Successfully deleted");
		sr.setStatus("Success");
		 logger.info("Comment with ID: {} deleted from post with ID: {}", commentID, postID);
		return sr;
		
	}
	
	/**
     * Creates a new comment for a specific post.
     * 
     * @param postID the ID of the post to create a comment for
     * @param cmts the {@link Comments} object containing the comment details
     * @return a {@link SuccessResponse} indicating the result of the creation operation
     */
	
	@PostMapping("posts/{postID}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse sendMsg(@PathVariable int postID, @Valid @RequestBody  Comments cmts)
	{
		logger.info("Received request to create a new comment for post with ID: {}", postID);
		String comment = cserv.addCmtSpecificPost(postID, cmts);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("sucess");
		sr.setMessage(comment);
		logger.info("New comment created for post with ID: {}", postID);
		return sr;
	}
	
	/**
     * Creates a new comment in the system.
     * 
     * @param cmtdto the {@link CommentDTO} object containing the comment details
     * @return a {@link SuccessResponse} indicating the result of the creation operation
     */
	@PostMapping("comments")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse createCmt(@Valid @RequestBody  CommentDTO cmtdto)
	{
		logger.info("Received request to create a new comment in the system");
		Comments cmt = new Comments();
		cmt.setCommentText(cmtdto.getComment_text());
		cmt.setUsers(cmtdto.getUsers());
		cmt.setPost(cmtdto.getPost());
		cserv.createCmt(cmt);
		SuccessResponse sr = new SuccessResponse();
		sr.setMessage("Comment added Successfully");
		sr.setStatus("Success");
		logger.info("New comment created in the system");
		return sr;
	}
}