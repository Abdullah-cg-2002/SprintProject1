package com.sprint.app.dto;

import com.sprint.app.model.Users;

public class MessageDTO {
	
	private String message_text;
	private Users sender;
	private Users receiver;
	public String getMessage_text() {
		return message_text;
	}
	public void setMessage_text(String message_text) {
		this.message_text = message_text;
	}
	public Users getSender() {
		return sender;
	}
	public void setSender(Users sender) {
		this.sender = sender;
	}
	public Users getReceiver() {
		return receiver;
	}
	public void setReceiver(Users receiver) {
		this.receiver = receiver;
	}
	
	
	

}
