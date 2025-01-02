package com.sprint.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.sprint.app.services.FriendService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

import jakarta.validation.Valid;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;

@RestController
@RequestMapping("/api/")
public class FriendRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageRestController.class);
	
	@Autowired
	private FriendService fs;
	
	/**
	 * 
	 * @param friendshipID
	 * @return retrive all the message between friends
	 */
	@GetMapping("friends/{friendshipID}/messages")
	public SuccessResponseGet getMsgFrds(@PathVariable int friendshipID)
	{
		List<Messages> messages = fs.getAllMsgBtwFrnds(friendshipID);
		if(messages.isEmpty())
		{
			throw new RuntimeException("No messages shared between friends");
		}
		else
		{
			SuccessResponseGet srg = new SuccessResponseGet();
			List<Object> obj = List.of(messages);
			srg.setStatus("success");
			srg.setData(obj);
			return srg;
		}
	}
	
	/**
	 * 
	 * @param friendshipID
	 * @param msgdto
	 * @return successresponse with status and message whether message sent or not
	 */
	@PostMapping("friends/{friendshipID}/message/send")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse sendMsg(@PathVariable int friendshipID, @Valid @RequestBody MessageDTO msgdto)
	{
		logger.info("sending message to a friend");
		fs.sendMsg(friendshipID, msgdto);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage("message sent");
		logger.info("message sent");
		return sr;
	}
	

}
