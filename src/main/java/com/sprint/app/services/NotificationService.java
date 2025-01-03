package com.sprint.app.services;

import java.util.*;

import com.sprint.app.model.Notifications;

public interface NotificationService {
public List<Notifications> getAllNotification(int userID);
public Notifications getSpecificNotification(int notificationID);
public void MarkNotificationAsRead(int userID, int notificationID);
public void deleteNotification(int userID, int notificationID);
}
