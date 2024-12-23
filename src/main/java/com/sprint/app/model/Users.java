package com.sprint.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
public class Users {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userID;
	private String username;
	private String email;
	private String password;
	
	@Column(columnDefinition = "BLOB")
	private String profile_picture;
	
	@OneToMany(mappedBy = "user")
	List<Posts> posts;
	
	@OneToMany(mappedBy= "user1")
	Set<Friends> friendsent;
	
	@OneToMany(mappedBy = "user2")
	Set<Friends> friendsrec;
	
	@OneToMany(mappedBy = "users")
	List<Comments> comments = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	List<Notifications> notification = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	List<Likes> likes = new ArrayList<>();
	
	@OneToMany(mappedBy = "sender")
	List<Messages> sentmsg = new ArrayList<>();
	
	@OneToMany(mappedBy = "receiver")
	List<Messages> receivedmsg = new ArrayList<>();
	
	@OneToMany(mappedBy = "admin")
	List<Groups> groups = new ArrayList<>();

	public int getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}

	public List<Posts> getPosts() {
		return posts;
	}

	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}

	public Set<Friends> getFriendsent() {
		return friendsent;
	}

	public void setFriendsent(Set<Friends> friendsent) {
		this.friendsent = friendsent;
	}
	

	public Set<Friends> getFriendsrec() {
		return friendsrec;
	}

	public void setFriendsrec(Set<Friends> friendsrec) {
		this.friendsrec = friendsrec;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public List<Notifications> getNotification() {
		return notification;
	}

	public void setNotification(List<Notifications> notification) {
		this.notification = notification;
	}

	public List<Likes> getLikes() {
		return likes;
	}

	public void setLikes(List<Likes> likes) {
		this.likes = likes;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}
	
	public List<Messages> getSentmsg() {
		return sentmsg;
	}

	public void setSentmsg(List<Messages> sentmsg) {
		this.sentmsg = sentmsg;
	}

	public List<Messages> getReceivedmsg() {
		return receivedmsg;
	}

	public void setReceivedmsg(List<Messages> receivedmsg) {
		this.receivedmsg = receivedmsg;
	}
	
	
	

}
