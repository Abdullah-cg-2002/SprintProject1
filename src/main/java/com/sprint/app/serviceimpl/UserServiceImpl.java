package com.sprint.app.serviceimpl;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.exception.*;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Notifications;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.FriendService;
import com.sprint.app.services.MessageService;
import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponseGet;

@Service
public class UserServiceImpl implements UserService
{
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private MessageService ms;
	
	@Autowired
	private FriendService fs;
	
	@Autowired
	public UserServiceImpl(UserRepo ur) {
		this.ur = ur;
	}

	//send msg to the frnd
	public void sendMsgFrnd(int userID, int frdID) {
		Optional<Users> usropt = ur.findById(userID);
		Optional<Users> frdopt = ur.findById(frdID);
		
		if(usropt.isPresent() && frdopt.isPresent())
		{
			Messages msg = new Messages();
			msg.setMessage_text("Hello, How are you?");
			msg.setReceiver(frdopt.get());
			msg.setSender(usropt.get());
			ms.createMsg(msg);
		}
	}
	
	//send a frnd request
	public void sendFrdReq(int userID, int frdID)
	{
		fs.addFrnd(userID, frdID);
	}
	
	//msg between 2 users
	public List<Messages> msgBtwUsers(int userID, int otherID)
	{
		Optional<Users> usropt = ur.findById(userID);
		Optional<Users> othopt = ur.findById(otherID);
		
		List<Messages> chats = new ArrayList<>();
		
		if(usropt.isPresent() && othopt.isPresent())
		{
			for(Messages mgs : ms.getMsgSpecificUser(userID))
			{
				if(mgs.getReceiver().getUserID() == otherID || mgs.getSender().getUserID() == otherID)
					chats.add(mgs);
			}
			
			return chats;
		}
		
		else
		{
			return null;
		}
		
		
	}
	
	//get all likes get by user on all posts
	public List<Likes> getAllLikesPst(int userID)
	{
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			List<Likes> likes = new ArrayList<>();
			Users usr = usropt.get();
			for(Posts pst : usr.getPosts())
			{
				likes.addAll(pst.getLikes());
			}
			
			return likes;
		}
		
		return null;
	}
	
	//get all likes done by a user
	public List<Likes> getAllLikesUsr(int userID)
	{
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			return usropt.get().getLikes();
		}
		
		return null;
	}

	
	public Users getUserById(int userID) {
        Optional<Users> userOptional = ur.findById(userID);
        return userOptional.orElse(null); // Return null if the user is not found
    }
	/**
     * Updates the information of a user.
     *
     * @param userID the unique identifier of the user to be updated. Must be a positive integer.
     * @param user   the Users object containing updated information. Must not be null.
     * @return a message indicating the result of the update operation.
     * @throws RuntimeException if the email or username already exists for another user.
     */
	@Override
	public String updateUser(int userID, Users user) {
		 logger.info("Starting update for user with ID: {}", userID);
		 
		List<Users> existingUserByMail = ur.findByEmail(user.getEmail());
		List<Users> existingUserByName = ur.findByUsername(user.getUsername());
		
		if(!existingUserByName.isEmpty() && existingUserByName.get(0).getUserID()!=userID) {
			logger.error("Email already exists for user with ID: {}", userID);
			throw new UserException("Email already exists");
		}
		if(!existingUserByMail.isEmpty() && existingUserByMail.get(0).getUserID() !=userID) {
			logger.error("Username already exists for user with ID: {}", userID);
			throw new UserException("Username already exists");
		}
		
		Users u1 = ur.findById(userID).get();
		u1.setUsername(user.getUsername());
		u1.setEmail(user.getEmail());
		u1.setPassword(user.getPassword());
		ur.save(u1);
		
		logger.info("User with ID: {} successfully updated", userID);
		return "User updated successfully";
	}
	

    /**
     * Retrieves notifications for a specific user.
     *
     * @param userID the unique identifier of the user whose notifications are to be retrieved. Must be a positive integer.
     * @return a SuccessResponseGet object containing the status and list of notifications for the specified user.
     * @throws RuntimeException if no notifications are found for the given user ID.
     */
	@Override
	public SuccessResponseGet getNotificationByUserID(int userID) {
		logger.info("Retrieving notifications for user with ID: {}", userID);
		Users u2 = ur.findById(userID).get();
		List<Notifications> notification = u2.getNotification();
		if(notification.isEmpty()) {
			logger.error("No notifications found for user with ID: {}", userID);
			throw new UserException("Notification not found for the given user ID");
		}
		SuccessResponseGet response = new SuccessResponseGet();
	    response.setStatus("success");
	    response.setData(new ArrayList<>(notification));  
	    logger.info("Successfully retrieved notifications for user with ID: {}", userID);
	    return response;
	}


	public List<Users> getAllUsers() {
        return ur.findAll();
    }
	
}
