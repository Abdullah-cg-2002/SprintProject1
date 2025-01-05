package com.sprint.app.controllertest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sprint.app.controller.NotificationRestController;
import com.sprint.app.model.Notifications;
import com.sprint.app.services.NotificationService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;

/**
 * Unit tests for NotificationController using Mockito.
 */
class NotificationControllerTest {

    @InjectMocks
    private NotificationRestController notificationController;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Test for retrieving all notifications for a user.
     */
    @Test
    void testGetAllNotificationForUser() {
        int userID = 1;
        List<Notifications> mockNotifications = new ArrayList<>();

        when(notificationService.getAllNotification(userID)).thenReturn(mockNotifications);

        SuccessResponseGet response = notificationController.getAllNotificationForUser(userID);

        assertEquals("success", response.getStatus());
        assertEquals(1, response.getData().size());
        verify(notificationService, times(1)).getAllNotification(userID);
    }

    /**
     * Test for retrieving a specific notification.
     */
    @Test
    void testGetSpecificNotification() {
        int notificationID = 10000003;
        Notifications mockNotification = new Notifications();

        when(notificationService.getSpecificNotification(notificationID)).thenReturn(mockNotification);

        SuccessResponseGet response = notificationController.getSpecificNotification(notificationID);

        assertEquals("success", response.getStatus());
        assertEquals(1, response.getData().size());
        assertEquals(mockNotification, response.getData().get(0));
        verify(notificationService, times(1)).getSpecificNotification(notificationID);
    }

    /**
     * Test for marking a notification as read.
     */
    @Test
    void testReadNotification() {
        int userID = 1;
        int notificationID = 10000003;

        SuccessResponse response = notificationController.readNotification(userID, notificationID);

        assertEquals("success", response.getStatus());
        assertEquals("Notification marked as read", response.getMessage());
        verify(notificationService, times(1)).MarkNotificationAsRead(userID, notificationID);
    }

    /**
     * Test for deleting a notification.
     */
    @Test
    void testDeleteNotification() {
        int userID = 1;
        int notificationID = 10000003;

        SuccessResponse response = notificationController.deleteNotification(userID, notificationID);

        assertEquals("success", response.getStatus());
        assertEquals("Notification deleted successfully", response.getMessage());
        verify(notificationService, times(1)).deleteNotification(userID, notificationID);
    }
}

