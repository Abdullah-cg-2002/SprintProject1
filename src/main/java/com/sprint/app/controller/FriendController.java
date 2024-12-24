package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.sprint.app.services.FriendService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;
import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;

@RestController
@RequestMapping("/api/")
public class FriendController {
	
	@Autowired
	private FriendService fs;
	
	@GetMapping("users/{userID}/friends")
	public SuccessResponseGet getFrdsUsr(@PathVariable int userID)
	{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> obj = new ArrayList<>();
		obj.addAll(fs.getAllFrnds(userID));
		srg.setStatus("success");
		srg.setData(obj);
		return srg;
	}
	
	@GetMapping("friends/{friendshipID}/messages")
	public List<Messages> getMsgFrds(@PathVariable int friendshipID)
	{
		return fs.getAllMsgBtwFrnds(friendshipID);
	}
	
	@PostMapping("users/{userID}/friends/{friendID}")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse addFrnd(@PathVariable int userID, @PathVariable int friendID)
	{
		String message = fs.addFrnd(userID, friendID);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("sucess");
		sr.setMessage(message);
		
		return sr;
		
	}
	
	@PostMapping("friends/{friendshipID}/message/send")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse sendMsg(@PathVariable int friendshipID, @RequestBody MessageDTO msgdto)
	{
		String message = fs.sendMsg(friendshipID, msgdto);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("sucess");
		sr.setMessage(message);
		
		return sr;
	}
	
	@DeleteMapping("users/{userID}/friends/{friendID}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SuccessResponse deleteFrd(@PathVariable int userID, @PathVariable int friendID)
	{
		String message = fs.deleteFrnd(userID, friendID);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("sucess");
		sr.setMessage(message);
		
		return sr;
	}

}
