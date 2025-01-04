package com.sprint.app.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) for representing a User.
 * This class is used to transfer user-related data between layers, such as from the controller to the service.
 * It contains information about the user’s ID, username, email, profile picture, and associated post, message, group, and friend IDs.
 */
public class UserDTO {

    /**
     * The unique identifier for the user.
     */
    private int userID;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The profile picture of the user, represented as a string (e.g., URL or file path).
     */
    private String profile_picture;

    /**
     * A list of post IDs associated with the user. This is not a list of the full post objects.
     */
    private List<Integer> postIDs;

    /**
     * A list of sent message IDs associated with the user. This is not a list of the full message objects.
     */
    private List<Integer> sentMessageIDs;

    /**
     * A list of received message IDs associated with the user.
     */
    private List<Integer> receivedMessageIDs;

    /**
     * A list of group IDs associated with the user.
     */
    private List<Integer> groupIDs;

    /**
     * A list of friend IDs associated with the user.
     */
    private List<Integer> friendIDs;

    /**
     * Retrieves the user ID.
     *
     * @return the unique ID of the user
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID.
     *
     * @param userID the ID to set for the user
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the user.
     *
     * @param username the username to set for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for the user.
     *
     * @param email the email address to set for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the profile picture URL or file path of the user.
     *
     * @return the profile picture of the user
     */
    public String getProfile_picture() {
        return profile_picture;
    }

    /**
     * Sets the profile picture for the user.
     *
     * @param profile_picture the profile picture to set for the user
     */
    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    /**
     * Retrieves the list of post IDs associated with the user.
     *
     * @return the list of post IDs associated with the user
     */
    public List<Integer> getPostIDs() {
        return postIDs;
    }

    /**
     * Sets the list of post IDs for the user.
     *
     * @param postIDs the list of post IDs to set for the user
     */
    public void setPostIDs(List<Integer> postIDs) {
        this.postIDs = postIDs;
    }

    /**
     * Retrieves the list of sent message IDs associated with the user.
     *
     * @return the list of sent message IDs associated with the user
     */
    public List<Integer> getSentMessageIDs() {
        return sentMessageIDs;
    }

    /**
     * Sets the list of sent message IDs for the user.
     *
     * @param sentMessageIDs the list of sent message IDs to set for the user
     */
    public void setSentMessageIDs(List<Integer> sentMessageIDs) {
        this.sentMessageIDs = sentMessageIDs;
    }

    /**
     * Retrieves the list of received message IDs associated with the user.
     *
     * @return the list of received message IDs associated with the user
     */
    public List<Integer> getReceivedMessageIDs() {
        return receivedMessageIDs;
    }

    /**
     * Sets the list of received message IDs for the user.
     *
     * @param receivedMessageIDs the list of received message IDs to set for the user
     */
    public void setReceivedMessageIDs(List<Integer> receivedMessageIDs) {
        this.receivedMessageIDs = receivedMessageIDs;
    }

    /**
     * Retrieves the list of group IDs associated with the user.
     *
     * @return the list of group IDs associated with the user
     */
    public List<Integer> getGroupIDs() {
        return groupIDs;
    }

    /**
     * Sets the list of group IDs for the user.
     *
     * @param groupIDs the list of group IDs to set for the user
     */
    public void setGroupIDs(List<Integer> groupIDs) {
        this.groupIDs = groupIDs;
    }

    /**
     * Retrieves the list of friend IDs associated with the user.
     *
     * @return the list of friend IDs associated with the user
     */
    public List<Integer> getFriendIDs() {
        return friendIDs;
    }

    /**
     * Sets the list of friend IDs for the user.
     *
     * @param friendIDs the list of friend IDs to set for the user
     */
    public void setFriendIDs(List<Integer> friendIDs) {
        this.friendIDs = friendIDs;
    }
}
