package com.sprint.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
import com.sprint.app.model.Friends;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Status;
import com.sprint.app.model.Users;
import com.sprint.app.repo.FriendsRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.UserServiceImpl;
import com.sprint.app.services.FriendService;
import com.sprint.app.services.MessageService;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTesting {
	
	@Mock
	private UserRepo userRepo;
	
	@Mock
	private MessageService messageService;
	
	@Mock
	private FriendService friendService;
	
	@Mock
	private FriendsRepo friendsRepo;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	private Users user;
	private Users friend;
	private MessageDTO messageDto;
	private Friends friends;
	private Messages messages;
	
	@BeforeEach
	void setup()
	{
		user = new Users();
		friend = new Users();
		
		user.setUsername("sujit");
		user.setEmail("sujit@cg.com");
		user.setPassword("sujit@123");
		user.setProfile_picture("sujitpicture");
		
		//receiver details
		friend.setUsername("nishaanth");
		friend.setEmail("nishuu@cg.com");
		friend.setPassword("nishuu@123");
		friend.setProfile_picture("nishaanthpicture");
		
		messageDto = new MessageDTO();
		messageDto.setMessage_text("Hi Nishaanth");
		
		friends = new Friends();
		friends.setStatus(Status.PENDING);
		friends.setUser1(user);
		friends.setUser2(friend);
		
		messages = new Messages();
		messages.setMessage_text("Hello Nishaanth");
		messages.setReceiver(friend);
		messages.setSender(user);
		
	}
	
	@Test
	void testSendMsgtoFrnd()
	{
		when(userRepo.findById(1)).thenReturn(Optional.of(user));
		when(userRepo.findById(2)).thenReturn(Optional.of(friend));
		
		String userResult = userServiceImpl.sendMsgFrnd(1, 2, messageDto);
		assertEquals("success", userResult);
		verify(messageService).createMsg(messageDto);
	}
	
	@Test
	void testSendMsgtoFrnd_Exception()
	{
		when(userRepo.findById(1)).thenReturn(Optional.of(user));
		when(userRepo.findById(2)).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class,
													()->{
													userServiceImpl.sendMsgFrnd(1, 2, messageDto);
													});
		
		assertEquals("User or Friend not found", exception.getMessage());
	}
	
	@Test
	void testSendFrdReq()
	{
		
		when(friendService.addFrnd(1, 2)).thenReturn("Friend Request Sent Successfully");

		String userResult = userServiceImpl.sendFrdReq(1, 2);
		assertEquals("Friend Request Sent Successfully", userResult);
	}
	
	@Test
	void testGetAllMsgsBtwnFrnds()
	{
		when(userRepo.findById(1)).thenReturn(Optional.of(user));
		when(userRepo.findById(2)).thenReturn(Optional.of(friend));
		List<Messages> msgs = List.of(messages);
		when(messageService.getMsgSpecificUser(1)).thenReturn(msgs);
		List<Messages> resultList = userServiceImpl.msgBtwUsers(1, 2);
		assertNotNull(resultList);
	}
	
	@Test
	void testGetAllMsgsBtwnFrnds_Exception()
	{
		when(userRepo.findById(1)).thenReturn(Optional.empty());
		when(userRepo.findById(2)).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class,
													()->{
													userServiceImpl.msgBtwUsers(1, 2);
													});
		
		assertEquals("User or Receiver not found", exception.getMessage());
	}
	
	@Test
	void testGetAllLikes()
	{
		Posts post = new Posts();
		Likes like = new Likes();
		like.setLikesID(100);
		like.setTimestamp(LocalDateTime.now());
		List<Likes> likes = List.of(like);
		post.setLikes(likes);
		List<Posts> postlist = List.of(post);
		
		user = mock(Users.class);
		when(userRepo.findById(1)).thenReturn(Optional.of(user));
		when(user.getPosts()).thenReturn(postlist);
		
		List<Likes> result = userServiceImpl.getAllLikesPst(1);
		assertNotNull(result);
	}
	
	@Test
	void testGetAllLikesUser()
	{
		user = mock(Users.class);
		when(userRepo.findById(1)).thenReturn(Optional.of(user));
		when(user.getLikes()).thenReturn(new ArrayList<>());
		
		List<Likes> likes = userServiceImpl.getAllLikesUsr(1);
		assertNotNull(likes);
	}

}
