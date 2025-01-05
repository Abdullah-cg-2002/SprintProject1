package com.sprint.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comments {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int commentID;
	//@NotBlank(message="Comment is requried")
	@Column(name="comment_text")
	private String commentText;
	@Column(name="timestamp")
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name="postID")
	@JsonIgnore
	private Posts post;
	
	@ManyToOne
	@JoinColumn(name="userID")
	@JsonIgnore
	private Users users;

	public int getCommentid() {
		return commentID;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Posts getPost() {
		return post;
	}

	public void setPost(Posts post) {
		this.post = post;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}
