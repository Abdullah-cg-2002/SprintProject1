package com.sprint.app.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;


import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;


@RestController
@RequestMapping("/api/")
@Validated
public class UserRestController {
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	private UserService us;
	
	@PostMapping("users/{userID}/messages/send/{frdID}")
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
	}
	
	
	//kiki
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
	
	@GetMapping("users/getAll")
    public List<Users> getAllUsers() {
        return us.getAllUsers();
    }
}
