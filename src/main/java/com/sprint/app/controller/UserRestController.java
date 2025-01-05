package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

import jakarta.validation.Valid;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;

@RestController
@RequestMapping("/api/")
public class UserRestController {
	
	@Autowired
	private UserService us;

	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	/**
     * Retrieves all users.
     * 
     * @return SuccessResponseGet containing the status and list of users.
     */
    @GetMapping("users/all")
    public SuccessResponseGet getAllUsers()
    {
        logger.info("Retrieving all users");
        SuccessResponseGet srg = new SuccessResponseGet();
        List<Object> lis = new ArrayList<>();
        lis.addAll(us.getAllUsers());
        srg.setStatus("success");
        srg.setData(lis);
        logger.info("Retrieved all users");
        return srg;
    }

    /**
     * Retrieves a specific user based on user ID.
     * 
     * @param userID The ID of the user to retrieve.
     * @return SuccessResponseGet containing the status and user details.
     */
    @GetMapping("users/{userID}")
    public SuccessResponseGet getSpecificUser(@PathVariable int userID)
    {
        logger.info("Retrieving a specific user");
        SuccessResponseGet srg = new SuccessResponseGet();
        List<Object> lis = new ArrayList<>();
        lis.add(us.getSpecificUser(userID));
        srg.setStatus("success");
        srg.setData(lis);
        logger.info("Retrieved the specific user");
        return srg;
    }

    /**
     * Searches for users by their username.
     * 
     * @param username The username to search for.
     * @return SuccessResponseGet containing the status and matching users.
     */
    @GetMapping("users/search/{username}")
    public SuccessResponseGet searchForUserByName(@PathVariable String username)
    {
        logger.info("Searching for user by username");
        SuccessResponseGet srg = new SuccessResponseGet();
        List<Object> lis = new ArrayList<>();
        lis.addAll(us.searchForUserByName(username));
        srg.setStatus("success");
        srg.setData(lis);
        logger.info("Users retrieved");
        return srg;
    }

    /**
     * Adds a new user.
     * 
     * @param user The user object containing user details.
     * @return SuccessResponse with status and success message.
     */
    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse addUser(@RequestBody Users user)
    {
    	logger.info("Adding new user");
    	us.addUser(user);
    	SuccessResponse sr = new SuccessResponse();
    	sr.setStatus("Success");
    	sr.setMessage("User added Successfully");
    	logger.info("New User added Successfully");
    	return sr;
    }

    /**
     * Deletes a user based on user ID.
     * 
     * @param userID The ID of the user to be deleted.
     * @return SuccessResponse with status and success message.
     */
    @DeleteMapping("users/delete/{userID}")
    public SuccessResponse deleteUser(@PathVariable int userID)
    {
        logger.info("Deleting user");
        us.deleteUser(userID);
        SuccessResponse sr= new SuccessResponse();
        sr.setStatus("success");
        
        sr.setMessage("User removed successfully");
        logger.info("User removed successfully");
        return sr;
    }

    /**
     * Fetches all groups associated with a specific user.
     * 
     * @param userID The ID of the user whose groups are to be retrieved.
     * @return SuccessResponseGet containing the status and list of groups.
     */
    @GetMapping("users/{userID}/groups")
    public SuccessResponseGet getAllGroupsofUser(@PathVariable int userID)
    {
        logger.info("Fetching groups for user");
        SuccessResponseGet srg = new SuccessResponseGet();
        List<Object> lis = new ArrayList<>();
        lis.addAll(us.getAllGroupsofUser(userID));
        srg.setStatus("success");
        srg.setData(lis);
        logger.info("Fetched groups successfully for user");
        return srg;
    }
    
    /**
	 * 
	 * @param userID
	 * @param frdID
	 * @param msgdto
	 * @return successresponse with message sent to friend successfully
	 */
	@PostMapping("users/{userID}/messages/send/{frdID}")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse sendMsgFrnd(@PathVariable int userID,@PathVariable int frdID,@Valid @RequestBody MessageDTO msgdto)
	{
		SuccessResponse sr = new SuccessResponse();
		logger.info("sending message to the friend");
		us.sendMsgFrnd(userID, frdID, msgdto);
		logger.info("message sent successfully");
		sr.setStatus("success");
		sr.setMessage("message sent to friend successfully");
		return sr;
	}
	
	/**
	 * 
	 * @param userID
	 * @param frdID
	 * @return successresponse with friend request sent successfully
	 */
	@PostMapping("users/{userID}/friend-request/send/{frdID}")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse sendFrdReq(@PathVariable int userID,@PathVariable int frdID)
	{
		logger.info("sending friend request to the friend");
		us.sendFrdReq(userID, frdID);
		logger.info("friend request sent successfully");
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage("friend request sent successfully");
		return sr;
	}
	
	/**
	 * @param userID
	 * @param otherID
	 * @return successresponse with data list
	 */
	@GetMapping("users/{userID}/messages/{otherID}")
	public SuccessResponseGet getMsgBetUsers(@PathVariable int userID, @PathVariable int otherID)
	{
		List<Messages> messages = us.msgBtwUsers(userID, otherID);
		
		if(!messages.isEmpty())
		{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> list = new ArrayList<>();
		logger.info("fetching all the message between users");
		list.addAll(messages);
		srg.setStatus("success");
		srg.setData(list);
		logger.info("retrived {} messages", list.size());
		return srg;
		}
		else
		{
			throw new RuntimeException("No messages exists between these 2 users");
		}
	}
	
	/**
	 * 
	 * @param userID
	 * @return successresponse which has all the likes the specific user got
	 */
	@GetMapping("users/{userID}/posts/likes")
	public SuccessResponseGet getAllLikes(@PathVariable int userID)
	{
		List<Likes> likes = us.getAllLikesPst(userID);
		
		if(!likes.isEmpty())
		{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> list = new ArrayList<>();
		logger.info("retriving likes");
		list.addAll(likes);
		srg.setStatus("success");
		srg.setData(list);
		logger.info("retrived {} likes", list.size());
		return srg;
		}
		else
		{
			throw new RuntimeException("This user got no likes");
		}
	}
	
	/**
	 * 
	 * @param userID
	 * @return successresponse which has all the likes done by specific user
	 */
	@GetMapping("users/{userID}/likes")
	public SuccessResponseGet getAllLikesByUser(@PathVariable int userID)
	{
		List<Likes> likes = us.getAllLikesUsr(userID);
		
		if(!likes.isEmpty())
		{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> list = new ArrayList<>();
		logger.info("fetching likes by the user");
		list.addAll(likes);
		srg.setStatus("success");
		srg.setData(list);
		logger.info("user likes {} posts", list.size());
		return srg;
		}
		else
		{
			throw new RuntimeException("No posts were liked by this user");
		}
	}


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
	
	/**
     * Updates the information of a user.
     *
     * @param userID the unique identifier of the user to be updated. Must be a positive integer.
     * @param user   the user object containing updated information. Must be valid according to the defined constraints.
     * @return a SuccessResponse object containing the status and message of the update operation.
     */
	@PutMapping("users/update/{userID}")
	public SuccessResponse updateUser(@PathVariable @Min(1) int userID, @RequestBody @Valid Users user) {
		logger.info("Received update request for user with ID: {}", userID);
		String message = us.updateUser(userID, user);
		
		logger.info("User with ID {} updated successfully. Message: {}", userID, message);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage(message);
		
		return sr;
		
	}
	
	 /**
     * Retrieves notifications for a specific user.
     *
     * @param userID the unique identifier of the user whose notifications are to be retrieved. Must be a positive integer.
     * @return a SuccessResponseGet object containing the notifications for the specified user.
     */
	@GetMapping("users/{userID}/notification")
	public SuccessResponseGet getNotificationByUserID(@PathVariable @Min(1) int userID){
		logger.info("Retrieving notifications for user with ID: {}", userID);
		SuccessResponseGet response= us.getNotificationByUserID(userID);
		logger.info("Successfully retrieved notifications for user with ID: {}", userID);
		return response;
	}
	
	
	
}


