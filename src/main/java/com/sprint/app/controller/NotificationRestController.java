package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.services.NotificationService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;
import org.slf4j.*;

/**
 * Controller for managing notifications.
 * Provides endpoints to retrieve, mark as read, and delete notifications.
 */
@RestController
@RequestMapping("/api")
public class NotificationRestController {

	private static final Logger logger = LoggerFactory.getLogger(NotificationRestController.class);

	@Autowired
	private NotificationService ns;
	
	/**
	 * Retrieve all notifications for a specific user.
	 * 
	 * @param userID ID of the user.
	 * @return SuccessResponseGet containing a list of notifications.
	 */
	@GetMapping("/users/{userID}/notifications")
	public SuccessResponseGet getAllNotificationForUser(@PathVariable int userID)
	{
		logger.info("Retrieving all notifications for user with ID: {}", userID);
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> lis = new ArrayList<>();
		lis.add(ns.getAllNotification(userID));
		srg.setStatus("success");
		srg.setData(lis);
		logger.info("Notifications retrieved successfully for user ID: {}", userID);
		return srg;
	}
	
	/**
	 * Retrieve a specific notification by ID.
	 * 
	 * @param notificationID ID of the notification.
	 * @return SuccessResponseGet containing the specific notification.
	 */
	@GetMapping("/notifications/{notificationID}")
	public SuccessResponseGet getSpecificNotification(@PathVariable int notificationID)
	{
		logger.info("Retrieving specific notification with ID: {}", notificationID);
		SuccessResponseGet srg = new SuccessResponseGet();
		List<Object> lis = new ArrayList<>();
		lis.add(ns.getSpecificNotification(notificationID));
		srg.setStatus("success");
		srg.setData(lis);
		logger.info("Notification retrieved successfully with ID: {}", notificationID);
		return srg;
	}
	
	/**
	 * Mark a specific notification as read.
	 * 
	 * @param userID ID of the user.
	 * @param notificationID ID of the notification.
	 * @return SuccessResponse indicating the operation status.
	 */
	@PutMapping("/users/{userID}/notifications/mark-read/{notificationID}")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse readNotification(@PathVariable int userID, @PathVariable int notificationID)
	{
		logger.info("Marking notification as read for user ID: {}, notification ID: {}", userID, notificationID);
		ns.MarkNotificationAsRead(userID, notificationID);
		SuccessResponse sr= new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage("Notification marked as read");
		logger.info("Notification marked as read successfully for ID: {}", notificationID);
		return sr;
	}
	
	/**
	 * Delete a specific notification.
	 * 
	 * @param userID ID of the user.
	 * @param notificationID ID of the notification.
	 * @return SuccessResponse indicating the operation status.
	 */
	@DeleteMapping("users/{userID}/notifications/delete/{notificationID}")
	public SuccessResponse deleteNotification(@PathVariable int userID, @PathVariable int notificationID)
	{
		logger.info("Deleting notification with ID: {} for user ID: {}", notificationID, userID);
		ns.deleteNotification(userID, notificationID);
		SuccessResponse sr= new SuccessResponse();
		sr.setStatus("success");
		sr.setMessage("Notification deleted successfully");
		logger.info("Notification deleted successfully with ID: {}", notificationID);
		return sr;
	}
}
