package com.sprint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;
import com.sprint.app.services.MessageService;

@RestController
@RequestMapping("/api/")
public class MessageController {
	
	@Autowired
	private MessageService ms;
	
	@GetMapping("messages")
	public List<Messages> getAllMsg()
	{
		return ms.getAllMsgs();
	}
	
	@GetMapping("messages/{messageID}")
	public Messages getSpecificMsg(@PathVariable int messageID)
	{
		return ms.getSpecificMsg(messageID);
	}
	
	@PostMapping("messages")
	@ResponseStatus(HttpStatus.CREATED)
	public void createMsg(@RequestBody MessageDTO msgdto)
	{
		Messages msg = new Messages();
		msg.setMessage_text(msgdto.getMessage_text());
		msg.setReceiver(msgdto.getReceiver());
		msg.setSender(msgdto.getSender());
		ms.createMsg(msg);
	}
	
	@PutMapping("messages/{messageID}")
	public void updateMsg(@PathVariable int messageID, @RequestBody Messages msg)
	{
		ms.updateMsg(messageID, msg);
	}
	
	@DeleteMapping("messages/{messageID}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMsg(@PathVariable int messageID)
	{
		ms.deleteMsg(messageID);
	}

}
