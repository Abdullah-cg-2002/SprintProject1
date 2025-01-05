package com.sprint.app.services;

import java.util.*;

import com.sprint.app.model.Friends;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;

public interface GroupService {
	//kiki
	/**
	 * Retrieves all groups a user is a member of.
	 *
	 * @param userID the unique identifier of the user. Must be a positive integer.
	 * @return a list of Groups that the user is a member of.
	 * @throws RuntimeException if no groups are found for the user.
	 */
	List<Groups> getUserGroup (int userID);  //Retrieve all groups a user is a member of.
	/**
	 * Retrieves all messages in a specific group.
	 *
	 * @param groupID the unique identifier of the group. Must be a positive integer.
	 * @return a list of Messages in the specified group.
	 * @throws RuntimeException if the group does not exist.
	 */
	List<Messages> getAllMessagesInGroup (int groupID);   //Retrieve all messages in a group
	/**
	 * Retrieves all friends who are members of a specific group.
	 *
	 * @param groupID the unique identifier of the group. Must be a positive integer.
	 * @return a list of Users who are friends and members of the specified group.
	 * @throws RuntimeException if no friends are found in the group.
	 */
	List<Users> getFriendsOfUser (int groupID);   //Retrieve all friends who are members of a specific group
	/**
	 * Retrieves all groups where a user's friends are members.
	 *
	 * @param userID the unique identifier of the user. Must be a positive integer.
	 * @return a list of Groups that the user's friends belong to.
	 */
	List<Groups> findGroupsofFrnds(int userID) ;//Retrieve all groups where a user's friends are members 
	/**
	 * Retrieves all members of a specific group.
	 *
	 * @param groupID the unique identifier of the group. Must be a positive integer.
	 * @return a Users object representing all members of the specified group.
	 * @throws RuntimeException if no members are found for the group.
	 */
	Users getAllMembersOfGroup (int groupID); //Retrieve all members of a specific group
	/**
	 * Removes a user from a specific group.
	 *
	 * @param groupID the unique identifier of the group. Must be a positive integer.
	 * @param userID  the unique identifier of the user to be removed. Must be a positive integer.
	 * @return a message indicating the result of the removal operation.
	 * @throws RuntimeException if the group does not exist or if the user is not the admin of the group.
	 */
	String removeUserFromGroup(int groupID,int userID); //remove user
	/**
	 * Removes a group by its ID.
	 *
	 * @param groupID the unique identifier of the group to be removed. Must be a positive integer.
	 * @return a message indicating the result of the removal operation.
	 * @throws RuntimeException if the group does not exist.
	 */
	String removeGroupById(int groupID); //remove grp by id
	/**
	 * Removes a user from a specific group.
	 *
	 * @param groupID the unique identifier of the group. Must be a positive integer.
	 * @param userID  the unique identifier of the user to be removed. Must be a positive integer.
	 * @return a message indicating the result of the removal operation.
	 * @throws RuntimeException if the group does not exist or if the user is not the admin of the group.
	 */
	String removeUserFromAGroup(int groupID, int userID);
	
	/**
	 * Adds a member to a specific group.
	 *
	 * @param groupID the unique identifier of the group. Must be a positive integer.
	 * @param userID  the unique identifier of the user to be added. Must be a positive integer.
	 * @return a message indicating the result of the addition operation.
	 * @throws RuntimeException if the user does not exist or if the group does not exist.
	 */
	String addMember(int groupID,int userID); //add member
	/**
	 * Sends a message to a specific group.
	 *
	 * @param groupID the unique identifier of the group. Must be a positive integer.
	 * @param userID  the unique identifier of the user sending the message. Must be a positive integer.
	 * @param message  the Messages object containing the message details. Must not be null.
	 * @return the Messages object that was sent.
	 * @throws RuntimeException if the group does not exist or if the user is not a member of the group.
	 */
	Messages sendMessageToGroup(int groupID, int userID, Messages message);
	/**
	 * Adds a user as a member to a specific group.
	 *
	 * @param groupID the unique identifier of the group. Must be a positive integer.
	 * @param userID  the unique identifier of the user to be added. Must be a positive integer.
	 * @return a message indicating the result of the addition operation.
	 * @throws RuntimeException if the user does not exist or if the group does not exist.
	 */
	String addUserAsMember(int groupID,int userID);
	List<Groups> getAllGroups();

}
