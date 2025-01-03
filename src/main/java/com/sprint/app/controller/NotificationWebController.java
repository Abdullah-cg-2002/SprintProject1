package com.sprint.app.controller;

import com.sprint.app.model.Notifications;
import com.sprint.app.services.NotificationService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Thymeleaf Notification Controller handles the view-related logic for notifications.
 * It works with the NotificationService to fetch, mark as read, and delete notifications.
 */
@Controller
@RequestMapping("/notifications")
public class NotificationWebController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationWebController.class);

    @Autowired
    private NotificationService notificationService;

    /**
     * Displays all notifications for a specific user.
     *
     * @param userID ID of the user to fetch notifications for.
     * @param model  Model object for rendering the view.
     * @return The view name with notification data.
     */
    @GetMapping("/user/{userID}")
    public String getAllNotificationsForUser(@PathVariable int userID, Model model) {
        logger.info("Fetching all notifications for user ID: {}", userID);
        List<Notifications> notifications = notificationService.getAllNotification(userID);
        model.addAttribute("notifications", notifications);
        model.addAttribute("userID", userID);
        logger.info("Loaded notifications into model for user ID: {}", userID);
        return "notifications"; 
    }

    /**
     * Displays the details of a specific notification.
     *
     * @param notificationID ID of the notification to fetch.
     * @param model          Model object for rendering the view.
     * @return The view name with notification details.
     */
    @GetMapping("/{notificationID}")
    public String getSpecificNotification(@PathVariable int notificationID, Model model) {
        logger.info("Fetching details for notification ID: {}", notificationID);
        Notifications notification = notificationService.getSpecificNotification(notificationID);
        model.addAttribute("notification", notification);
        logger.info("Loaded specific notification into model with ID: {}", notificationID);
        return "notification-details";  
    }

    /**
     * Marks a notification as read.
     *
     * @param userID         ID of the user.
     * @param notificationID ID of the notification to mark as read.
     * @return Redirects back to the user's notifications view after marking the notification as read.
     */
    @PostMapping("/mark-read/{userID}/{notificationID}")
    public String markNotificationAsRead(@PathVariable int userID, @PathVariable int notificationID) {
        logger.info("Marking notification ID: {} as read for user ID: {}", notificationID, userID);
        notificationService.MarkNotificationAsRead(userID, notificationID);
        logger.info("Notification ID: {} marked as read successfully for user ID: {}", notificationID, userID);
        return "redirect:/notifications/user/" + userID;  
    }

    /**
     * Deletes a specific notification.
     *
     * @param userID         ID of the user.
     * @param notificationID ID of the notification to delete.
     * @return Redirects back to the user's notifications view after deletion.
     */
    @GetMapping("/delete/{userID}/{notificationID}")
    public String deleteNotification(@PathVariable int userID, @PathVariable int notificationID) {
        logger.info("Deleting notification ID: {} for user ID: {}", notificationID, userID);
        notificationService.deleteNotification(userID, notificationID);
        logger.info("Notification ID: {} deleted successfully for user ID: {}", notificationID, userID);
        return "redirect:/notifications/user/" + userID;  // Redirect back to the notifications list for the user
    }
}
