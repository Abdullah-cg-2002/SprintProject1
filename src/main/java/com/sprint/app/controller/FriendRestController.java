package com.sprint.app.controller;


import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.sprint.app.services.FriendService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

/**
 * Controller for managing friendships between users.
 * Provides endpoints to retrieve, add, and delete friends for a specific user.
 */
@RestController
@RequestMapping("/api/")
public class FriendRestController {

	private static final Logger logger = LoggerFactory.getLogger(FriendRestController.class);

	@Autowired
	private FriendService fs;

	/**
	 * Retrieves all friends of a specific user.
	 *
	 * @param userID ID of the user whose friends are to be retrieved.
	 * @return SuccessResponseGet containing the list of friends.
	 */
	@GetMapping("users/{userID}/friends")
	public SuccessResponseGet getFrdsUsr(@PathVariable int userID)
	{
		logger.info("Retrieving all the friends of a user");
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> obj = new ArrayList<>();
		obj.addAll(fs.getAllFrnds(userID));
		srg.setStatus("success");
		srg.setData(obj);
		logger.info("Retrieved Successfully");
		return srg;
	}

	/**
	 * Adds a friend for a specific user.
	 *
	 * @param userID ID of the user who is adding a friend.
	 * @param friendID ID of the user to be added as a friend.
	 * @return SuccessResponse containing the status and message of the operation.
	 */
	@PostMapping("users/{userID}/friends/{friendID}")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse addFrnd(@PathVariable int userID, @PathVariable int friendID)
	{
		logger.info("Adding a Friend to a user");
		String message = fs.addFrnd(userID, friendID);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage(message);
		logger.info("Added Successfully");
		return sr;
	}

	/**
	 * Deletes a friend for a specific user.
	 *
	 * @param userID ID of the user who wants to delete a friend.
	 * @param friendID ID of the user to be removed as a friend.
	 * @return SuccessResponse containing the status and message of the operation.
	 */
	@DeleteMapping("users/{userID}/friends/{friendID}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SuccessResponse deleteFrd(@PathVariable int userID, @PathVariable int friendID)
	{
		logger.info("Deleting a Friend to a user");
		String message = fs.deleteFrnd(userID, friendID);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage(message);
		logger.info("Deleted Successfully");
		return sr;
	}
	
	/**
	 * 
	 * @param friendshipID
	 * @return retrive all the message between friends
	 */
	@GetMapping("friends/{friendshipID}/messages")
	public SuccessResponseGet getMsgFrds(@PathVariable int friendshipID)
	{
		List<Messages> messages = fs.getAllMsgBtwFrnds(friendshipID);
		if(messages.isEmpty())
		{
			throw new RuntimeException("No messages shared between friends");
		}
		else
		{
			SuccessResponseGet srg = new SuccessResponseGet();
			List<Object> obj = List.of(messages);
			srg.setStatus("success");
			srg.setData(obj);
			return srg;
		}
	}
	
	/**
	 * 
	 * @param friendshipID
	 * @param msgdto
	 * @return successresponse with status and message whether message sent or not
	 */
	@PostMapping("friends/{friendshipID}/message/send")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse sendMsg(@PathVariable int friendshipID, @Valid @RequestBody MessageDTO msgdto)
	{
		logger.info("sending message to a friend");
		fs.sendMsg(friendshipID, msgdto);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage("message sent");
		logger.info("message sent");
		return sr;
	}

}
