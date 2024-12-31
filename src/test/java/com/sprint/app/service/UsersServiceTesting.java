package com.sprint.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.serviceimpl.UserServiceImpl;

import jakarta.transaction.Transactional;

@SpringBootTest
public class UsersServiceTesting {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	private MessageDTO messageDto;
	
	@BeforeEach
	void setup()
	{	
		messageDto = new MessageDTO();
		messageDto.setMessage_text("Hi Nishaanth");	
	}
	
	@Test
	@Transactional
	void testSendMsgtoFrnd()
	{
		assertEquals("success", userServiceImpl.sendMsgFrnd(1, 2, messageDto));
	}
	
	@Test
	void testSendMsgtoFrnd_Exception()
	{
		
		RuntimeException exception = assertThrows(RuntimeException.class,
													()->{
													userServiceImpl.sendMsgFrnd(0, 2, messageDto);
													});
		
		assertEquals("User or Friend not found", exception.getMessage());
	}
	
	@Test
	@Transactional
	void testSendFrdReq()
	{
		String userResult = userServiceImpl.sendFrdReq(6, 10);
		assertEquals("Friend Request Sent Successfully", userResult);
	}
	
	@Test
	void testSendFrdReq_Exception()
	{
		RuntimeException exception = assertThrows(RuntimeException.class,
				()->{
				userServiceImpl.sendFrdReq(0, 0);
				});

assertEquals("UserId or FriendId not found", exception.getMessage());
	}
	
	@Test
	@Transactional
	void testGetAllMsgsBtwnFrnds()
	{
		List<Messages> resultList = userServiceImpl.msgBtwUsers(1, 2);
		assertNotNull(resultList);
	}
	
	@Test
	void testGetAllMsgsBtwnFrnds_Exception()
	{
		
		RuntimeException exception = assertThrows(RuntimeException.class,
													()->{
													userServiceImpl.msgBtwUsers(1, 0);
													});
		
		assertEquals("User or Receiver not found", exception.getMessage());
	}
	
	@Test
	@Transactional
	void testGetAllLikes()
	{	
		List<Likes> result = userServiceImpl.getAllLikesPst(1);
		assertNotNull(result);
	}
	
	@Test
	void testGetAllLikes_Exception()
	{
		RuntimeException exception = assertThrows(RuntimeException.class,
				()->{
				userServiceImpl.getAllLikesPst(0);
				});

		assertEquals("User not found", exception.getMessage());
	}
	
	@Test
	void testGetAllLikesUser()
	{	
		List<Likes> likes = userServiceImpl.getAllLikesUsr(1);
		assertNotNull(likes);
	}
	
	@Test
	void testGetAllLikesUser_Exception()
	{
		RuntimeException exception = assertThrows(RuntimeException.class,
				()->{
				userServiceImpl.getAllLikesUsr(0);
				});

		assertEquals("User not found", exception.getMessage());
	}

}
