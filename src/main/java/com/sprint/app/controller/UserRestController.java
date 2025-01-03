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

import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

import jakarta.validation.Valid;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;

@RestController
@RequestMapping("/api/")
public class UserRestController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(MessageRestController.class);
	
	@Autowired
	private UserService us;
	
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

}
