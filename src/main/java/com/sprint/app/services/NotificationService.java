package com.sprint.app.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.repo.NotificationRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.model.Notifications;
import com.sprint.app.model.Users;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepo nr;
	
	@Autowired
	private UserRepo ur;
	
	//get all notification for a user
	public List<Notifications> getAllNotif(int userID)
	{
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			return usropt.get().getNotification();
		}
		
		return null;
	}
	
	//create a notification
	public void createNotif(int userID)
	{
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			Users usr = usropt.get();
			
			boolean notif = false;
			for(Notifications ntf : usr.getNotification())
			{
				if(ntf.getContent().equalsIgnoreCase("You have a new notification"))
				{
					notif = true;
					break;
				}
			}
			
			Notifications ntf = new Notifications();
			
			if(notif)
			{
				ntf.setContent("Another notification for you");
				ntf.setTimestamp(LocalDateTime.now());
				usr.getNotification().add(ntf);
				ur.save(usr);
				nr.save(ntf);
			}
			else
			{
				ntf.setContent("You have a new notification");
				ntf.setTimestamp(LocalDateTime.now());
				usr.getNotification().add(ntf);
				ur.save(usr);
				nr.save(ntf);
			}
		}
	}
	
	//modify notification
	public void updateNotif(int userID, int notificationID)
	{
		Optional<Users> usropt = ur.findById(userID);
		Optional<Notifications> ntfopt = nr.findById(notificationID);
		
		if(usropt.isPresent())
		{
			if(ntfopt.isPresent())
			{
				Notifications ntf = ntfopt.get();
				Users usr = usropt.get();
				
				ntf.setContent("Notification mark as read");
				ntf.setTimestamp(LocalDateTime.now());
				
				ur.save(usr);
				nr.save(ntf);
			}
		}
	}
	
	//delete a notification
	public void deleteNotif(int userID, int notificationID)
	{
		Optional<Users> usropt = ur.findById(userID);
		Optional<Notifications> ntfopt = nr.findById(notificationID);
		
		if(usropt.isPresent())
		{
			if(ntfopt.isPresent())
			{
				Notifications ntf = ntfopt.get();
				Users usr = usropt.get();
				
				usr.getNotification().remove(ntf);
				ur.save(usr);
				nr.deleteById(notificationID);
				
			}
		}
	}

}
