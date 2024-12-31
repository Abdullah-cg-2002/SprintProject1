package com.sprint.app.controller;

import java.util.ArrayList;
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
import com.sprint.app.dto.MessageDTO;

@RestController
@RequestMapping("/api/")
public class FriendRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageRestController.class);
	
	@Autowired
	private FriendService fs;
	
	/**
	 * 
	 * @param friendshipID
	 * @return
	 */
	@GetMapping("friends/{friendshipID}/messages")
	public SuccessResponseGet getMsgFrds(@PathVariable int friendshipID)
	{
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> obj = new ArrayList<>();
		obj.addAll(fs.getAllMsgBtwFrnds(friendshipID));
		srg.setStatus("success");
		srg.setData(obj);
		
		return srg;
	}
	
	/**
	 * 
	 * @param friendshipID
	 * @param msgdto
	 * @return successresponse with status and message whether message sent or not
	 */
	@PostMapping("friends/{friendshipID}/message/send")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse sendMsg(@PathVariable int friendshipID, @RequestBody MessageDTO msgdto)
	{
		logger.info("sending message to a friend");
		String message = fs.sendMsg(friendshipID, msgdto);
		SuccessResponse sr = new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage(message);
		logger.info(message);
		return sr;
	}
	

}
