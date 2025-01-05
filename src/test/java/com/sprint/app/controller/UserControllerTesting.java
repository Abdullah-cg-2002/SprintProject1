package com.sprint.app.controller;



import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.app.controller.UserRestController;
import com.sprint.app.model.Users;
import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AutoConfigureMockMvc
class UserControllerTesting {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    void testUpdateUser() throws Exception {
        int userID = 1;
        Users user = new Users();
        user.setUsername("UpdatedUser");
        user.setEmail("updateduser@example.com");
        user.setPassword("newpassword");

        String successMessage = "User updated successfully";
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setStatus("success");
        successResponse.setMessage(successMessage);

        when(userService.updateUser(eq(userID), any(Users.class))).thenReturn(successMessage);

       
        mockMvc.perform(put("/api/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value(successMessage))
                .andDo(print());

        verify(userService, times(1)).updateUser(eq(userID), any(Users.class));
    }

    
    @Test
    void testGetNotificationByUserID() throws Exception {
        int userID = 1;

        SuccessResponseGet successResponseGet = new SuccessResponseGet();
        successResponseGet.setStatus("success");

        // Create mock notifications as Map
        List<Object> notifications = new ArrayList<>();
        Map<String, String> notification1 = new HashMap<>();
        notification1.put("title", "Notification 1");
        notification1.put("message", "This is the first notification");
        notifications.add(notification1);

        Map<String, String> notification2 = new HashMap<>();
        notification2.put("title", "Notification 2");
        notification2.put("message", "This is the second notification");
        notifications.add(notification2);

        successResponseGet.setData(notifications);

        when(userService.getNotificationByUserID(userID)).thenReturn(successResponseGet);

        mockMvc.perform(get("/api/users/1/notification"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].title").value("Notification 1"))
                .andExpect(jsonPath("$.data[0].message").value("This is the first notification"))
                .andExpect(jsonPath("$.data[1].title").value("Notification 2"))
                .andExpect(jsonPath("$.data[1].message").value("This is the second notification"))
                .andDo(print());

        verify(userService, times(1)).getNotificationByUserID(userID);
    }

}
