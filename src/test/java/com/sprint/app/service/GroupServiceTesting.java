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