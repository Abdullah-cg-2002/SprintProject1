package com.sprint.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.app.dto.MessageDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTesting {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testGetAllMsgsBtwnUsers() throws Exception
	{
		mockMvc.perform(get("/api/users/1/messages/3"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value("success"))
		.andExpect(jsonPath("$.data[0].message_text").value("Hello from user1 to user3"));	
	}
	
	@Test
	void testGetAllLikesPost() throws Exception
	{
		mockMvc.perform(get("/api/users/1/posts/likes"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value("success"))
		.andExpect(jsonPath("$.data[0].likesID").value(10001));
	}
	
	@Test
	void testGetAllLikesUser() throws Exception
	{
		mockMvc.perform(get("/api/users/1/likes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data[0].likesID").value(10001));
	}
	
	@Test
//	@Disabled
	void testSendMsgToUser() throws Exception
	{
		MessageDTO messageDto = new MessageDTO();
		messageDto.setMessage_text("Hello user3 by user 1");
		
		mockMvc.perform(post("/api/users/1/messages/send/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(messageDto)))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("message sent to friend successfully"));
	}
	
	@Test
	@Disabled
	void testSendFrndReq() throws Exception
	{
		mockMvc.perform(post("/api/users/5/friend-request/send/10"))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.status").value("success"))
		.andExpect(jsonPath("$.message").value("friend request sent successfully"));
			
	}

}
