package com.sprint.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a user in the system.
 * This class defines the structure of a user entity, including personal details
 * such as username, email, password, profile picture, and associations with other entities like posts,
 * friends, comments, notifications, and messages. It is mapped to a database entity using JPA annotations.
 */
@Entity
public class Users {
    
    /**
     * The unique identifier for the user.
     * This is the primary key for the Users entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    private int userID;

    /**
     * The username of the user.
     * This must contain only alphabetic characters, numbers, and spaces.
     * Validated with a regular expression pattern.
     */
    @Pattern(regexp = "^[A-Za-z1-9 ]+$", message = "Guest name must only contain alphabetic characters and numbers")
    private String username;

    /**
     * The email address of the user.
     * This is validated to ensure it is in the correct email format.
     */
    @Email(message = "enter valid email id")
    private String email;

    /**
     * The password of the user.
     * This is stored as a string.
     */
    private String password;

    /**
     * The profile picture of the user.
     * Stored as a BLOB (Binary Large Object).
     */
    @Column(columnDefinition = "BLOB")
    private String profile_picture;

    /**
     * A list of posts created by the user.
     * This is a one-to-many relationship with the Posts entity.
     * Cascade type ALL ensures that associated posts are managed along with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Posts> posts;

    /**
     * A set of friends that the user has sent friend requests to.
     * This is a one-to-many relationship with the Friends entity, where the user is the sender (user1).
     * Cascade type ALL ensures that associated friend relationships are managed along with the user.
     */
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    Set<Friends> friendsent;

    /**
     * A set of friends that the user has received friend requests from.
     * This is a one-to-many relationship with the Friends entity, where the user is the receiver (user2).
     * Cascade type ALL ensures that associated friend relationships are managed along with the user.
     */
    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    Set<Friends> friendsrec;

    /**
     * A list of comments made by the user.
     * This is a one-to-many relationship with the Comments entity.
     * Cascade type ALL ensures that associated comments are managed along with the user.
     */
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    List<Comments> comments = new ArrayList<>();

    /**
     * A list of notifications for the user.
     * This is a one-to-many relationship with the Notifications entity.
     * Cascade type ALL ensures that associated notifications are managed along with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Notifications> notification = new ArrayList<>();

    /**
     * A list of likes associated with the user's posts.
     * This is a one-to-many relationship with the Likes entity.
     * Cascade type ALL ensures that associated likes are managed along with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Likes> likes = new ArrayList<>();

    /**
     * A list of messages that the user has sent.
     * This is a one-to-many relationship with the Messages entity, where the user is the sender.
     * Cascade type ALL ensures that associated sent messages are managed along with the user.
     */
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    List<Messages> sentmsg = new ArrayList<>();

    /**
     * A list of messages that the user has received.
     * This is a one-to-many relationship with the Messages entity, where the user is the receiver.
     * Cascade type ALL ensures that associated received messages are managed along with the user.
     */
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    List<Messages> receivedmsg = new ArrayList<>();

    /**
     * A list of groups that the user is an admin of.
     * This is a one-to-many relationship with the Groups entity.
     * Cascade type ALL ensures that associated groups are managed along with the user.
     */
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    List<Groups> groups = new ArrayList<>();

    /**
     * Sets the unique identifier for the user.
     *
     * @param userID the unique identifier for the user.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return the user ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the profile picture of the user.
     *
     * @return the profile picture as a string.
     */
    public String getProfile_picture() {
        return profile_picture;
    }

    /**
     * Sets the profile picture of the user.
     *
     * @param profile_picture the profile picture to set.
     */
    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    /**
     * Gets the list of posts made by the user.
     *
     * @return the list of posts.
     */
    public List<Posts> getPosts() {
        return posts;
    }

    /**
     * Sets the list of posts for the user.
     *
     * @param posts the posts to set.
     */
    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    /**
     * Gets the set of friends the user has sent friend requests to.
     *
     * @return the set of sent friend requests.
     */
    public Set<Friends> getFriendsent() {
        return friendsent;
    }

    /**
     * Sets the set of friends the user has sent friend requests to.
     *
     * @param friendsent the sent friend requests to set.
     */
    public void setFriendsent(Set<Friends> friendsent) {
        this.friendsent = friendsent;
    }

    /**
     * Gets the set of friends the user has received friend requests from.
     *
     * @return the set of received friend requests.
     */
    public Set<Friends> getFriendsrec() {
        return friendsrec;
    }

    /**
     * Sets the set of friends the user has received friend requests from.
     *
     * @param friendsrec the received friend requests to set.
     */
    public void setFriendsrec(Set<Friends> friendsrec) {
        this.friendsrec = friendsrec;
    }

    /**
     * Gets the list of comments made by the user.
     *
     * @return the list of comments.
     */
    public List<Comments> getComments() {
        return comments;
    }

    /**
     * Sets the list of comments made by the user.
     *
     * @param comments the comments to set.
     */
    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    /**
     * Gets the list of notifications for the user.
     *
     * @return the list of notifications.
     */
    public List<Notifications> getNotification() {
        return notification;
    }

    /**
     * Sets the list of notifications for the user.
     *
     * @param notification the notifications to set.
     */
    public void setNotification(List<Notifications> notification) {
        this.notification = notification;
    }

    /**
     * Gets the list of likes for the user.
     *
     * @return the list of likes.
     */
    public List<Likes> getLikes() {
        return likes;
    }

    /**
     * Sets the list of likes for the user.
     *
     * @param likes the likes to set.
     */
    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    /**
     * Gets the list of groups that the user is an admin of.
     *
     * @return the list of groups.
     */
    public List<Groups> getGroups() {
        return groups;
    }

    /**
     * Sets the list of groups that the user is an admin of.
     *
     * @param groups the groups to set.
     */
    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    /**
     * Gets the list of messages the user has sent.
     *
     * @return the list of sent messages.
     */
    public List<Messages> getSentmsg() {
        return sentmsg;
    }

    /**
     * Sets the list of messages the user has sent.
     *
     * @param sentmsg the sent messages to set.
     */
    public void setSentmsg(List<Messages> sentmsg) {
        this.sentmsg = sentmsg;
    }

    /**
     * Gets the list of messages the user has received.
     *
     * @return the list of received messages.
     */
    public List<Messages> getReceivedmsg() {
        return receivedmsg;
    }

    /**
     * Sets the list of messages the user has received.
     *
     * @param receivedmsg the received messages to set.
     */
    public void setReceivedmsg(List<Messages> receivedmsg) {
        this.receivedmsg = receivedmsg;
    }
}
