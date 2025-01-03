package com.sprint.app.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Entity class representing a Friendship between two users.
 */
@Entity
public class Friends {

    /**
     * Unique identifier for the friendship.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int friendshipID;

    /**
     * Status of the friendship.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * First user involved in the friendship.
     */
    @ManyToOne
    @JoinColumn(name = "userID1", referencedColumnName = "userID")
    @JsonIgnore
    private Users user1;

    /**
     * Second user involved in the friendship.
     */
    @ManyToOne
    @JoinColumn(name = "userID2", referencedColumnName = "userID")
    @JsonIgnore
    private Users user2;

    /**
     * Sets the friendship ID.
     * 
     * @param friendshipID The unique identifier for the friendship.
     */
    public void setFriendshipID(int friendshipID) {
        this.friendshipID = friendshipID;
    }

    /**
     * Gets the friendship ID.
     * 
     * @return The unique identifier for the friendship.
     */
    public int getFriendshipID() {
        return friendshipID;
    }

    /**
     * Gets the status of the friendship.
     * 
     * @return The status of the friendship.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the friendship.
     * 
     * @param status The status of the friendship.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the first user in the friendship.
     * 
     * @return The first user.
     */
    public Users getUser1() {
        return user1;
    }

    /**
     * Sets the first user in the friendship.
     * 
     * @param user1 The first user.
     */
    public void setUser1(Users user1) {
        this.user1 = user1;
    }

    /**
     * Gets the second user in the friendship.
     * 
     * @return The second user.
     */
    public Users getUser2() {
        return user2;
    }

    /**
     * Sets the second user in the friendship.
     * 
     * @param user2 The second user.
     */
    public void setUser2(Users user2) {
        this.user2 = user2;
    }

    /**
     * Checks if two friendship objects are equal based on their users.
     * 
     * @param o The object to compare with.
     * @return true if the friendships involve the same users; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friends)) return false;
        Friends friend = (Friends) o;
        return (user1.getUserID() == friend.getUser1().getUserID() &&
                user2.getUserID() == friend.getUser2().getUserID());
    }

    /**
     * Generates a hash code based on the user IDs.
     * 
     * @return Hash code for the friendship.
     */
    @Override
    public int hashCode() {
        return Objects.hash(user1.getUserID(), user2.getUserID());
    }
}
