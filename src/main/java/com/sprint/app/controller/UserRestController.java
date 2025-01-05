package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.services.FriendService;
import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponseGet;
import com.sprint.app.model.Comments;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Posts;

@RestController
@RequestMapping("/api/")

public class UserRestController {
	
	@Autowired
	private UserService us;
	

	
	 private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	/*@PostMapping("users/{userID}/messages/send/{frdID}")
	@ResponseStatus(HttpStatus.CREATED)
	public void sendMsgFrnd(@PathVariable int userID,@PathVariable int frdID)
	{
		us.sendMsgFrnd(userID, frdID);
	}
	
	@PostMapping("users/{userID}/friend-request/send/{frdID}")
	@ResponseStatus(HttpStatus.CREATED)
	public void sendFrdReq(@PathVariable int userID,@PathVariable int frdID)
	{
		us.sendFrdReq(userID, frdID);
	}
	
	@GetMapping("users/{userID}/messages/{otherID}")
	public List<Messages> getMsgBetUsers(@PathVariable int userID, @PathVariable int otherID)
	{
		return us.msgBtwUsers(userID, otherID);
	}
	
	@GetMapping("users/{userID}/posts/likes")
	public List<Likes> getAllLikes(@PathVariable int userID)
	{
		return us.getAllLikesPst(userID);
	}
	
	@GetMapping("users/{userID}/likes")
	public List<Likes> getAllLikesByUser(@PathVariable int userID)
	{
		return us.getAllLikesUsr(userID);
	}*/

	
	 
	 
	 
	 /**
	  * Fetches all posts created by a specific user based on the provided user ID.
	  * 
	  * This method retrieves a list of posts associated with the given user from the service layer
	  * and returns them in a structured response format indicating the status of the operation.
	  * 
	  * @param userID The unique identifier of the user whose posts are to be fetched.
	  * @return A {@link SuccessResponseGet} containing the status of the operation and a list of posts
	  *         created by the specified user.
	  */
	
	@GetMapping("users/{userID}/posts")
	public SuccessResponseGet getAllPostsByUser(@PathVariable int userID)
	{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> lobj = new ArrayList<>();
		lobj.addAll(us.getAllPostsUsr(userID));
		srg.setStatus("Success");
		srg.setData(lobj);
		return srg;
	   
	}
	
	/**
	 * Fetches all comments on posts created by a specific user based on the provided user ID.
	 * 
	 * This method retrieves a list of comments associated with the posts of the given user 
	 * and returns them in a structured response format indicating the status of the operation.
	 * 
	 * @param userID The unique identifier of the user whose post comments are to be fetched.
	 * @return A {@link SuccessResponseGet} containing the status of the operation and a list of comments
	 *         on the user's posts.
	 */
	
	
	@GetMapping("users/{userID}/posts/comments")
	public SuccessResponseGet getAllCmtsonPost(@PathVariable int userID)
	{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> lobj = new ArrayList<>();
		lobj.addAll(us.getAllCmtsPst(userID));
		srg.setStatus("Success");
		srg.setData(lobj);
		return srg;
	   
	}
	
	/*
	@GetMapping("users/{userId}/friends")
	public SuccessResponseGet getFrndsByUser(@PathVariable int userID)
	{
		SuccessResponseGet srg = new SuccessResponseGet();
		Set<Object> sobj = new HashSet<>();
		sobj.addAll(us.getAllFrndsUsr(userID));
		srg.setStatus("Success");
		srg.setData(sobj);
		return srg;
		
	}*/
	
	/**
	 * Retrieves all pending friend requests for a specific user.
	 * 
	 * This method takes a user ID as a path variable and returns a list of pending friend requests for that user.
	 * The response is wrapped in a {@link SuccessResponseGet} object containing the list of pending friend requests.
	 * 
	 * @param userID the ID of the user whose pending friend requests are to be retrieved
	 * @return a {@link SuccessResponseGet} containing the status of the operation and the list of pending friend requests
	 */
	@GetMapping("users/{userID}/friend-requests/pending")
	public SuccessResponseGet getPendingReq(@PathVariable int userID)
	{
		SuccessResponseGet srg = new SuccessResponseGet(); 
		List<Object> lobj = new ArrayList<>();
		lobj.addAll(us.getPendingFrndReq(userID));
		srg.setStatus("Success");
		srg.setData(lobj);
		return srg;
		
		
	}
	
}
