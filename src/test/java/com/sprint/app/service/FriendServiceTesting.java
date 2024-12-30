package com.sprint.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Status;
import com.sprint.app.model.Users;
import com.sprint.app.repo.FriendsRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.FriendServiceImpl;
import com.sprint.app.services.MessageService;

@ExtendWith(MockitoExtension.class)
public class FriendServiceTesting {
	
	@Mock
	private FriendsRepo friendRepo;
	
	@Mock
	private UserRepo userRepo;
	
	@Mock
	private MessageService messageService;
	
	@InjectMocks
	private FriendServiceImpl friendServiceImpl;
	
	private Users user;
	private Users friend;
	private MessageDTO messageDto;
	private Friends frds;
	private Messages message;
	
	@BeforeEach
	void setup()
	{
		user = new Users();
		friend = new Users();
		
		user.setUserID(1);
		user.setUsername("sujit");
		user.setEmail("sujit@cg.com");
		user.setPassword("sujit@123");
		user.setProfile_picture("sujitpicture");
		
		friend.setUserID(2);
		friend.setUsername("nishaanth");
		friend.setEmail("nishuu@cg.com");
		friend.setPassword("nishuu@123");
		friend.setProfile_picture("nishaanthpicture");
		
		messageDto = new MessageDTO();
		messageDto.setMessage_text("Hi Nishaanth");
		
		message = new Messages();
		message.setMessage_text("Hi Nishaanth");
		message.setReceiver(friend);
		message.setSender(user);
		
		frds = new Friends();
		frds.setStatus(Status.ACCEPTED);
		frds.setUser1(user);
		frds.setUser2(friend);
	}
	
	@Test
	void testSendMsgToFrnd()
	{
		
		
		when(friendRepo.findById(10)).thenReturn(Optional.of(frds));
		
		String userResult = friendServiceImpl.sendMsg(10, messageDto);
		assertEquals("Message Sent Successfully!", userResult);
		verify(messageService).createMsg(messageDto);
	}
	
	@Test
	void testSendMsgToFrnd_Exception()
	{
		when(friendRepo.findById(10)).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class, 
														()->{
															friendServiceImpl.sendMsg(10, messageDto);
														});
		assertEquals("FriendShip doesn't Exists", exception.getMessage());
	}
	
	@Test
	void testGetAllMsgsBtwnFrnds()
	{
		List<Messages> msgList = List.of(message);
		when(friendRepo.findById(10)).thenReturn(Optional.of(frds));
		when(messageService.getMsgSpecificUser(1)).thenReturn(msgList);
		
		List<Messages> result = friendServiceImpl.getAllMsgBtwFrnds(10);
		assertTrue(result.contains(message));
	}
	
	@Test
	void testGetAllMsgsBtwnFrnds_Exception()
	{
		when(friendRepo.findById(10)).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class, 
														()->{
															friendServiceImpl.getAllMsgBtwFrnds(10);
														});
		assertEquals("FriendShip doesn't exists", exception.getMessage());
	}

}
