package com.sprint.app.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.model.Friends;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.services.GroupService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Controller for managing group-related operations.
 * <p>
 * This controller provides endpoints for retrieving user groups, managing group members,
 * sending messages within groups, and other group-related functionalities.
 * </p>
 */
@RestController
@RequestMapping("/api/")
@Validated 
public class GroupRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupRestController.class);
	@Autowired
	private GroupService gs;

    /**
     * Retrieves a list of all groups.
     * 
     * @return SuccessResponseGet A response object containing the list of all groups and a success status.
     */
    @GetMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponseGet getAllGroupsData() {
        logger.info("Request to fetch all groups received.");

        SuccessResponseGet srg = new SuccessResponseGet();  
        List<Object> groupsList = gs.getAllGroupsData();  
        srg.setStatus("Success"); 
        srg.setData(groupsList); 
        
        logger.info("Returning response with status: Success and {} groups.", groupsList.size());
        return srg; 
    }

    /**
     * Retrieves a group by its ID.
     * 
     * @param groupId The ID of the group to retrieve.
     * @return SuccessResponseGet A response object containing the group data and a success status.
     * @throws GroupsIdNotFoundException if the group is not found.
     */
    @GetMapping("/groups/{groupId}")
    public SuccessResponseGet getGroupDataById(@PathVariable("groupId") int groupId) throws GroupException {
        // Log the incoming request with the group ID
		logger.info("Request to fetch group with ID: {}", groupId);

		// Try to fetch the group data from the service
		Groups group = gs.getGroupDataByID(groupId);

	
		// Prepare the response with the group data
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> groupList = new ArrayList<>();
		groupList.add(group); 
		srg.setStatus("Success");
		srg.setData(groupList);

		// Log the successful response
		logger.info("Returning response with status: Success for group with ID: {}", groupId);
		
		return srg;  // Return the successful response
    }

    /**
     * Creates a new group.
     * 
     * @param gdto The DTO containing the group details such as group name and admin.
     * @return SuccessResponse A response object containing a success message.
     */
    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse create(@Valid @RequestBody Groups group) {
        logger.info("Request to create a new group with name: {}", group.getGroupName());

        SuccessResponse srg = new SuccessResponse();  
        gs.createNewGroup(group);  

        srg.setStatus("Success");  
        srg.setMessage("Group created successfully");  

        logger.info("Group with name '{}' created successfully.", group.getGroupName());
        return srg; 
    }

    /**
     * Updates the name of an existing group by its ID.
     * 
     * @param gdto The DTO containing the new group name.
     * @param groupId The ID of the group to be updated.
     * @return SuccessResponse A response object containing a success message.
     */
    @PutMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse updateGroupName(
            @Valid @RequestBody GroupDTO gdto, 
            @PathVariable("groupId") @Positive(message = "Group ID must be positive") int groupId) {
        logger.info("Request to update group with ID: {} with new name: {}", groupId, gdto.getGroupName());

        SuccessResponse srg = new SuccessResponse();  
        gs.updateGroupName(gdto.getGroupName(), groupId); 
        srg.setStatus("Success"); 
        srg.setMessage("Group updated successfully");

        logger.info("Group with ID: {} updated successfully to new name: {}", groupId, gdto.getGroupName());
        return srg; 
    }
	
	/**
     * Retrieves the list of groups associated with a specific user.
     *
     * @param userID the unique identifier of the user. Must be a positive integer.
     * @return a list of Groups associated with the specified user.
     */
	@GetMapping("users/{userID}/groups")
	public List<Groups> getUserGroup (@PathVariable @Min(1) int userID){
		//return gs.getUserGroup(userID);
		 logger.info("Retrieving groups for user with ID: {}", userID);
	        List<Groups> groups = gs.getUserGroup(userID);
	        logger.info("Found {} groups for user with ID: {}", groups.size(), userID);
	        return groups;
	}
	
	/**
     * Retrieves all messages in a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @return a SuccessResponseGet object containing the status and list of messages in the group.
     */
	@GetMapping("groups/{groupID}/messages")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponseGet getAllMessagesInGroup (@PathVariable @Min(1) int groupID){
		
		logger.info("Retrieving all messages for group with ID: {}", groupID);
		SuccessResponseGet response = new SuccessResponseGet();
        response.setStatus("success");
       
        List<Messages> messages = gs.getAllMessagesInGroup(groupID);
        response.setData(new ArrayList<>(messages));
        logger.info("Retrieved {} messages for group with ID: {}", messages.size(), groupID);
        return response;
		
	}
	
	/**
     * Retrieves the friends of a user in a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @return a list of Users who are friends of the user in the specified group.
     */
	@GetMapping("groups/{groupID}/friends")
	public List<Users> getFriendsOfUser (@PathVariable @Min(1) int groupID){
		//return gs.getFriendsOfUser(groupID);
		logger.info("Retrieving friends for user in group with ID: {}", groupID);
        List<Users> friends = gs.getFriendsOfUser(groupID);
        logger.info("Found {} friends in group with ID: {}", friends.size(), groupID);
        return friends;
	}
	
	/**
     * Retrieves all members of a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @return a Users object representing all members of the specified group.
     */
	//getallmembers
	@GetMapping("group/{groupID}/members")
	@ResponseStatus(HttpStatus.OK)
	public Users getAllMembersOfGroup(@PathVariable @Min(1)int groupID) {
		logger.info("Retriving");
		Users u =gs.getAllMembersOfGroup(groupID);
		logger.info("Successfully retrived: {}", groupID );
		return u;
	}
	
	/**
     * Finds groups of friends for a specific user.
     *
     * @param userID the unique identifier of the user. Must be a positive integer.
     * @return a list of Groups that the user's friends belong to.
     */
	@GetMapping("users/{userID}/friends/groups")
	@ResponseStatus(HttpStatus.OK)
	public List<Groups> findGroupsofFrnds (@PathVariable @Min(1) int userID){
		//return gs.findGroupsofFrnds(userID);
		logger.info("Retrieving groups for friends of user with ID: {}", userID);
        List<Groups> groups = gs.findGroupsofFrnds(userID);
        logger.info("Found {} groups for friends of user with ID: {}", groups.size(), userID);
        return groups;
	}
	
	 /**
     * Removes a user from a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @param userID  the unique identifier of the user to be removed. Must be a positive integer.
     * @return a SuccessResponse object containing the status and message of the removal operation.
     */
	@DeleteMapping("groups/{groupID}/leave/{userID}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public SuccessResponse removeUserFromGroup(@PathVariable("groupID") @Min(1) int groupID,@PathVariable("userID")@Min(1) int userID){
		logger.info("Removing user with ID: {} from group with ID: {}", userID, groupID);
        String message = gs.removeUserFromGroup(groupID, userID);
        SuccessResponse sr = new SuccessResponse();
        sr.setStatus("success");
        sr.setMessage(message);
        logger.info("User with ID: {} successfully removed from group with ID: {}", userID, groupID);
        return sr;
	}
	
	/**
     * Removes a group by its ID.
     *
     * @param groupID the unique identifier of the group to be removed. Must be a positive integer.
     * @return a SuccessResponse object containing the status and message of the removal operation.
     */
	@DeleteMapping("groups/{groupID}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public SuccessResponse removeGroupById(@PathVariable @Min(1)int groupID) {
//		//gs.removeGroupById(groupID);
//		String message = gs.removeGroupById(groupID);
//		SuccessResponse sr = new SuccessResponse();
//		sr.setStatus("sucess");
//		sr.setMessage(message);
//		
//		return sr;
		
		logger.info("Removing group with ID: {}", groupID);
        String message = gs.removeGroupById(groupID);
        SuccessResponse sr = new SuccessResponse();
        sr.setStatus("success");
        sr.setMessage(message);
        logger.info("Group with ID: {} successfully removed", groupID);
        return sr;
	}
	
	/**
     * Removes a specific user from a group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @param userID  the unique identifier of the user to be removed. Must be a positive integer.
     * @return a SuccessResponse object containing the status and message of the removal operation.
     */
	@DeleteMapping("groups/{groupID}/members/remove/{userID}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public SuccessResponse removeUserFromAGroup(@PathVariable("groupID") @Min(1) int groupID,@PathVariable("userID") @Min(1) int userID){
//		String message = gs.removeUserFromAGroup(groupID, userID);
//		SuccessResponse sr = new SuccessResponse();
//		sr.setStatus("sucess");
//		sr.setMessage(message);
//		return sr;
		
		logger.info("Removing user with ID: {} from group with ID: {}", userID, groupID);
        String message = gs.removeUserFromAGroup(groupID, userID);
        SuccessResponse sr = new SuccessResponse();
        sr.setStatus("success");
        sr.setMessage(message);
        logger.info("User with ID: {} successfully removed from group with ID: {}", userID, groupID);
        return sr;
		
	}
	
	 /**
     * Adds a user as a member of a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @param userID  the unique identifier of the user to be added. Must be a positive integer.
     * @return a SuccessResponse object containing the status and message of the addition operation.
     */
	@PostMapping("groups/{groupID}/join/{userID}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public SuccessResponse addUserAsMember(@PathVariable @Min(1) int groupID,@PathVariable @Min(1) int userID) {
		 logger.info("Adding user with ID: {} as member of group with ID: {}", userID, groupID);
	        String message = gs.addUserAsMember(groupID, userID);
	        SuccessResponse sr = new SuccessResponse();
	        sr.setStatus("success");
	        sr.setMessage(message);
	        logger.info("User with ID: {} successfully added as member of group with ID: {}", userID, groupID);
	        return sr;
	}
	
	/**
     * Sends a message to a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @param userID  the unique identifier of the user sending the message. Must be a positive integer.
     * @param message  the message object containing the content to be sent. Must be valid according to the defined constraints.
     * @return the Messages object representing the sent message.
     */
	@PostMapping("groups/{groupID}/messages/send/{userID}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Messages sendMessageToGroup(@PathVariable @Min(1) int groupID,@PathVariable @Min(1) int userID,@RequestBody @Valid Messages message) {
		//return gs.sendMessageToGroup(groupID, userID, message);
		logger.info("Sending message to group with ID: {} from user with ID: {}", groupID, userID);
        Messages sentMessage = gs.sendMessageToGroup(groupID, userID, message);
        logger.info("Message successfully sent to group with ID: {} from user with ID: {}", groupID, userID);
        return sentMessage;
		
	}
	
	 /**
     * Adds a member to a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @param userID  the unique identifier of the user to be added. Must be a positive integer.
     * @return a SuccessResponse object containing the status and message of the addition operation.
     */
	@PostMapping("groups/{groupID}/members/add/{userID}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public SuccessResponse addMember(@PathVariable @Min(1) int groupID,@PathVariable @Min(1) int userID) {
//		String message = gs.addMember(groupID,userID);
//		SuccessResponse sr = new SuccessResponse();
//		sr.setStatus("sucess");
//		sr.setMessage(message);
//		
//		return sr;
		logger.info("Adding member with ID: {} to group with ID: {}", userID, groupID);
        String message = gs.addMember(groupID, userID);
        SuccessResponse sr = new SuccessResponse();
        sr.setStatus("success");
        sr.setMessage(message);
        logger.info("Member with ID: {} successfully added to group with ID: {}", userID, groupID);
        return sr;

	}
	
	@GetMapping("groups/getAll")
    public List<Groups> getAllGroups() {
        return gs.getAllGroups();
    }

	
}
