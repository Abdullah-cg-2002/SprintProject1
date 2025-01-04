package com.sprint.app.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for representing a Like.
 * This class is used to transfer like-related data between layers, such as from the controller to service.
 * It contains information about the like's ID, timestamp, the user who liked the post, and the post being liked.
 */
public class LikesDTO {

    /**
     * The unique identifier of the like.
     */
    private int likesID;

    /**
     * The timestamp when the like was created.
     */
    private LocalDateTime timestamp;

    /**
     * The ID of the user who liked the post.
     */
    private int userID;

    /**
     * The ID of the post that was liked.
     */
    private int postID;

    /**
     * Default constructor for LikesDTO.
     * Initializes an empty LikesDTO object.
     */
    public LikesDTO() {}

    /**
     * Parameterized constructor for LikesDTO.
     * Initializes a LikesDTO object with the provided values for likesID, timestamp, userID, and postID.
     *
     * @param likesID the unique ID of the like
     * @param timestamp the timestamp when the like was created
     * @param userID the ID of the user who liked the post
     * @param postID the ID of the post that was liked
     */
    public LikesDTO(int likesID, LocalDateTime timestamp, int userID, int postID) {
        this.likesID = likesID;
        this.timestamp = timestamp;
        this.userID = userID;
        this.postID = postID;
    }

    /**
     * Retrieves the ID of the like.
     *
     * @return the unique ID of the like
     */
    public int getLikesID() {
        return likesID;
    }

    /**
     * Retrieves the timestamp when the like was created.
     *
     * @return the timestamp of the like
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the like was created.
     *
     * @param timestamp the timestamp to set for the like
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the ID of the user who liked the post.
     *
     * @return the ID of the user who liked the post
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the ID of the user who liked the post.
     *
     * @param userID the user ID to set for the like
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Retrieves the ID of the post that was liked.
     *
     * @return the ID of the post that was liked
     */
    public int getPostID() {
        return postID;
    }
}
