package com.sprint.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Posts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int postID;
	private String content;
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name="userID")
	@JsonIgnore
	private Users user;
	
	@OneToMany(mappedBy = "post",cascade=CascadeType.ALL)
	private List<Comments> comments;
	
	@OneToMany(mappedBy = "posts",cascade=CascadeType.ALL)
	private List<Likes> likes;

	public int getPostID() {
		return postID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public List<Likes> getLikes() {
		return likes;
	}

	public void setLikes(List<Likes> likes) {
		this.likes = likes;
	}
	
	

}
