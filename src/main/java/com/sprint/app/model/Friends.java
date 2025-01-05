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

@Entity
public class Friends {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int friendshipID;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name="userID1",referencedColumnName = "userID")
	@JsonIgnore
	private Users user1;
	
	@ManyToOne
	@JoinColumn(name="userID2",referencedColumnName = "userID")
	@JsonIgnore
	private Users user2;

	public int getFriendshipID() {
		return friendshipID;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Users getUser1() {
		return user1;
	}

	public void setUser1(Users user1) {
		this.user1 = user1;
	}

	public Users getUser2() {
		return user2;
	}

	public void setUser2(Users user2) {
		this.user2 = user2;
	}
	public void setFriendshipID(int friendshipID) {
		this.friendshipID=friendshipID;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else
        {
        	Friends friend = (Friends) o;
            return ((user1.getUserID() == friend.getUser1().getUserID() && user2.getUserID() == friend.getUser2().getUserID()));
        }
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(user1.getUserID(),user2.getUserID());
	}

}
