package com.sprint.app.controller;

import java.util.ArrayList;
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
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

@RestController
@RequestMapping("/api/")
public class MessageController {
	
	@Autowired
	private MessageService ms;
	
	@GetMapping("messages")
	public SuccessResponseGet getAllMsg()
	{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> obj = new ArrayList<>();
		srg.setStatus("success");
		obj.addAll(ms.getAllMsgs());
		srg.setData(obj);
		
		return srg;
	}
	
	@GetMapping("messages/{messageID}")
	public Messages getSpecificMsg(@PathVariable int messageID)
	{
		return ms.getSpecificMsg(messageID);
	}
	
	@PostMapping("messages")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse createMsg(@RequestBody MessageDTO msgdto)
	{
		Messages msg = new Messages();
		msg.setMessage_text(msgdto.getMessage_text());
		msg.setReceiver(msgdto.getReceiver());
		msg.setSender(msgdto.getSender());
		ms.createMsg(msg);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage("message sent successfully");
		
		return sr;
	}
	
	@PutMapping("messages/{messageID}")
	public SuccessResponse updateMsg(@PathVariable int messageID, @RequestBody Messages msg)
	{
		ms.updateMsg(messageID, msg);
		SuccessResponse sr = new SuccessResponse();
		sr.setMessage("message updated successfully");
		sr.setStatus("success");
		
		return sr;
	}
	
	@DeleteMapping("messages/{messageID}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SuccessResponse deleteMsg(@PathVariable int messageID)
	{
		ms.deleteMsg(messageID);
		SuccessResponse sr = new SuccessResponse();
		sr.setMessage("message deleted successfully");
		sr.setStatus("success");
		
		return sr;
	}

}
