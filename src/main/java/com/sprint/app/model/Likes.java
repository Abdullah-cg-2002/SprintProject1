package com.sprint.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Likes {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int likesID;
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name="userID")
	@JsonIgnore
	private Users user;
	
	@ManyToOne
	@JoinColumn(name="postID")
	@JsonIgnore
	private Posts posts;
	
	

	public void setLikesID(int likesID) {
		this.likesID = likesID;
	}

	public int getLikesID() {
		return likesID;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}
	
	

}
