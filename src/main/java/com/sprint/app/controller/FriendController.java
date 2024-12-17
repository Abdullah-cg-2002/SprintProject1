package com.sprint.app.controller;

import java.util.List;
import java.util.Set;

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
import com.sprint.app.model.Friends;
import com.sprint.app.services.FriendService;
import com.sprint.app.model.Messages;

@RestController
@RequestMapping("/api/")
public class FriendController {
	
	@Autowired
	private FriendService fs;
	
	@GetMapping("users/{userID}/friends")
	public Set<Friends> getFrdsUsr(@PathVariable int userID)
	{
		return fs.getAllFrnds(userID);
	}
	
	@GetMapping("friends/{friendshipID}/messages")
	public List<Messages> getMsgFrds(@PathVariable int friendshipID)
	{
		return fs.getAllMsgBtwFrnds(friendshipID);
	}
	
	@PostMapping("users/{userID}/friends/{friendID}")
	@ResponseStatus(HttpStatus.CREATED)
	public void addFrnd(@PathVariable int userID, @PathVariable int friendID)
	{
		fs.addFrnd(userID, friendID);
	}
	
	@PostMapping("friends/{friendshipID}/message/send")
	@ResponseStatus(HttpStatus.CREATED)
	public void sendMsg(@PathVariable int friendshipID, @RequestBody Messages msg)
	{
		fs.sendMsg(friendshipID, msg);
	}
	
	@DeleteMapping("users/{userID}/friends/{friendID}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFrd(@PathVariable int userID, @PathVariable int friendID)
	{
		fs.deleteFrnd(userID, friendID);
	}

}
