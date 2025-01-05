package com.sprint.app.controller;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.sprint.app.controller.GroupRestController;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.services.GroupService;
import com.sprint.app.success.SuccessResponseGet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTesting {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupRestController groupController;

    @Autowired
    private MockMvc mockMvc;
    
    private Users users;
    private Groups group;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    void testGetAllGroupsData() throws Exception {
        // Create some mock data for groups
        Users admin1 = new Users();
        Groups group1 = new Groups();
        group1.setGroupID(1);
        group1.setGroupName("Group 1");
        group1.setAdmin(admin1);

        Users admin2 = new Users();
        Groups group2 = new Groups();
        group2.setGroupID(2);
        group2.setGroupName("Group 2");
        group2.setAdmin(admin2);

        List<Object> groupsList = Arrays.asList(group1, group2);
        when(groupsService.getAllGroupsData()).thenReturn(groupsList);  // Mock service to return a list of groups

        // Perform the GET request
        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())  // Check if status is OK
                .andExpect(jsonPath("$.status").value("Success"))  // Check status is "Success"
                .andExpect(jsonPath("$.data.length()").value(2));  // Check the number of groups returned

        verify(groupsService).getAllGroupsData();  // Verify that the service method was called
    }

    @Test
    void testGetGroupDataById() throws Exception {
        // Create mock group data
        Users admin = new Users();
        Groups group = new Groups();
        group.setGroupID(1);
        group.setGroupName("Group 1");
        group.setAdmin(admin);

        when(groupsService.getGroupDataByID(1)).thenReturn(group);  // Mock service to return the group

        // Perform the GET request to fetch group by ID
        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isOk())  // Check if status is OK
                .andExpect(jsonPath("$.status").value("Success"))  // Check status is "Success"
                .andExpect(jsonPath("$.data[0].groupName").value("Group 1"));  // Check group name

        verify(groupsService).getGroupDataByID(1);  // Verify that the service method was called
    }

    @Test
    void testCreateGroup() throws Exception {
        // Create GroupDTO for the POST request
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupName("New Group created");
        groupDTO.setAdmin(new Users());  // Assuming admin is required

        // Mock the service to return a newly created group
        Groups createdGroup = new Groups();
        createdGroup.setGroupID(1);
        createdGroup.setGroupName("New Group created");
        createdGroup.setAdmin(new Users());  // Set some admin

        doNothing().when(groupsService).createNewGroup(Mockito.any(Groups.class));

        // Perform the POST request to create a group
        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupDTO)))  // Send group data
                .andExpect(status().isCreated())  // Expect created status
                .andExpect(jsonPath("$.status").value("Success"))  // Check status is "Success"
                .andExpect(jsonPath("$.message").value("Group created successfully"));  // Check success message

    }

    @Test
    void testUpdateGroupName() throws Exception {
        // Create GroupDTO for the PUT request
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupName("2nd final");

        // Create the updated group
        Groups updatedGroup = new Groups();
        updatedGroup.setGroupID(1065);
        updatedGroup.setGroupName("Updated Group 1");
        updatedGroup.setAdmin(new Users());  // Assuming admin exists

        when(groupsService.updateGroupName(Mockito.anyString(), Mockito.anyInt())).thenReturn(updatedGroup);  // Mock update to return the updated group

        // Perform the PUT request to update group name
        mockMvc.perform(put("/api/groups/1065")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupDTO)))  // Send updated group data
                .andExpect(status().isOk())  // Expect OK status
                .andExpect(jsonPath("$.status").value("Success"))  // Check status is "Success"
                .andExpect(jsonPath("$.message").value("Group updated successfully"));  // Check success message

    }

    // Test for /users/{userID}/groups
    @Test
    void testGetUserGroup() throws Exception {
        int userID = 1;
        Groups group1 = new Groups();
        group1.setGroupID(1);
        group1.setGroupName("Group 1");

        Groups group2 = new Groups();
        group2.setGroupID(2);
        group2.setGroupName("Group 2");

        List<Groups> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        when(groupService.getUserGroup(userID)).thenReturn(groups);

        mockMvc.perform(get("/api/users/{userID}/groups", userID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].groupName").value("Group 1"))
                .andExpect(jsonPath("$[1].groupName").value("Group 2"))
                .andDo(print());

        verify(groupService, times(1)).getUserGroup(userID);
    }

    @Test
    public void testGetAllMessagesInGroup_Success() {
        // Arrange
        int groupID = 1;
        List<Messages> messages = new ArrayList<>();
        Messages message = new Messages();
        messages.add(message);

        when(groupService.getAllMessagesInGroup(groupID)).thenReturn(messages);

        // Act
        SuccessResponseGet response = groupController.getAllMessagesInGroup(groupID);

        // Assert
        assertEquals("success", response.getStatus());
        assertEquals(messages, response.getData());
        assertEquals("success", response.getStatus());
        verify(groupService, times(1)).getAllMessagesInGroup(groupID);
    }

    
   

//    // Test for /groups/{groupID}/friends
//    @Test
//    void testGetFriendsOfUser() throws Exception {
////        int groupID = 1;
////        Users user1 = new Users();
////        user1.setUserID(1);
////        user1.setUsername("User1");
////
////        Users user2 = new Users();
////        user2.setUserID(2);
////        user2.setUsername("User2");
//
//        List<Users> friends = new ArrayList<>();
////        friends.add(user1);
////        friends.add(user2);
//
//        when(groupService.getFriendsOfUser(1)).thenReturn(friends);
//
////        mockMvc.perform(get("/api/groups/1/friends")
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.length()").value(2))
////                .andExpect(jsonPath("$[0].username").value("User1"))
////                .andExpect(jsonPath("$[1].username").value("User2"))
////                .andDo(print());
//        
//        SuccessResponseGet response = groupController.getFriendsOfUser(1);
//
//        // Assert
//        assertEquals("success", response.getStatus());
//        assertEquals(0, response.getData().size());
//        assertEquals("success", response.getStatus());
//        verify(groupService, times(1)).getFriendsOfUser(1);
//    }

    // Test for /groups/{groupID}/leave/{userID}
    @Test
    void testRemoveUserFromGroup() throws Exception {
        int groupID = 1;
        int userID = 1;
        String successMessage = "User removed successfully";

        when(groupService.removeUserFromGroup(groupID, userID)).thenReturn(successMessage);

        mockMvc.perform(delete("/api/groups/{groupID}/leave/{userID}", groupID, userID))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value(successMessage))
                .andDo(print());

        verify(groupService, times(1)).removeUserFromGroup(groupID, userID);
    }

    // Test for /groups/{groupID}/join/{userID}
    @Test
    void testAddUserAsMember() throws Exception {
        int groupID = 1;
        int userID = 1;
        String successMessage = "User added successfully";

        when(groupService.addUserAsMember(groupID, userID)).thenReturn(successMessage);

        mockMvc.perform(post("/api/groups/{groupID}/join/{userID}", groupID, userID))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value(successMessage))
                .andDo(print());

        verify(groupService, times(1)).addUserAsMember(groupID, userID);
    }

    // Test for /groups/{groupID}/members/remove/{userID}
    @Test
    void testRemoveUserFromAGroup() throws Exception {
        int groupID = 1;
        int userID = 1;
        String successMessage = "User removed from group";

        when(groupService.removeUserFromAGroup(groupID, userID)).thenReturn(successMessage);

        mockMvc.perform(delete("/api/groups/{groupID}/members/remove/{userID}", groupID, userID))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value(successMessage))
                .andDo(print());

        verify(groupService, times(1)).removeUserFromAGroup(groupID, userID);
    }

    // Test for /groups/{groupID}
    @Test
    void testRemoveGroupById() throws Exception {
        int groupID = 1;
        String successMessage = "Group removed successfully";

        when(groupService.removeGroupById(groupID)).thenReturn(successMessage);

        mockMvc.perform(delete("/api/groups/{groupID}", groupID))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value(successMessage))
                .andDo(print());

        verify(groupService, times(1)).removeGroupById(groupID);
    }

    // Test for /groups/{groupID}/members/add/{userID}
    @Test
    void testAddMember() throws Exception {
        int groupID = 1;
        int userID = 1;
        String successMessage = "Member added successfully";

        when(groupService.addMember(groupID, userID)).thenReturn(successMessage);

        mockMvc.perform(post("/api/groups/{groupID}/members/add/{userID}", groupID, userID))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value(successMessage))
                .andDo(print());

        verify(groupService, times(1)).addMember(groupID, userID);
    }

    // Test for /groups/{groupID}/messages/send/{userID}
    @Test
    void testSendMessageToGroup() throws Exception {
        int groupID = 1;
        int userID = 1;

        // Create a sample message to mock
        Messages message = new Messages();
        message.setMessage_text("Test message");

        // Stub the service method with any message object
        when(groupService.sendMessageToGroup(eq(groupID), eq(userID), any(Messages.class)))
            .thenReturn(message);

        // Perform the POST request and check the response
        mockMvc.perform(post("/api/groups/{groupID}/messages/send/{userID}", groupID, userID)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"message_text\": \"Test message\" }"))
                .andExpect(status().isAccepted())  // Expected status is 202 for accepted
                .andExpect(jsonPath("$.message_text").value("Test message"))
                .andDo(print());

        // Verify that the sendMessageToGroup method was called
        verify(groupService, times(1)).sendMessageToGroup(eq(groupID), eq(userID), any(Messages.class));
    }
    
   //getallmembers
    @Test
    void testGetAllMembersOfGroup() throws Exception{
    	Users user = new Users();
    	//user.setUserID(1);
    	user.setUsername("kiritika");
    	
    	when(groupService.getAllMembersOfGroup(1)).thenReturn(user);
    	
    	mockMvc.perform(get("/api/group/1/members")
    			.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
    	
    	verify(groupService, times(1)).getAllMembersOfGroup(1);
    }
    
    @Test
    void testFindGroupsofFrnds() throws Exception {
        // Setup mock behavior
        Groups group1 = new Groups();
        group1.setGroupID(1);
        group1.setGroupName("Group 1");
        Groups group2 = new Groups();
        group2.setGroupID(2);
        group2.setGroupName("Group 2");

        List<Groups> groups = Arrays.asList(group1, group2);
        when(groupService.findGroupsofFrnds(1)).thenReturn(groups);

        // Perform the GET request and assert the response
        mockMvc.perform(get("/api/users/1/friends/groups")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupID").value(1))
                .andExpect(jsonPath("$[0].groupName").value("Group 1"))
                .andExpect(jsonPath("$[1].groupID").value(2))
                .andExpect(jsonPath("$[1].groupName").value("Group 2"));

        // Verify service interaction
        verify(groupService, times(1)).findGroupsofFrnds(1);
    }

}
