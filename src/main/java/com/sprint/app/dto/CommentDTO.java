package com.sprint.app.dto;


import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;

public class CommentDTO {
	
	private String comment_text;
	private Posts post;
	private Users users;
	public String getComment_text() {
		return comment_text;
	}
	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
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
