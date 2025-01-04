//package com.sprint.app.model;
//
//import java.time.LocalDateTime;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//
//@Entity
//public class Messages {
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private int messageID;
//	private String message_text;
//	private LocalDateTime timestamp;
//	
//	@ManyToOne
//	@JoinColumn(name="senderID", referencedColumnName = "userID")
//	@JsonIgnore
//	private Users sender;
//	
//	@ManyToOne
//	@JoinColumn(name="receiverID", referencedColumnName = "userID")
//	@JsonIgnore
//	private Users receiver;
//
//	public int getMessageID() {
//		return messageID;
//	}
//
//	public String getMessage_text() {
//		return message_text;
//	}
//
//	public void setMessage_text(String message_text) {
//		this.message_text = message_text;
//	}
//
//	public LocalDateTime getTimestamp() {
//		return timestamp;
//	}
//
//	public void setTimestamp(LocalDateTime timestamp) {
//		this.timestamp = timestamp;
//	}
//
//	public Users getSender() {
//		return sender;
//	}
//
//	public void setSender(Users sender) {
//		this.sender = sender;
//	}
//
//	public Users getReceiver() {
//		return receiver;
//	}
//
//	public void setReceiver(Users receiver) {
//		this.receiver = receiver;
//	}
//	
//	
//	
//	
//
//}
