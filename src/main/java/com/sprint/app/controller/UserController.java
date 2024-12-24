package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.sprint.app.dto.MessageDTO;

@RestController
@RequestMapping("/api/")
public class UserController {
	
	@Autowired
	private UserService us;
	
	@PostMapping("users/{userID}/messages/send/{frdID}")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse sendMsgFrnd(@PathVariable int userID,@PathVariable int frdID, @RequestBody MessageDTO msgdto)
	{
		SuccessResponse sr = new SuccessResponse();
		us.sendMsgFrnd(userID, frdID, msgdto);
		sr.setStatus("success");
		sr.setMessage("message sent to friend successfully");
		return sr;
	}
	
	@PostMapping("users/{userID}/friend-request/send/{frdID}")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse sendFrdReq(@PathVariable int userID,@PathVariable int frdID)
	{
		us.sendFrdReq(userID, frdID);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage("friend request sent successfully");
		return sr;
	}
	
	@GetMapping("users/{userID}/messages/{otherID}")
	public SuccessResponseGet getMsgBetUsers(@PathVariable int userID, @PathVariable int otherID)
	{
		
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> list = new ArrayList<>();
		list.addAll(us.msgBtwUsers(userID, otherID));
		srg.setStatus("success");
		srg.setData(list);
		return srg;
	}
	
	@GetMapping("users/{userID}/posts/likes")
	public SuccessResponseGet getAllLikes(@PathVariable int userID)
	{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> list = new ArrayList<>();
		list.addAll(us.getAllLikesPst(userID));
		srg.setStatus("success");
		srg.setData(list);
		return srg;
	}
	
	@GetMapping("users/{userID}/likes")
	public SuccessResponseGet getAllLikesByUser(@PathVariable int userID)
	{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> list = new ArrayList<>();
		list.addAll(us.getAllLikesUsr(userID));
		srg.setStatus("success");
		srg.setData(list);
		return srg;
	}

}
