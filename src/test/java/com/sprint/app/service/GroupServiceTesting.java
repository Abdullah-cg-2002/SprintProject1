package com.sprint.app.service;

import com.sprint.app.dto.GroupDTO;
import com.sprint.app.exception.GroupException;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Users;
import com.sprint.app.repo.GroupRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.GroupsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GroupServiceTesting {

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private UserRepo userRepo;
    
    private Groups group;
    
   
	private Users user;

	private Users admin;

   

    @Test
    void testCreateNewGroup_Success() {
        Groups g = new Groups();
       g.setGroupName("New Grp");
       g.setAdmin(admin);
        groupsService.createNewGroup(g);

        Groups createdGroup = groupRepo.findById(g.getGroupID()).orElse(null);

        assertNotNull(createdGroup);
        assertEquals("New Grp", createdGroup.getGroupName(), "The group name should match the provided name");
    }

    @Test
    void testGetGroupDataByID_Success() {
    	Groups g = new Groups();
    	g.setGroupID(1007);
    	g.setGroupName("Group 7");
        // Call the service to retrieve the group by ID
        Groups foundGroup = groupsService.getGroupDataByID(group.getGroupID());

        // Verify that the retrieved group is the same as the saved one
        assertNotNull(foundGroup, "Group should be found successfully");
        assertEquals(group.getGroupID(), foundGroup.getGroupID(), "The group ID should match");
        assertEquals(group.getGroupName(), foundGroup.getGroupName(), "The group name should match");
    }
    
    @Test
    void testUpdateGroupName_Success() {
    	Groups g = new Groups();
    	g.setGroupID(1008);
    	g.setGroupName("Group 8");
        String newGroupName = "Updated new Group Name";
        
        // Call the service to update the group name
        Groups updatedGroup = groupsService.updateGroupName(newGroupName, group.getGroupID());

        // Verify that the group name was updated successfully
        assertNotNull(updatedGroup, "Updated group should not be null");
        assertEquals(newGroupName, updatedGroup.getGroupName(), "The group name should be updated");
    }


    @BeforeEach
    void setUp() {
        // Set up a group for testing
        group = new Groups();
        group.setGroupID(1001);
        group.setGroupName("Test Group");
        group = groupRepo.save(group); 
    }
    
    @Test
    void testGetGroupDataByID_GroupNotFound() {
        // Try to retrieve a group that doesn't exist
        GroupException exception = assertThrows(GroupException.class, () -> {
            groupsService.getGroupDataByID(9999);  // Non-existent group ID
        });

        // Verify the exception message
        assertEquals("Group with id 9999 does not exist.", exception.getMessage());
    }

    
    @Test
    void testUpdateGroupName_GroupNotFound() {
        String newGroupName = "Updated Group Name";

        // Try to update a group that doesn't exist
        GroupException exception = assertThrows(GroupException.class, () -> {
            groupsService.updateGroupName(newGroupName, 9999);  // Non-existent group ID
        });

        // Verify the exception message
        assertEquals("Group with id 9999 does not exist.", exception.getMessage());
    }

    
}
