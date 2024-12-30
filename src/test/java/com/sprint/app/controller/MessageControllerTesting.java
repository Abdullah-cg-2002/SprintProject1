package com.sprint.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;



@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTesting {
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	void testGetAllMsgs() throws Exception
	{	
		mockMvc.perform(get("/api/messages"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data[0].message_text").value("Hello from user1 to user3"));
	}
	
	@Test
	void testSpecificMsg() throws Exception
	{
		mockMvc.perform(get("/api/messages/1000002"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data[0].message_text").value("Reply from user2 to user1"));
	}
	
	@Test
	@Disabled
	void testCreateMsg() throws Exception
	{
		Users sender = new Users();
		Users receiver = new Users();
		
		sender.setUserID(1);
		receiver.setUserID(5);
		
		MessageDTO messageDto = new MessageDTO();
		
		messageDto.setMessage_text("Hi user5 from user1");
		messageDto.setReceiver(receiver);
		messageDto.setSender(sender);
		
		mockMvc.perform(post("/api/messages")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(messageDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("message sent successfully"));
	}
	
	@Test
	@Disabled
	void testUpdateMsg() throws Exception
	{
		Messages messages = new Messages();
		messages.setMessage_text("Messages Edited by User1");
		
		mockMvc.perform(put("/api/messages/1000076")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(messages)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("message updated successfully"));
	}
	
	@Test
	@Disabled
	void testDeleteMsg() throws Exception
	{
		mockMvc.perform(delete("/api/messages/1000077"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("message deleted successfully"));
	}
}
