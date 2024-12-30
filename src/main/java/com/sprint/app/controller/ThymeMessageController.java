package com.sprint.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Users;
import com.sprint.app.services.MessageService;

@Controller
public class ThymeMessageController {
	
	@Autowired
	private MessageService ms;
	
	@GetMapping("/message/send")
	public String formMsg(Model m)
	{
		MessageDTO mdto = new MessageDTO();
		mdto.setReceiver(new Users());
		mdto.setSender(new Users());
		m.addAttribute("message", mdto);
		return "formMessage";
	}
	
	@PostMapping("/message/sent")
	public String sentMsg(@ModelAttribute MessageDTO mdto)
	{
		ms.createMsg(mdto);
		return "redirect:/formMessage";
	}

}
