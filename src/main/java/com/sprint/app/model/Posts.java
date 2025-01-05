package com.sprint.app.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Represents a Post entity in the application.
 * A post is created by a user and can have associated comments and likes.
 */
@Entity
public class Posts {

    /**
     * Unique identifier for each post.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int postID;
    
    /**
     * Content of the post.
     * The content must be between 1 and 500 characters.
     */
    @NotBlank(message = "Content cannot be empty")
    @Size(min = 1, max = 500, message = "Content must be between 1 and 500 characters")
    private String content;
    
    /**
     * Timestamp when the post was created.
     */
    private LocalDateTime timestamp;
    
    /**
     * The user who created this post.
     * A post is linked to one user.
     */
    @ManyToOne
    @JoinColumn(name="userID")
    @JsonIgnore
    private Users user;
    
    /**
     * List of comments associated with this post.
     * A post can have many comments.
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comments> comments;
    
    /**
     * List of likes associated with this post.
     * A post can have many likes.
     */
    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Likes> likes;

    /**
     * Gets the unique identifier of the post.
     * 
     * @return the post ID
     */
    public int getPostID() {
        return postID;
    }

    /**
     * Sets the unique identifier of the post.
     * 
     * @param postID the post ID to set
     */
    public void setPostID(int postID) {
        this.postID = postID;
    }

    /**
     * Gets the content of the post.
     * 
     * @return the content of the post
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the post.
     * 
     * @param content the content to set for the post
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the timestamp when the post was created.
     * 
     * @return the timestamp of the post
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the post was created.
     * 
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets the user who created the post.
     * 
     * @return the user who created the post
     */
    public Users getUser() {
        return user;
    }

    /**
     * Sets the user who created the post.
     * 
     * @param user the user to set for the post
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * Gets the list of comments associated with this post.
     * 
     * @return the list of comments
     */
    public List<Comments> getComments() {
        return comments;
    }

    /**
     * Sets the list of comments associated with this post.
     * 
     * @param comments the list of comments to set
     */
    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    /**
     * Gets the list of likes associated with this post.
     * 
     * @return the list of likes
     */
    public List<Likes> getLikes() {
        return likes;
    }

    /**
     * Sets the list of likes associated with this post.
     * 
     * @param likes the list of likes to set
     */
    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }
}
