package com.sprint.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.app.controller.GroupRestController;
import com.sprint.app.dto.GroupDTO;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Users;
import com.sprint.app.services.GroupsService;
import com.sprint.app.success.SuccessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTesting {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GroupsService groupsService;

    @InjectMocks
    private GroupRestController groupRestController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupRestController).build();
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
}
