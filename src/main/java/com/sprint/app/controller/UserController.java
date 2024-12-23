package com.sprint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.services.UserService;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;

@RestController
@RequestMapping("/api/")
public class UserController {
	
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

}
