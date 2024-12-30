package com.sprint.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.repo.MessageRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.MessageServiceImpl;
import com.sprint.app.services.NotificationService;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTesting {
	
	@Mock
	private MessageRepo messageRepo;
	
	@Mock
	private UserRepo userRepo;
	
	@Mock
	private NotificationService notifService;
	
	
	@InjectMocks
	private MessageServiceImpl messageServiceImpl;


	private MessageDTO messageDto;
	private Messages existingMessage;
	private Messages anotherMessage;
	private Users sender;
	private Users receiver;
	private List<Messages> messages = new ArrayList<>();
	private RuntimeException exception;
	
	@BeforeEach
	void setup()
	{
		sender = new Users();
		receiver = new Users();
		
		//sender details
		sender.setUsername("sujit");
		sender.setEmail("sujit@cg.com");
		sender.setPassword("sujit@123");
		sender.setProfile_picture("sujitpicture");
		
		//receiver details
		receiver.setUsername("nishaanth");
		receiver.setEmail("nishuu@cg.com");
		receiver.setPassword("nishuu@123");
		receiver.setProfile_picture("nishaanthpicture");
		
		messageDto = new MessageDTO();
		messageDto.setMessage_text("Hi Nishaanth");
		messageDto.setReceiver(receiver);
		messageDto.setSender(sender);
		
		existingMessage = new Messages();
		existingMessage.setMessage_text("Message 1");
		existingMessage.setReceiver(receiver);
		existingMessage.setSender(sender);
		
		anotherMessage = new Messages();
		anotherMessage.setMessage_text("Message 2");
		anotherMessage.setReceiver(sender);
		anotherMessage.setSender(receiver);
		
		messages.add(anotherMessage);
		messages.add(existingMessage);
	}
	
	@Test
	void testGetAllMsgs()
	{
		when(messageRepo.findAll()).thenReturn(messages);
		List<Messages> resultList = messageServiceImpl.getAllMsgs();
		assertNotNull(resultList);
	}
	
	@Test
	void testGetSpecificMsg()
	{
		when(messageRepo.findById(1)).thenReturn(Optional.of(anotherMessage));
		
		Messages message = messageServiceImpl.getSpecificMsg(1);
		assertNotNull(message);
		assertEquals(anotherMessage.getMessage_text(), message.getMessage_text());
		assertEquals(anotherMessage.getSender(), message.getSender());
		assertEquals(anotherMessage.getReceiver(), message.getReceiver());
	}
	
	@Test
	void testGetSpecificMsg_Exception()
	{
		when(messageRepo.findById(1)).thenReturn(Optional.empty());
		
		exception = assertThrows(RuntimeException.class, ()->{
			messageServiceImpl.getSpecificMsg(1);
		});
		
		assertEquals("messageid doesn't Exists", exception.getMessage());
	}
	
	@Test
	void testGetMsgSpecificUser()
	{
		Users user = mock(Users.class);
		
		when(userRepo.findById(1)).thenReturn(Optional.of(user));
		when(user.getSentmsg()).thenReturn(List.of(anotherMessage));
		when(user.getReceivedmsg()).thenReturn(List.of(existingMessage));
		
		List<Messages> resultList = messageServiceImpl.getMsgSpecificUser(1);
		assertNotNull(resultList);
		assertTrue(resultList.contains(anotherMessage));
		assertTrue(resultList.contains(existingMessage));
	}
	
	@Test
	void testGetMsgSpecificUser_Exception()
	{
		when(userRepo.findById(1)).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            messageServiceImpl.getMsgSpecificUser(1);
        });
		
		assertEquals("UsersId doesn't exists", exception.getMessage());
	}
	
	@Test
	void testCreateMsg()
	{
		when(userRepo.findById(sender.getUserID())).thenReturn(Optional.of(sender));
        when(userRepo.findById(receiver.getUserID())).thenReturn(Optional.of(receiver));
		
        String result = messageServiceImpl.createMsg(messageDto);
        assertEquals("success", result);
		verify(messageRepo).save(any(Messages.class));
	}
	
	@Test
	//@Disabled
	void testCreateMsg_Exception()
	{
		when(userRepo.findById(sender.getUserID())).thenReturn(Optional.empty());
        when(userRepo.findById(receiver.getUserID())).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class,()->{ messageServiceImpl.createMsg(messageDto);});
		assertEquals("sender or reciever doesn't exists", exception.getMessage());
		
		
	}
	
	@Test
	void testUpdateMsg()
	{
		Messages updatedMessage = new Messages();
		updatedMessage.setMessage_text("updated message");
		
		when(messageRepo.findById(1)).thenReturn(Optional.of(existingMessage));
		
		String result = messageServiceImpl.updateMsg(1, updatedMessage);
		assertEquals("success", result);
		verify(messageRepo).save(any(Messages.class));
		assertEquals("updated message", existingMessage.getMessage_text());
	}
	
	@Test
	void testDeleteMsg()
	{
		when(messageRepo.findById(1)).thenReturn(Optional.of(existingMessage));
		String result = messageServiceImpl.deleteMsg(1);
		assertEquals("success", result);
		verify(messageRepo).deleteById(1);
	}
	
	@Test
	void testDeleteMsg_Exception()
	{
		when(messageRepo.findById(1)).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            messageServiceImpl.deleteMsg(1);
        });
		
		assertEquals("messageid doesn't Exists", exception.getMessage());
	}
	

}
