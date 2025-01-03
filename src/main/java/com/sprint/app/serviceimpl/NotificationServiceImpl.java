package com.sprint.app.serviceimpl;

import com.sprint.app.services.NotificationService;
import java.time.LocalDateTime;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.repo.NotificationRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.model.Notifications;
import com.sprint.app.model.Users;

@Service
public class NotificationServiceImpl implements NotificationService
{
	@Autowired
	private NotificationRepo nr;
	@Autowired
	private UserRepo ur;
	
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
				if(ntf.getContent()!=null)
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
				ntf.setUser(usr);
				usr.getNotification().add(ntf);
				nr.save(ntf);
			}
			else
			{
				ntf.setContent("You have a new notification");
				ntf.setTimestamp(LocalDateTime.now());
				ntf.setUser(usr);
				usr.getNotification().add(ntf);
				nr.save(ntf);
			}
		}
	}


}
