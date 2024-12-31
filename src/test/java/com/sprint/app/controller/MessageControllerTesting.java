package com.sprint.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.services.MessageService;



@AutoConfigureMockMvc
@WebMvcTest(MessageRestController.class)
public class MessageControllerTesting {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private MessageService messageService;
	
	@Autowired
	private ObjectMapper objmap;
	
	
	private MessageDTO messageDto;
	private Messages message;
	private Users sender;
	private Users receiver;
	private List<Messages> messages;
	
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
	}
	
	
	@Test
	void testGetAllMsgs() throws Exception
	{
		when(messageService.getAllMsgs()).thenReturn(messages);
		mockMvc.perform(get("/api/messages"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data[0].message_text").value("Messages Edited by User1"));
	}
	
	@Test
	void testSpecificMsg() throws Exception
	{
		when(messageService.getSpecificMsg(1000002)).thenReturn(message);
		mockMvc.perform(get("/api/messages/1000002"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data[0].message_text").value("Messages Edited by User1"));
	}
	
	@Test
	void testCreateMsg() throws Exception
	{
		when(messageService.createMsg(messageDto)).thenReturn("success");
		
		mockMvc.perform(post("/api/messages")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objmap.writeValueAsString(messageDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value("message sent successfully"));
	}
	
	@Test
	void testUpdateMsg() throws Exception
	{	
		when(messageService.updateMsg(1000009, message)).thenReturn("success");
		mockMvc.perform(put("/api/messages/1000009")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objmap.writeValueAsString(message)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("message updated successfully"));
	}
	
	@Test
	void testDeleteMsg() throws Exception
	{
		when(messageService.deleteMsg(10000091)).thenReturn("success");
		mockMvc.perform(delete("/api/messages/10000091"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("message deleted successfully"));
	}
}
