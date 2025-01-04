package com.sprint.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sprint.app.dto.GroupDTO;
import com.sprint.app.model.Groups;

/**
 * Service interface for managing groups in the system. 
 * It provides methods for creating, retrieving, updating, and listing groups.
 */

@Service
public interface GroupsService {

	/**
	 * Creates a new group based on the provided {@link GroupDTO} data.
	 * 
	 * @param gdto the {@link GroupDTO} object containing the data required to create a new group
	 */
	public void createNewGroup(Groups group);


	/**
	 * Retrieves the data of a group by its ID.
	 * 
	 * @param groupId the ID of the group to retrieve
	 * @return the {@link Groups} entity representing the group with the given ID
	 */
	Groups getGroupDataByID(int groupId);

	/**
	 * Retrieves a list of all groups.
	 * 
	 * @return a list of {@link Object} representing all groups in the system
	 */
	public List<Object> getAllGroupsData();

	/**
	 * Updates the name of an existing group based on the provided group ID and new group name.
	 * 
	 * @param groupName the new name to assign to the group
	 * @param groupId the ID of the group to update
	 * @return the updated {@link Groups} entity
	 */
	Groups updateGroupName(String groupName, int groupId);

}
