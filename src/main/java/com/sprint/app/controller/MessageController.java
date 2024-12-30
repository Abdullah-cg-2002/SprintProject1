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
	public SuccessResponseGet getSpecificMsg(@PathVariable int messageID)
	{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> obj = new ArrayList<>();
		srg.setStatus("success");
		obj.add(ms.getSpecificMsg(messageID));
		srg.setData(obj);
		
		return srg;
	}
	
	@PostMapping("messages")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse createMsg(@RequestBody MessageDTO msgdto)
	{
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus(ms.createMsg(msgdto));
		sr.setMessage("message sent successfully");
		
		return sr;
	}
	
	@PutMapping("messages/{messageID}")
	public SuccessResponse updateMsg(@PathVariable int messageID, @RequestBody Messages msg)
	{
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus(ms.updateMsg(messageID, msg));
		sr.setMessage("message updated successfully");
		
		return sr;
	}
	
	@DeleteMapping("messages/{messageID}")
	public SuccessResponse deleteMsg(@PathVariable int messageID)
	{
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus(ms.deleteMsg(messageID));
		sr.setMessage("message deleted successfully");
		
		return sr;
	}

}
