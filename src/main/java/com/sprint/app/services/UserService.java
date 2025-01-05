package com.sprint.app.services;

import java.util.List;


import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.model.Groups;
import com.sprint.app.success.SuccessResponseGet;

public interface UserService
{
	void sendMsgFrnd(int userID, int frdID);
	void sendFrdReq(int userID, int frdID);
	List<Likes> getAllLikesPst(int userID);
	List<Messages> msgBtwUsers(int userID, int otherID);
	 List<Likes> getAllLikesUsr(int userID);
	 
	
	//update and notification by userID-kiki
	 /**
	  * Updates the information of a user.
	  *
	  * @param userID the unique identifier of the user to be updated. Must be a positive integer.
	  * @param user   the Users object containing updated information. Must not be null.
	  * @return a message indicating the result of the update operation.
	  * @throws RuntimeException if the email or username already exists for another user.
	  */
	String updateUser(int userID, Users user);
	
	/**
	 * Retrieves notifications for a specific user.
	 *
	 * @param userID the unique identifier of the user whose notifications are to be retrieved. Must be a positive integer.
	 * @return a SuccessResponseGet object containing the status and list of notifications for the specified user.
	 * @throws RuntimeException if no notifications are found for the given user ID.
	 */
	SuccessResponseGet getNotificationByUserID(int userID);
	Users getUserById(int userID);
	List<Users> getAllUsers();
	
}