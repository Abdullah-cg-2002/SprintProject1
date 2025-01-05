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
import com.sprint.app.model.Users;
import com.sprint.app.services.MessageService;

@Controller
public class MessageWebController {
	
	@Autowired
	private MessageService ms;
	
	//create message
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
	
	//get all message
	@GetMapping("/messages")
	public String getAllMsgs(Model m)
	{
		List<Messages> messages = List.copyOf(ms.getAllMsgs());
		m.addAttribute("messages", messages);
		return "displayMessage";
	}
	
	//get specific message
	@GetMapping("/messages/{messageID}")
	public String getSpecificMsg(@PathVariable int messageID, Model  m)
	{
		Messages messages = ms.getSpecificMsg(messageID);
		m.addAttribute("messages", messages);
		return "displayMessage";
	}
	
	//update method
	@GetMapping("/messages/update")
	public String updateSpecificMsg(Model m)
	{
		Messages message = new Messages();
		m.addAttribute("message", message);
		return "updateMessage";
	}
	
	@PostMapping("/message/updated")
	public String updatedMsg(@RequestParam int messageID, @ModelAttribute Messages message, Model m)
	{
		ms.updateMsg(messageID, message);
		return "redirect:/messages";
	}
	
	//delete message
	@GetMapping("/message/delete")
	public String deleteMsg(Model m)
	{
		return "deleteMessage";
	}
	
	@PostMapping("/message/deleted")
	public String deletedMsg(@RequestParam int messageID)
	{
		ms.deleteMsg(messageID);
		return "redirect:/messages";
	}

}
