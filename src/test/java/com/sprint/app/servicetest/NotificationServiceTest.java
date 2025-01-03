package com.sprint.app.servicetest;

import static org.junit.jupiter.api.Assertions.*;

import com.sprint.app.exception.NotificationException;
import com.sprint.app.model.Notifications;
import com.sprint.app.repo.NotificationRepo;

import com.sprint.app.serviceimpl.NotificationServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class NotificationServiceTest {

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private NotificationRepo notificationRepo;

    @Test
    @Transactional
    public void testGetAllNotifi_UserExists() {
      

        List<Notifications> result = notificationService.getAllNotification(1);
        assertFalse(result.isEmpty());
    }

    @Test
    @Transactional
    public void testGetAllNotifi_UserNotFound() {
        List<Notifications> result = notificationService.getAllNotification(999); 
        assertNull(result);
    }

    @Test
    public void testGetSpecNotifi_Success() {
        
        Notifications result = notificationService.getSpecificNotification(10000002);
        assertNotNull(result);
        assertEquals("Another notification for you", result.getContent());
    }

    @Test
    public void testGetSpecNotifi_NotificationNotFound() {
        assertThrows(NotificationException.class, () -> {  notificationService.getSpecificNotification(999); 
        });
        
    }

    @Test
    @Transactional
    public void testMarkNotifiAsRead_Success() {
        
        notificationService.MarkNotificationAsRead(1, 10000002);

        Notifications updatedNotification = notificationRepo.findById(10000002).orElse(null);
        assertNotNull(updatedNotification);
        assertEquals("Another notification for you", updatedNotification.getContent());
    }

    @Test
    @Transactional
    public void testDeleteNotifi_Success() {
        

        notificationService.deleteNotification(1, 10000002);

        assertFalse(notificationRepo.findById(10000002).isPresent());
    }

    
}