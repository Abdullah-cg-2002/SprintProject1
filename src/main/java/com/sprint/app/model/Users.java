package com.sprint.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * Entity class representing a User in the application.
 * This class defines the user model with attributes and relationships.
 */

@Entity
public class Users {
	/**
     * Unique identifier for the user.
     */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userID;
	/**
     * Username of the user.
     */
	@Pattern(regexp = "^[A-Za-z1-9 ]+$", message = "Guest name must only contain alphabetic characters and numbers")
    private String username;
 
    /**
     * The email address of the user.
     * This is validated to ensure it is in the correct email format.
     */
    private String email;
	/**
     * Password of the user.
     */
    
	private String password;
	/**
     * Profile picture of the user stored as a BLOB.
     */
	@Column(columnDefinition = "BLOB")
	private String profile_picture;
	/**
     * List of posts created by the user.
     */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<Posts> posts;
	/**
     * Sent friend requests.
     */
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    Set<Friends> friendsent;

    /**
     * Received friend requests.
     */
    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    Set<Friends> friendsrec;

    /**
     * Comments made by the user.
     */
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    List<Comments> comments = new ArrayList<>();

    /**
     * Notifications associated with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Notifications> notification = new ArrayList<>();

    /**
     * Likes made by the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Likes> likes = new ArrayList<>();

    /**
     * Messages sent by the user.
     */
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    List<Messages> sentmsg = new ArrayList<>();

    /**
     * Messages received by the user.
     */
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    List<Messages> receivedmsg = new ArrayList<>();

    /**
     * Groups administered by the user.
     */
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    List<Groups> groups = new ArrayList<>();

    /** Getters and Setters **/

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
