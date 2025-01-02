package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class MessageRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageRestController.class);
	
	@Autowired
	private MessageService ms;
	
	/**
	 * 
	 * @return this will return List of All messages
	 */
	@GetMapping("messages")
	public SuccessResponseGet getAllMsg()
	{
		List<Messages> messages = ms.getAllMsgs();
		
		if(!messages.isEmpty())
		{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> obj = new ArrayList<>();
		srg.setStatus("success");
		logger.info("fetching all messages");
		obj.addAll(messages);
		logger.info("retrived {} messages", obj.size());
		srg.setData(obj);
		return srg;
		}
		else {
			throw new RuntimeException("No Messages in database");
		}
	}
	
	/**
	 * 
	 * @param messageID
	 * @return successresponse with the specific message in data
	 */
	@GetMapping("messages/{messageID}")
	public SuccessResponseGet getSpecificMsg(@PathVariable int messageID)
	{
		Messages message =  ms.getSpecificMsg(messageID);
		
		if(message!=null)
		{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> obj = new ArrayList<>();
		srg.setStatus("success");
		logger.info("fetching specific message using messageid");
		obj.add(message);
		srg.setData(obj);
		logger.info("retrived {} message",obj.size());
		return srg;
		}
		
		else
		{
			throw new RuntimeException("No such message Exists");
		}
	}
	
	/**
	 * 
	 * @param msgdto
	 * @return
	 */
	@PostMapping("messages")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse createMsg(@Valid @RequestBody MessageDTO msgdto)
	{
		ms.createMsg(msgdto);
		SuccessResponse sr = new SuccessResponse();
		logger.info("creating message");
		sr.setStatus("success");
		sr.setMessage("message sent successfully");
		logger.info("message sent successfully");
		return sr;
	}
	
	/**
	 * 
	 * @param messageID
	 * @param msg
	 * @return
	 */
	@PutMapping("messages/{messageID}")
	public SuccessResponse updateMsg(@PathVariable int messageID,@Valid @RequestBody Messages msg)
	{
		ms.updateMsg(messageID, msg);
		SuccessResponse sr = new SuccessResponse();
		logger.info("updating message text");
		sr.setStatus("success");
		sr.setMessage("message updated successfully");
		logger.info("updated successfully");
		return sr;
	}
	
	/**
	 * 
	 * @param messageID
	 * @return
	 */
	@DeleteMapping("messages/{messageID}")
	public SuccessResponse deleteMsg(@PathVariable int messageID)
	{
		ms.deleteMsg(messageID);
		SuccessResponse sr = new SuccessResponse();
		logger.info("trying to delete the message using messageid");
		sr.setStatus("success");
		sr.setMessage("message deleted successfully");
		logger.info("message deleted successfully");
		return sr;
	}

}
