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
 * Entity class representing a Like for a Post in the application.
 * The Like entity contains information about the user who liked the post,
 * the post that was liked, and the timestamp of when the like occurred.
 * This class is mapped to a table in the database and uses JPA annotations
 * for persistence.
 */
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

    /**
     * Retrieves the timestamp of when the like was created.
     *
     * @return the timestamp of the like
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of when the like was created.
     *
     * @param timestamp the timestamp to set for the like
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the user who liked the post.
     *
     * @return the user who liked the post, represented as a {@link Users} entity
     */
    public Users getUser() {
        return user;
    }

    /**
     * Sets the user who liked the post.
     *
     * @param user the user who liked the post, represented as a {@link Users} entity
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * Retrieves the post that was liked.
     *
     * @return the post that was liked, represented as a {@link Posts} entity
     */
    public Posts getPosts() {
        return posts;
    }

    /**
     * Sets the post that was liked.
     *
     * @param posts the post that was liked, represented as a {@link Posts} entity
     */
    public void setPosts(Posts posts) {
        this.posts = posts;
    }

	public void setLikeID(int likesID) {
		this.likesID=likesID;
		
	}
	
}
