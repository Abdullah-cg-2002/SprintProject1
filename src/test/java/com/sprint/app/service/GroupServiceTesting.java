package com.sprint.app.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.repo.GroupRepo;
import com.sprint.app.repo.MessageRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.GroupServiceImpl;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional // Rolls back each test to keep database state consistent
class GroupServiceTesting {

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private GroupServiceImpl groupService; // Autowire the service

    private Users user;
    private Groups group;
    private Messages message;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setUsername("testUser");
        user.setEmail("testUser@example.com");
        user.setPassword("password");
        userRepo.save(user);

        group = new Groups();
        group.setGroupName("testGroup");
        group.setAdmin(user);
        groupRepo.save(group);

        message = new Messages();
        message.setMessage_text("Hello");
        message.setTimestamp(LocalDateTime.now());
        message.setSender(user);
        message.setReceiver(user);
        messageRepo.save(message);
    }

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

    @Test
    void testGetUserGroup_NoGroups() {
        Users newUser = new Users();
        newUser.setUsername("newUser");
        newUser.setEmail("newUser@example.com");
        newUser.setPassword("password");
        userRepo.save(newUser);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupService.getUserGroup(newUser.getUserID());
        });

        assertEquals("No groups found for user " + newUser.getUserID(), exception.getMessage());
    }

    @Test
    void testGetAllMessagesInGroup_Success() {
        // Ensure the message is associated with the group
        group.getAdmin().getSentmsg().add(message);
        groupRepo.save(group);

        List<Messages> messages = groupService.getAllMessagesInGroup(group.getGroupID());
        assertFalse(messages.isEmpty());
        assertEquals("Hello", messages.get(0).getMessage_text());
    }


    @Test
    void testGetAllMessagesInGroup_NoGroup() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupService.getAllMessagesInGroup(999);
        });

        assertEquals("Group does not exist", exception.getMessage());
    }

    @Test
    void testGetFriendsOfUser_NoFriends() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupService.getFriendsOfUser(group.getGroupID());
        });

        assertEquals("Friends are not found with GroupID: " + group.getGroupID(), exception.getMessage());
    }

    @Test
    void testFindGroupsofFrnds_NoGroups() {
        List<Groups> groups = groupService.findGroupsofFrnds(user.getUserID());
        assertTrue(groups.isEmpty());
    }

    @Test
    void testGetAllMembersOfGroup_Success() {
        Users members = groupService.getAllMembersOfGroup(group.getGroupID());
        assertNotNull(members);
        assertEquals("testUser", members.getUsername());
    }

    @Test
    void testGetAllMembersOfGroup_NoUsers() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupService.getAllMembersOfGroup(999);
        });

        assertEquals("User is not found in the group 999", exception.getMessage());
    }

    @Test
    void testRemoveUserFromGroup_Success() {
        String result = groupService.removeUserFromGroup(group.getGroupID(), user.getUserID());
        assertEquals("User successfully removed from the group", result);
    }

    @Test
    void testRemoveUserFromGroup_NoGroup() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupService.removeUserFromGroup(999, user.getUserID());
        });

        assertEquals("No group with ID 999 exists", exception.getMessage());
    }

    @Test
    void testAddMember_Success() {
        Users newUser = new Users();
        newUser.setUsername("newUser");
        newUser.setEmail("newUser@example.com");
        newUser.setPassword("password");
        userRepo.save(newUser);

        String result = groupService.addMember(group.getGroupID(), newUser.getUserID());
        assertEquals("User added successfully", result);
    }

    @Test
    void testAddMember_NoUser() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupService.addMember(group.getGroupID(), 999);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testRemoveGroupById_Success() {
        String result = groupService.removeGroupById(group.getGroupID());
        assertEquals("Groups deleted successfully", result);
    }

    @Test
    void testRemoveGroupById_NoGroup() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupService.removeGroupById(999);
        });

        assertEquals("Group not found for groupID: 999", exception.getMessage());
    }

    @Test
    void testSendMessageToGroup_Success() {
        Messages sentMessage = groupService.sendMessageToGroup(group.getGroupID(), user.getUserID(), message);
        assertNotNull(sentMessage);
        assertEquals("Hello", sentMessage.getMessage_text());
    }

    @Test
    void testSendMessageToGroup_NoGroup() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupService.sendMessageToGroup(999, user.getUserID(), message);
        });

        assertEquals("No group with ID 999 exists", exception.getMessage());
    }

    @Test
    void testAddUserAsMember_Success() {
        Users newUser = new Users();
        newUser.setUsername("newUser");
        newUser.setEmail("newUser@example.com");
        newUser.setPassword("password");
        userRepo.save(newUser);

        String result = groupService.addUserAsMember(group.getGroupID(), newUser.getUserID());
        assertEquals("User added to a group successfully", result);
    }

    @Test
    void testAddUserAsMember_NoUser() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupService.addUserAsMember(group.getGroupID(), 999);
        });

        assertEquals("User not found", exception.getMessage());
    }
}