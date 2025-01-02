package com.sprint.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.exception.MessageException;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.serviceimpl.MessageServiceImpl;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MessageServiceTesting {
	
	@Autowired
	private MessageServiceImpl messageServiceImpl;
	
	private MessageDTO messageDto;
	private Users sender;
	private Users receiver;
	
	@Test
	void testGetAllMsgs()
	{
		assertFalse(messageServiceImpl.getAllMsgs().isEmpty());
	}
	
	@Test
	void testGetSpecificMsg()
	{
		assertNotNull(messageServiceImpl.getSpecificMsg(1000005));
	}
	
	@Test
	void testGetSpecificMsg_Exception()
	{

		
		RuntimeException exception = assertThrows(MessageException.class, ()->{
			messageServiceImpl.getSpecificMsg(1);
		});
		
		assertEquals("messageid doesn't Exists", exception.getMessage());
	}
	
	@Test
	@Transactional
	void testGetMsgSpecificUser()
	{
		assertFalse(messageServiceImpl.getMsgSpecificUser(3).isEmpty());
	}
	
	@Test
	void testGetMsgSpecificUser_Exception()
	{	
		RuntimeException exception = assertThrows(MessageException.class, () -> {
            messageServiceImpl.getMsgSpecificUser(200);
        });
		
		assertEquals("UsersId doesn't exists", exception.getMessage());
	}
	
	@Test
	@Transactional
	void testCreateMsg()
	{
		sender = new Users();
		receiver = new Users();
		
		sender.setUserID(6);
		receiver.setUserID(20);
		messageDto = new MessageDTO();
		messageDto.setMessage_text("new message for user 20");
		messageDto.setReceiver(receiver);
		messageDto.setSender(sender);
		assertEquals("success", messageServiceImpl.createMsg(messageDto));
	}
	
	@Test
	void testCreateMsg_Exception()
	{	
		sender = new Users();
		receiver = new Users();
		
		sender.setUserID(0);
		receiver.setUserID(20);
		messageDto = new MessageDTO();
		messageDto.setMessage_text("new message for user 20");
		messageDto.setReceiver(receiver);
		messageDto.setSender(sender);
		RuntimeException exception = assertThrows(MessageException.class,()->{ messageServiceImpl.createMsg(messageDto);});
		assertEquals("sender or reciever doesn't exists", exception.getMessage());	
	}
	
	@Test
	@Transactional
	void testUpdateMsg()
	{
		Messages message = new Messages();
		message.setMessage_text("edited text");
		assertEquals("success", messageServiceImpl.updateMsg(1000010, message));
	}
	
	@Test
	void testUpdateMsg_Exception()
	{
		RuntimeException exception = assertThrows(MessageException.class,()->{ messageServiceImpl.updateMsg(1, null);});
		assertEquals("messageid doesn't Exists", exception.getMessage());	
	}
	
	@Test
	@Transactional
	void testDeleteMsg()
	{
		String result = messageServiceImpl.deleteMsg(1000089);
		assertEquals("success", result);
	}
	
	@Test
	void testDeleteMsg_Exception()
	{
		RuntimeException exception = assertThrows(MessageException.class, () -> {
            messageServiceImpl.deleteMsg(1);
        });
		
		assertEquals("messageid doesn't Exists", exception.getMessage());
	}
	

}
