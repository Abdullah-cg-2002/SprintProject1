package com.sprint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.services.UserService;

@Controller
public class UserWebController {
	
	@Autowired
	private UserService userService;
	
	//send msg to frnd
	@GetMapping("/user/send/message/friend")
	public String getDetailsForSendMsg(Model m)
	{
		MessageDTO messageDto = new MessageDTO();
		m.addAttribute("messageDto", messageDto);
		return "formSendMessage";
	}
	
	@PostMapping("/user/send/message/post")
	public String sendMsgToOtherUser(@RequestParam int userID, @RequestParam int frdID, @ModelAttribute MessageDTO messageDto)
	{
		userService.sendMsgFrnd(userID, frdID, messageDto);
		return "redirect:/user/send/message/friend";
	}
	
	//send friend request
	@GetMapping("/user/send/friend-req")
	public String sendFrndReq(Model m)
	{
		return "sendFrndReq";
	}
	
	@PostMapping("/user/sent/friend-req")
	public String frndReqSent(@RequestParam int userID, @RequestParam int frdID)
	{
		userService.sendFrdReq(userID, frdID);
		return "redirect:/user/send/friend-req";
	}
	
	//get msgs between users
	@GetMapping("/users/{userID}/messages/{otherID}")
	public String msgsBtwnUsers(@PathVariable int userID, @PathVariable int otherID, Model m)
	{
		List<Messages> messages = userService.msgBtwUsers(userID, otherID);
		m.addAttribute("messages", messages);
		return "msgBtwnUsers";
	}
	
	//get all likes for a user
	@GetMapping("/users/{userID}/posts/likes")
	public String getAllLikesUserGot(@PathVariable int userID, Model m)
	{
		List<Likes> likes = userService.getAllLikesPst(userID);
		m.addAttribute("likes", likes);
		return "getLikesUser";
	}
	
	//get all likes user likes
	@GetMapping("/users/{userID}/likes")
	public String getAllLikesByUser(@PathVariable int userID, Model m)
	{
		List<Likes> likes = userService.getAllLikesUsr(userID);
		m.addAttribute("likes", likes);
		return "getLikesUser";
	}
	
	

}
