package com.sprint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.services.MessageService;

@Controller
public class MessageWebController {
	
	@Autowired
	private MessageService ms;
	
	@GetMapping("/messages")
    public String getAllMessages(Model model) {
        List<Messages> messages = ms.getAllMsgs();
        model.addAttribute("messages", messages);
        return "messages";
    }

    @GetMapping("/messages/{messageID}")
    public String getSpecificMessage(@PathVariable int messageID, Model model) {
        Messages message = ms.getSpecificMsg(messageID);
        model.addAttribute("message", message);
        return "displayMessage";
    }

    @GetMapping("/messages/create")
    public String createMessageForm(Model model) {
    	MessageDTO msgDto = new MessageDTO();
    	Users sender = new Users();
    	Users receiver = new Users();
    	msgDto.setReceiver(receiver);
    	msgDto.setSender(sender);
    	model.addAttribute("messageDto", msgDto);
        return "formMessage";
    }

    @PostMapping("/messages")
    public String createMessage(@ModelAttribute MessageDTO msgdto) {
        ms.createMsg(msgdto);
        return "redirect:/messages";
    }

    @GetMapping("/messages/update/{messageID}")
    public String updateMessageForm(@PathVariable int messageID, Model model) {
        Messages message = ms.getSpecificMsg(messageID);
        model.addAttribute("message", message);
        return "updateMessage";
    }

    @PostMapping("/messages/{messageID}")
    public String updateMessage(@PathVariable int messageID, @ModelAttribute Messages msg) {
        ms.updateMsg(messageID, msg);
        return "redirect:/messages";
    }

    @GetMapping("/messages/delete/{messageID}")
    public String deleteMessageForm(@PathVariable int messageID, Model model) {
        Messages message = ms.getSpecificMsg(messageID);
        model.addAttribute("message", message);
        return "deleteMessage";
    }

    @PostMapping("/messages/delete/{messageID}")
    public String deleteMessage(@PathVariable int messageID) {
        ms.deleteMsg(messageID);
        return "redirect:/messages";
    }

}
