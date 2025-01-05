package com.sprint.app.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a Comment entity in the application.
 * A comment is associated with a specific post and user.
 * Contains a comment's text, timestamp, and relationships with post and user entities.
 */
@Entity
public class Comments {

    /**
     * Unique identifier for each comment.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int commentID;
    
    /**
     * Text content of the comment.
     * The comment must have between 1 and 500 characters.
     */
    @NotNull(message = "Comments should not be null")
    @Size(min = 1, max = 500, message = "Comment text must be between 1 and 500 characters")
    @Column(name="comment_text")
    private String commentText;
    
    /**
     * Timestamp when the comment was created.
     */
    private LocalDateTime timestamp;
    
    /**
     * The post associated with this comment.
     * A comment is linked to one post.
     */
    @ManyToOne
    @JoinColumn(name="postID")
    @JsonIgnore
    private Posts post;
    
    /**
     * The user who made the comment.
     * A comment is linked to one user.
     */
    @ManyToOne
    @JoinColumn(name="userID")
    @JsonIgnore
    private Users users;

   

	/**
     * Gets the unique identifier of the comment.
     * 
     * @return the comment ID
     */
    public int getCommentid() {
        return commentID;
    }

    /**
     * Sets the unique identifier of the comment.
     * 
     * @param commentid the comment ID to set
     */
    public void setCommentid(int commentid) {
        this.commentID = commentid;
    }

    /**
     * Gets the text content of the comment.
     * 
     * @return the comment text
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Sets the text content of the comment.
     * 
     * @param commentText the comment text to set
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     * Gets the timestamp when the comment was created.
     * 
     * @return the timestamp of the comment
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the comment was created.
     * 
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets the post associated with this comment.
     * 
     * @return the post associated with the comment
     */
    public Posts getPost() {
        return post;
    }

    /**
     * Sets the post associated with this comment.
     * 
     * @param post the post to associate with the comment
     */
    public void setPost(Posts post) {
        this.post = post;
    }

    /**
     * Gets the user who made the comment.
     * 
     * @return the user who made the comment
     */
    public Users getUsers() {
        return users;
    }

    /**
     * Sets the user who made the comment.
     * 
     * @param users the user to associate with the comment
     */
    public void setUsers(Users users) {
        this.users = users;
    }
}
