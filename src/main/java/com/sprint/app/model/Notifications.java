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
//public class Notifications {
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private int notificationID;
//	private String content;
//	private LocalDateTime timestamp;
//	
//	@ManyToOne
//	@JoinColumn(name="userID")
//	@JsonIgnore
//	private Users user;
//
//	public int getNotificationID() {
//		return notificationID;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
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
//	public Users getUser() {
//		return user;
//	}
//
//	public void setUser(Users user) {
//		this.user = user;
//	}
//	
//	
//	
//
//}
