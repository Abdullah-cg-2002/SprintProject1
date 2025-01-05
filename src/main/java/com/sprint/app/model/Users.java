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

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Represents a user in the application.
 * This class is mapped to the 'Users' table in the database.
 * It contains the user's information such as username, email, password, profile picture, and various related entities such as posts, friends, comments, and messages.
 */
@Entity
public class Users {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    /**
     * The username of the user. Cannot be blank and must be between 3 and 50 characters.
     */
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /**
     * The email address of the user. Cannot be blank and must be a valid email.
     */
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    /**
     * The password of the user. Cannot be blank and must be at least 6 characters long.
     */
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    /**
     * The profile picture of the user, stored as a BLOB.
     */
    @Column(columnDefinition = "BLOB")
    private String profile_picture;

    /**
     * The list of posts created by the user.
     */
    @OneToMany(mappedBy = "user")
    private List<Posts> posts;

    /**
     * The set of friends that the user has sent friend requests to.
     */
    @OneToMany(mappedBy = "user1")
    private Set<Friends> friendsent;

    /**
     * The set of friends that the user has received friend requests from.
     */
    @OneToMany(mappedBy = "user2")
    private Set<Friends> friendsrec;

    /**
     * The list of comments made by the user.
     */
    @OneToMany(mappedBy = "users")
    private List<Comments> comments = new ArrayList<>();

    /**
     * The list of notifications associated with the user.
     */
    @OneToMany(mappedBy = "user")
    private List<Notifications> notification = new ArrayList<>();

    /**
     * The list of likes made by the user.
     */
    @OneToMany(mappedBy = "user")
    private List<Likes> likes = new ArrayList<>();

    /**
     * The list of messages sent by the user.
     */
    @OneToMany(mappedBy = "sender")
    private List<Messages> sentmsg = new ArrayList<>();

    /**
     * The list of messages received by the user.
     */
    @OneToMany(mappedBy = "receiver")
    private List<Messages> receivedmsg = new ArrayList<>();

    /**
     * The list of groups that the user is an admin of.
     */
    @OneToMany(mappedBy = "admin")
    private List<Groups> groups = new ArrayList<>();

    /**
     * Gets the unique user ID.
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
     * @return the profile picture.
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
     * Gets the list of posts created by the user.
     * 
     * @return the list of posts.
     */
    public List<Posts> getPosts() {
        return posts;
    }

    /**
     * Sets the list of posts created by the user.
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
     * @param friendsent the friends to set.
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
     * @param friendsrec the friends to set.
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
     * Gets the list of notifications associated with the user.
     * 
     * @return the list of notifications.
     */
    public List<Notifications> getNotification() {
        return notification;
    }

    /**
     * Sets the list of notifications associated with the user.
     * 
     * @param notification the notifications to set.
     */
    public void setNotification(List<Notifications> notification) {
        this.notification = notification;
    }

    /**
     * Gets the list of likes made by the user.
     * 
     * @return the list of likes.
     */
    public List<Likes> getLikes() {
        return likes;
    }

    /**
     * Sets the list of likes made by the user.
     * 
     * @param likes the likes to set.
     */
    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    /**
     * Gets the list of messages sent by the user.
     * 
     * @return the list of sent messages.
     */
    public List<Messages> getSentmsg() {
        return sentmsg;
    }

    /**
     * Sets the list of messages sent by the user.
     * 
     * @param sentmsg the sent messages to set.
     */
    public void setSentmsg(List<Messages> sentmsg) {
        this.sentmsg = sentmsg;
    }

    /**
     * Gets the list of messages received by the user.
     * 
     * @return the list of received messages.
     */
    public List<Messages> getReceivedmsg() {
        return receivedmsg;
    }

    /**
     * Sets the list of messages received by the user.
     * 
     * @param receivedmsg the received messages to set.
     */
    public void setReceivedmsg(List<Messages> receivedmsg) {
        this.receivedmsg = receivedmsg;
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
     * Sets the unique user ID.
     * 
     * @param userID the user ID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
