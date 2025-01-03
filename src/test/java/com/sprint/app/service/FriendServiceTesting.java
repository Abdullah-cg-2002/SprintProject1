package com.sprint.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.exception.FriendException;
import com.sprint.app.serviceimpl.FriendServiceImpl;

import jakarta.transaction.Transactional;


@SpringBootTest
public class FriendServiceTesting {
	
	@Autowired
	private FriendServiceImpl friendServiceImpl;
	
	@Test
	@Transactional
	void testSendMsgToFrnd()
	{
		MessageDTO messageDto = new MessageDTO();
		messageDto.setMessage_text("Hello new Message");
		assertEquals("Message Sent Successfully!", friendServiceImpl.sendMsg(100002, messageDto));
	}
	
	@Test
	void testSendMsgToFrnd_Exception()
	{
		
		RuntimeException exception = assertThrows(FriendException.class, 
														()->{
															friendServiceImpl.sendMsg(1, null);
														});
		assertEquals("FriendShip doesn't Exists", exception.getMessage());
	}
	
	@Test
	@Transactional
	void testGetAllMsgsBtwnFrnds()
	{
		assertFalse(friendServiceImpl.getAllMsgBtwFrnds(100003).isEmpty());
	}
	
	@Test
	void testGetAllMsgsBtwnFrnds_Exception()
	{
		
		RuntimeException exception = assertThrows(FriendException.class, 
														()->{
															friendServiceImpl.getAllMsgBtwFrnds(10);
														});
		assertEquals("FriendShip doesn't exists", exception.getMessage());
	}
	
	@Test
	@Transactional
	void testGetAllFrds()
	{
		assertNotNull(friendServiceImpl.getAllFrnds(1));
	}
	
	@Test
	void testGetAllFrds_Exception()
	{
		RuntimeException exception = assertThrows(FriendException.class, 
				()->{
					friendServiceImpl.getAllFrnds(0);
				});
		assertEquals("UserId not found", exception.getMessage());
	}

}
