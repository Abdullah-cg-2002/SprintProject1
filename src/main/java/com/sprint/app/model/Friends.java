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
 * Represents a friendship relationship between two users.
 * This entity defines the structure of a friendship, including the friendship ID,
 * the status of the friendship, and the two users involved in the friendship.
 * It is mapped to a database entity using JPA annotations.
 */
@Entity
public class Friends {
    
    /**
     * The unique identifier for the friendship.
     * This is the primary key for the Friends entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int friendshipID;

    /**
     * The status of the friendship.
     * This is an enum that defines the state of the friendship (e.g., PENDING, ACCEPTED, REJECTED).
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * The first user in the friendship.
     * This is a many-to-one relationship with the Users entity.
     * The user is referenced by the userID field.
     */
    @ManyToOne
    @JoinColumn(name = "userID1", referencedColumnName = "userID")
    @JsonIgnore
    private Users user1;

    /**
     * The second user in the friendship.
     * This is a many-to-one relationship with the Users entity.
     * The user is referenced by the userID field.
     */
    @ManyToOne
    @JoinColumn(name = "userID2", referencedColumnName = "userID")
    @JsonIgnore
    private Users user2;

    /**
     * Gets the unique identifier of the friendship.
     *
     * @return the friendship ID.
     */
    public int getFriendshipID() {
        return friendshipID;
    }

    /**
     * Gets the status of the friendship.
     *
     * @return the status of the friendship.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the friendship.
     *
     * @param status the status to set.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the first user involved in the friendship.
     *
     * @return the first user.
     */
    public Users getUser1() {
        return user1;
    }

    /**
     * Sets the first user involved in the friendship.
     *
     * @param user1 the first user to set.
     */
    public void setUser1(Users user1) {
        this.user1 = user1;
    }

    /**
     * Gets the second user involved in the friendship.
     *
     * @return the second user.
     */
    public Users getUser2() {
        return user2;
    }

    /**
     * Sets the second user involved in the friendship.
     *
     * @param user2 the second user to set.
     */
    public void setUser2(Users user2) {
        this.user2 = user2;
    }

    /**
     * Compares this friendship object with another object for equality.
     * Two friendship objects are considered equal if they involve the same pair of users.
     * 
     * @param o the object to compare this friendship with.
     * @return true if the two friendships involve the same users, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else {
            Friends friend = (Friends) o;
            return (user1.getUserID() == friend.getUser1().getUserID() &&
                    user2.getUserID() == friend.getUser2().getUserID());
        }
    }

    /**
     * Generates a hash code for the friendship object.
     * The hash code is generated based on the user IDs of the two users involved in the friendship.
     * 
     * @return the hash code for the friendship object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(user1.getUserID(), user2.getUserID());
    }
}
