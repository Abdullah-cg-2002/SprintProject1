
package com.sprint.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.exception.*;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.repo.GroupRepo;
import com.sprint.app.repo.MessageRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.GroupService;


/**
 * Implementation of the GroupService interface.
 * <p>
 * This class provides methods for managing groups, including retrieving user groups,
 * sending messages, adding/removing members, and managing group notifications.
 * </p>
 */
@Service
public class GroupServiceImpl implements GroupService{
	private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
	
	@Autowired
	private GroupRepo gr;
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private MessageRepo mr;

	
	 /**
     * Retrieves the list of groups associated with a specific user.
     *
     * @param userID the unique identifier of the user. Must be a positive integer.
     * @return a list of Groups associated with the specified user.
     * @throws RuntimeException if no groups are found for the user.
     */
	@Override
	public List<Groups> getUserGroup(int userID) {
		logger.info("Fetching groups for userID: {}", userID);
        Users u = ur.findById(userID).orElseThrow(() -> {
            logger.error("User with ID {} not found", userID);
            return new GroupException("User not found");
        });
        List<Groups> groups = u.getGroups();
        if(groups.isEmpty()) {
            logger.error("No groups found for user {}", userID);
            throw new GroupException("No groups found for user " + userID);
        }
        logger.info("Groups retrieved successfully for userID: {}", userID);
        return groups;
	}
	
	/**
     * Retrieves all messages in a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @return a list of Messages in the specified group.
     * @throws RuntimeException if the group does not exist.
     */
	@Override
	public List<Messages> getAllMessagesInGroup(int groupID) {
		logger.info("Fetching all messages for groupID: {}", groupID);
        Groups g = gr.findById(groupID).orElseThrow(() -> {
            logger.error("Group with ID {} does not exist", groupID);
            return new GroupException("Group does not exist");
        });
        List<Messages> msg = g.getAdmin().getSentmsg();
        msg.addAll(g.getAdmin().getReceivedmsg());
        logger.info("Messages retrieved successfully for groupID: {}", groupID);
        return msg;
	}

	 /**
     * Retrieves the friends of users in a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @return a list of Users who are friends of the group members.
     * @throws RuntimeException if no friends are found for the group.
     */
	@Override
	public List<Users> getFriendsOfUser(int groupID) {

		logger.info("Fetching friends for groupID: {}", groupID);
        List<Users> friends = gr.findFriendsOfGroupMembers(groupID);
        if (friends.isEmpty()) {
            logger.error("No friends found for groupID: {}", groupID);
            throw new RuntimeException("Friends are not found with GroupID: " + groupID);
        }
        logger.info("Friends retrieved successfully for groupID: {}", groupID);
        return friends;
	}

	/**
     * Finds groups of friends for a specific user.
     *
     * @param userID the unique identifier of the user. Must be a positive integer.
     * @return a list of Groups that the user's friends belong to.
     */
	@Override
	public List<Groups> findGroupsofFrnds(int userID) {
		logger.info("Fetching groups of friends for userID: {}", userID);
        List<Groups> grps = new ArrayList<>();
        Users usr = ur.findById(userID).orElseThrow(() -> {
            logger.error("User with ID {} not found", userID);
            return new GroupException("User not found");
        });

        for(Friends frds : usr.getFriendsent()) {
            grps.addAll(frds.getUser2().getGroups());
        }

        for(Friends frds : usr.getFriendsrec()) {
            grps.addAll(frds.getUser1().getGroups());
        }
        logger.info("Groups of friends retrieved successfully for userID: {}", userID);
        return grps;

	}
		
	/**
     * Retrieves all members of a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @return a Users object representing all members of the specified group.
     * @throws RuntimeException if no users are found for the group.
     */
	//getallmembers
	@Override
	public Users getAllMembersOfGroup(int groupID) {
		logger.info("Fetching the data");
		Users user = gr.findUsersByGroupID(groupID);
		if(user==null) {
			logger.error("User not found");
			throw new GroupException("User is not found in the group "+groupID);
		}
		logger.info(null);
		return user;
	}
	

	/**
     * Removes a user from a specific group.
     *
     * @param groupID the unique identifier of the group. * @param userID the unique identifier of the user to be removed. Must be a positive integer.
     * @return a message indicating the result of the removal operation.
     * @throws RuntimeException if the group does not exist or the user is not the admin of the group.
     */
	@Override
	public String removeUserFromGroup(int groupID, int userID) {
		 logger.info("Removing userID: {} from groupID: {}", userID, groupID);
	        Groups group = gr.findById(groupID).orElseThrow(() -> {
	            logger.error("Group with ID {} does not exist", groupID);
	            return new GroupException("No group with ID " + groupID + " exists");
	        });
	        if(group.getAdmin().getUserID() != userID) {
	            logger.error("UserID: {} is not the admin of groupID: {}", userID, groupID);
	            throw new GroupException("User is not the admin of the group");
	        }
	        gr.deleteById(groupID);
	        logger.info("User successfully removed from groupID: {}", groupID);
	        return "User successfully removed from the group";
	}

	/**
     * Adds a member to a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @param userID the unique identifier of the user to be added. Must be a positive integer.
     * @return a message indicating the result of the addition operation.
     * @throws RuntimeException if the user does not exist or the group does not exist.
     */
	@Override
	public String addMember(int groupID, int userID) {
		logger.info("Adding userID: {} as a member to groupID: {}", userID, groupID);
        Users user = ur.findById(userID).orElseThrow(() -> {
            logger.error("User with ID {} not found", userID);
            return new GroupException("User not found");
        });

        Groups grp = new Groups();
        grp.setGroupID(groupID);
        grp.setGroupName(gr.findById(groupID).get().getGroupName());
        grp.setAdmin(user);
        
        gr.save(grp);
        logger.info("UserID: {} added as member to groupID: {}", userID, groupID);
        return "User added successfully";
	}

	/**
     * Removes a group by its ID.
     *
     * @param groupID the unique identifier of the group to be removed. Must be a positive integer.
     * @return a message indicating the result of the removal operation.
     * @throws RuntimeException if the group does not exist.
     */
	@Override
	public String removeGroupById(int groupID) {

		logger.info("Attempting to remove group with groupID: {}", groupID);
	    Groups g = gr.findById(groupID).orElse(null); // gr.findById(groupID).get();
	    
	    if (g == null) {
	        logger.error("Group with groupID: {} not found", groupID);
	        throw new GroupException("Group not found for groupID: " + groupID);
	    }
	    
	    gr.deleteById(groupID);
	    logger.info("Group with groupID: {} deleted successfully", groupID);
	    return "Groups deleted successfully";
		
	}

	/**
	 * Removes a user from a specific group.
	 *
	 * @param groupID the unique identifier of the group from which the user is to be removed. Must be a positive integer.
	 * @param userID  the unique identifier of the user to be removed. Must be a positive integer.
	 * @return a message indicating the result of the removal operation.
	 * @throws RuntimeException if the group does not exist or if the user is not the admin of the group.
	 */
	@Override
	public String removeUserFromAGroup(int groupID, int userID) {
		logger.info("Attempting to remove userID: {} from groupID: {}", userID, groupID);
	    Groups group = gr.findById(groupID).orElse(null);
	    
	    if (group == null) {
	        logger.error("Group with groupID: {} does not exist", groupID);
	        throw new GroupException("No group with " + groupID + " exists in database");
	    }
	    
	    
	    if (group.getAdmin().getUserID() != userID) {
	        logger.error("User with userID: {} is not the admin of groupID: {}", userID, groupID);
	        throw new GroupException("No user with Id: " + userID + " found in group with Id: " + groupID);
	    }
	    
	    gr.deleteById(groupID);
	    logger.info("User with userID: {} successfully removed from groupID: {}", userID, groupID);
	    return "User successfully removed from the group";

	}

	/**
     * Sends a message to a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @param userID the unique identifier of the user sending the message. Must be a positive integer.
     * @param message the Messages object containing the message details. Must not be null.
     * @return the Messages object that was sent.
     * @throws RuntimeException if the group does not exist or the user is not the admin of the group.
     */
	@Override
	public Messages sendMessageToGroup(int groupID, int userID, Messages message) {
	    logger.info("Sending message to groupID: {} from userID: {}", groupID, userID);
	    Groups group = gr.findById(groupID).orElseThrow(() -> {
	        logger.error("Group with ID {} does not exist", groupID);
	        return new GroupException("No group with ID " + groupID + " exists");
	    });
	    Users user = ur.findById(userID).orElseThrow(() -> {
	        logger.error("User with ID {} not found", userID);
	        return new GroupException("User not found");
	    });
	    if (group.getAdmin().getUserID() != userID) {
	        logger.error("UserID: {} is not the admin of groupID: {}", userID, groupID);
	        throw new GroupException("No user with Id: " + userID + " found in groupID: " + groupID);
	    }

	    Messages msg = new Messages();
	    msg.setMessageID(message.getMessageID());
	    msg.setSender(user);
	    msg.setReceiver(group.getAdmin());
	    msg.setMessage_text(message.getMessage_text()); // Set the message text from the input message
	    msg.setTimestamp(LocalDateTime.now());
	    mr.save(msg);
	    logger.info("Message sent successfully to groupID: {} from userID: {}", groupID, userID);
	    return msg;
	}

	

	/**
	 * Adds a user as a member to a specific group.
	 *
	 * @param groupID the unique identifier of the group to which the user is to be added. Must be a positive integer.
	 * @param userID  the unique identifier of the user to be added. Must be a positive integer.
	 * @return a message indicating the result of the addition operation.
	 * @throws RuntimeException if the user does not exist or if the group does not exist.
	 */
	
	
		@Override
	public String addUserAsMember(int groupID, int userID) {
			logger.info("Adding userID: {} as a member to groupID: {}", userID, groupID);
	        Users user = ur.findById(userID).orElseThrow(() -> {
	            logger.error("User with ID {} not found", userID);
	            return new GroupException("User not found");
	        });

	        Groups grp = new Groups();
	        grp.setGroupID(groupID);
	        grp.setGroupName(gr.findById(groupID).get().getGroupName());
	        grp.setAdmin(user);
	        
	        gr.save(grp);
	        logger.info("UserID: {} added as member to groupID: {}", userID, groupID);
	        return "User added to a group successfully";

	}
		
		public List<Groups> getAllGroups() {
	        return gr.findAll();
	    }
		
	}


