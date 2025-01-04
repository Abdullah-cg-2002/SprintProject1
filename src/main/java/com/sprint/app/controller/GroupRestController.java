package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.dto.GroupDTO;
import com.sprint.app.exception.GroupException;
import com.sprint.app.model.Groups;
import com.sprint.app.services.GroupsService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

/**
 * REST controller for managing groups in the application.
 */
@RestController
@RequestMapping("/api")

public class GroupRestController {

    @Autowired
    private GroupsService gs;  // Using field injection

    private static final Logger logger = LoggerFactory.getLogger(GroupRestController.class);

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

    
}
