package com.sprint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.services.MessageService;

@Controller
public class MessageWebController {
	
	@Autowired
	private MessageService ms;
	
	@GetMapping("/message/send")
	public String formMsg(Model m)
	{
		Users sender = new Users();
		Users receiver = new Users();
		MessageDTO mdto = new MessageDTO();
		mdto.setReceiver(sender);
		mdto.setSender(receiver);
		m.addAttribute("message", mdto);
		return "formMessage";
	}
	
	@PostMapping("/message/sent")
	public String sentMsg(@ModelAttribute MessageDTO mdto)
	{
		ms.createMsg(mdto);
		return "redirect:/message/send";
	}
	
	@GetMapping("/messages")
	public String getAllMsgs(Model m)
	{
		List<Messages> messages = List.copyOf(ms.getAllMsgs());
		m.addAttribute("messages", messages);
		return "displayMessage";
	}

}
