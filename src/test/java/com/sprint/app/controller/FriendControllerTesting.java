package com.sprint.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.app.dto.MessageDTO;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class FriendControllerTesting {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@Order(1)
	void testGetAllMsgsBtwnFrnds() throws Exception
	{
		mockMvc.perform(get("/api/friends/100001/messages"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data[0].messageID").value(1000002));
	}
	
	@Test
	@Order(2)
	void testSendMsgToFrnd() throws Exception
	{
		MessageDTO messageDto = new MessageDTO();
		messageDto.setMessage_text("New Msg from your friend");
		mockMvc.perform(post("/api/friends/100001/message/send")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(messageDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("Message Sent Successfully!"));
	}

}
