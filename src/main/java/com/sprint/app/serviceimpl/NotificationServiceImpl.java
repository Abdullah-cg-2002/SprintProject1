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

    /**
     * Retrieves all notifications for a specific user.
     * 
     * @param userID The ID of the user whose notifications are to be fetched.
     * @return A list of notifications for the specified user, or null if the user is not found.
     */
    @Override
    public List<Notifications> getAllNotification(int userID) {
        logger.info("Fetching all notifications for user");
        Optional<Users> usropt = ur.findById(userID);

        if(usropt.isPresent()) {
            logger.info("User found");
            return usropt.get().getNotification();
        }
        logger.warn("User not found with ID: {}", userID);
        return null;
    }

    /**
     * Retrieves a specific notification by its ID.
     * 
     * @param notificationID The ID of the notification to be fetched.
     * @return The notification if found.
     * @throws RuntimeException if the notification is not found.
     */
    @Override
    public Notifications getSpecificNotification(int notificationID) {
        logger.info("Fetching specific notification");
        Optional<Notifications> n = nr.findById(notificationID);
        if(n.isEmpty()) {
            logger.error("Notification not found");
            throw new NotificationException("Notification not found");
        }
        logger.info("Notification found");
        return n.get();
    }

    /**
     * Marks a specific notification as read for a given user.
     * 
     * @param userID The ID of the user.
     * @param notificationID The ID of the notification to be marked as read.
     */
    @Override
    public void MarkNotificationAsRead(int userID, int notificationID) {
        logger.info("Marking notification as read");
        Optional<Users> uopt = ur.findById(userID);
        Optional<Notifications> nopt = nr.findById(notificationID);

        if(uopt.isPresent()) {
            logger.info("User found");
            if(nopt.isPresent()){
                Notifications n = nopt.get();
                Users u = uopt.get();
                for(Notifications nf : u.getNotification()) {
                    if(nf.getNotificationID() == n.getNotificationID()) {
                        logger.info("Marking notification as read");
                        n.setContent("mark as read");
                        nr.save(n);
                    }
                }
            }
        }
        else {
            logger.warn("User or Notification not found");
        }
    }

    /**
     * Deletes a specific notification for a given user.
     * 
     * @param userID The ID of the user.
     * @param notificationID The ID of the notification to be deleted.
     */
    @Override
    public void deleteNotification(int userID, int notificationID) {
        logger.info("Deleting notification");
        Optional<Users> uopt = ur.findById(userID);
        Optional<Notifications> nopt = nr.findById(notificationID);

        if(uopt.isPresent()) {
            logger.info("User found");
            if(nopt.isPresent()) {
                logger.info("Notification found");
                Notifications n = nopt.get();
                Users u = uopt.get();

                u.getNotification().remove(n);
                nr.deleteById(notificationID);
                logger.info("Notification deleted");
            }
            else {
                logger.warn("Notification not found");
            }
        }
        else {
            logger.warn("User not found");
        }
    }    

}
