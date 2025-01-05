package com.sprint.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
import com.sprint.app.services.FriendService;

@WebMvcTest(FriendRestController.class)
@AutoConfigureMockMvc
public class FriendControllerTesting {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private FriendService friendService;
	
	@Autowired
	private ObjectMapper objmap;
	
	
	private Messages message;
	private Users sender;
	private Users receiver;
	private MessageDTO messageDto;
	
	@BeforeEach
	void setup()
	{
		sender = new Users();
		receiver = new Users();
		
		sender.setUserID(1);
		receiver.setUserID(2);
		
		Messages message = new Messages();
		message.setMessage_text("new Message");
		message.setSender(sender);
		message.setReceiver(receiver);
		
		messageDto = new MessageDTO();
		messageDto.setMessage_text("New Msg from your friend");
	}
	
	@Test
	void testGetAllMsgsBtwnFrnds() throws Exception
	{
		when(friendService.getAllMsgBtwFrnds(100001)).thenReturn(Arrays.asList(message));
		mockMvc.perform(get("/api/friends/100001/messages"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"));
	}
	
	@Test
	void testGetAllMsgsBtwnFrnds_Exception() throws Exception
	{
		when(friendService.getAllMsgBtwFrnds(100001)).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/api/friends/100001/messages"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("No messages shared between friends"));
		
	}
	@Test
	void testSendMsgToFrnd() throws Exception {
		
		String message = "message sent";
	    when(friendService.sendMsg(100002, messageDto)).thenReturn(message);

	    mockMvc.perform(post("/api/friends/100002/message/send")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(objmap.writeValueAsString(messageDto)))
	            .andExpect(jsonPath("$.status").value("success"))
	            .andExpect(jsonPath("$.message").value("message sent"));
	}


}
