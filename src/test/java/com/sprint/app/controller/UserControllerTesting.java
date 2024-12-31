package com.sprint.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.services.UserService;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc
public class UserControllerTesting {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objmap;
	
	@MockitoBean
	private UserService userService;
	
	private MessageDTO messageDto;
	private Messages message;
	private Users sender;
	private Users receiver;
	private List<Messages> messages;
	private Likes like;
	
	@BeforeEach
	void setup()
	{
		sender = new Users();
		receiver = new Users();
		
		sender.setUserID(1);
		receiver.setUserID(5);
		
		messageDto = new MessageDTO();
		
		messageDto.setMessage_text("Hi user5 from user1");
		messageDto.setReceiver(receiver);
		messageDto.setSender(sender);
		
		message = new Messages();
		message.setMessage_text("Messages Edited by User1");
		
		messages = List.of(message);
		
		like = new Likes();
		like.setLikesID(100);
		like.setTimestamp(LocalDateTime.now());
	}
	
	@Test
	void testGetAllMsgsBtwnUsers() throws Exception
	{
		when(userService.msgBtwUsers(1, 3)).thenReturn(messages);
		
		mockMvc.perform(get("/api/users/1/messages/3"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value("success"))
		.andExpect(jsonPath("$.data[0].message_text").value("Messages Edited by User1"));	
	}
	
	@Test
	void testGetAllLikesPost() throws Exception
	{
		when(userService.getAllLikesPst(1)).thenReturn(Arrays.asList(like));
		mockMvc.perform(get("/api/users/1/posts/likes"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value("success"))
		.andExpect(jsonPath("$.data[0].likesID").value(100));
	}
	
	@Test
	void testGetAllLikesUser() throws Exception
	{
		when(userService.getAllLikesUsr(1)).thenReturn(Arrays.asList(like));
		
		mockMvc.perform(get("/api/users/1/likes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data[0].likesID").value(100));
	}
	
	@Test
	void testSendMsgToUser() throws Exception
	{
		when(userService.sendMsgFrnd(1, 3, messageDto)).thenReturn(null);
		mockMvc.perform(post("/api/users/1/messages/send/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objmap.writeValueAsString(messageDto)))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("message sent to friend successfully"));
	}
	
	@Test
	void testSendFrndReq() throws Exception
	{
		when(userService.sendFrdReq(7, 10)).thenReturn(null);
		mockMvc.perform(post("/api/users/7/friend-request/send/10"))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.status").value("success"))
		.andExpect(jsonPath("$.message").value("friend request sent successfully"));
			
	}

}
