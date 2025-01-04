package com.sprint.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Entity class for Notifications.
 * Stores notification details such as content, timestamp, and associated user.
 */
@Entity
public class Notifications {
	
	/**
	 * Unique identifier for the notification.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int notificationID;

	/**
	 * The content or message of the notification.
	 */
	private String content;

	/**
	 * The timestamp when the notification was created.
	 */
	private LocalDateTime timestamp;
	
	/**
	 * The user associated with the notification.
	 * Marked with @JsonIgnore to avoid serialization of user details.
	 */
	@ManyToOne
	@JoinColumn(name="userID")
	@JsonIgnore
	private Users user;

	/**
	 * Gets the notification ID.
	 * @return the unique identifier of the notification
	 */
	public int getNotificationID() {
		return notificationID;
	}

	/**
	 * Gets the content of the notification.
	 * @return the notification content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content of the notification.
	 * @param content the notification content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the timestamp of the notification.
	 * @return the timestamp of creation
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp of the notification.
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the user associated with the notification.
	 * @return the associated user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user associated with the notification.
	 * @param user the user to associate
	 */
	public void setUser(Users user) {
		this.user = user;
	}
}
