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
import com.sprint.app.model.Messages;
import com.sprint.app.services.FriendService;

@Controller
public class FriendWebController {
	
	@Autowired
	private FriendService friendService;
	
	//get msgs between 2 friends
	@GetMapping("/friends/{friendshipID}/messages")
	public String getFrndsMsgs(@PathVariable int friendshipID, Model m)
	{
		List<Messages> messages = friendService.getAllMsgBtwFrnds(friendshipID);
		m.addAttribute("messages", messages);
		return "displayMessage";
	}
	
	//post a message to a friend
	@GetMapping("/friends/message/send")
	public String sendMsgToFriend(Model m)
	{
		MessageDTO messageDto = new MessageDTO();
		m.addAttribute("messageDto", messageDto);
		return "sendMsgFriend";
	}
	
	@PostMapping("/friends/message/sent")
	public String sentMsgToFriend(@RequestParam int friendshipID, @ModelAttribute MessageDTO messageDto)
	{
		friendService.sendMsg(friendshipID, messageDto);
		return "redirect:/friends/message/send";
	}

}
