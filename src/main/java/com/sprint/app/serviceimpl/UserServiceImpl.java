package com.sprint.app.serviceimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.exception.UserNotFoundException;
import com.sprint.app.model.Comments;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Status;
import com.sprint.app.model.Users;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.FriendService;
import com.sprint.app.services.MessageService;
import com.sprint.app.services.UserService;

@Service
public class UserServiceImpl implements UserService
{
	
	@Autowired
	private UserRepo ur;
	

	
	
	 private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	//send msg to the frnd
	/*public void sendMsgFrnd(int userID, int frdID) {
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
	}*/

	
	
	
	
	
	/**
     * Retrieves all posts created by a specific user.
     * 
     * @param userID The ID of the user whose posts to retrieve.
     * @return A list of posts created by the user, or null if the user is not found.
     */
    @Override
    public List<Posts> getAllPostsUsr(int userID) {
        logger.info("Attempting to retrieve posts for user with ID: {}", userID);
        Optional<Users> usropt = ur.findById(userID);

        if (usropt.isPresent()) {
            logger.info("Found user with ID: {}", userID);
            return usropt.get().getPosts();
        }
        logger.warn("User with ID: {} not found.", userID);
        throw new UserNotFoundException("User not found with ID: " + userID);
    }

    /**
     * Retrieves all comments made on posts created by a specific user.
     * 
     * @param userID The ID of the user whose posts' comments are to be retrieved.
     * @return A list of comments on the user's posts, or null if the user is not found.
     */
    @Override
    public List<Comments> getAllCmtsPst(int userID) {
        logger.info("Attempting to retrieve comments for posts by user with ID: {}", userID);
        Optional<Users> usropt = ur.findById(userID);

        if (usropt.isPresent()) {
            List<Comments> cmts = new ArrayList<>();
            Users usr = usropt.get();

            // Iterate over the user's posts and collect all comments
            for (Posts pst : usr.getPosts()) {
                cmts.addAll(pst.getComments());
            }
            logger.info("Found {} comments for user with ID: {}", cmts.size(), userID);
            return cmts;
        }
        logger.warn("User with ID: {} not found.", userID);
        throw new UserNotFoundException("User not found with ID: " + userID);
    }

    /**
     * Retrieves all pending friend requests for a specific user.
     * A friend request is considered pending if the status is "PENDING".
     * 
     * @param userID The ID of the user whose pending friend requests are to be retrieved.
     * @return A list of pending friend requests, or null if the user is not found.
     */
    @Override
    public List<Object> getPendingFrndReq(int userID) {
        logger.info("Attempting to retrieve pending friend requests for user with ID: {}", userID);
        Optional<Users> usropt = ur.findById(userID);

        if (usropt.isPresent()) {
            Users usr = usropt.get();
            Set<Friends> allFrnds = new HashSet<>();
            List<Object> pendingReq = new ArrayList<>();

            // Add both sent and received friend requests
            allFrnds.addAll(usr.getFriendsent());
            allFrnds.addAll(usr.getFriendsrec());

            // Filter out pending friend requests
            for (Friends frnds : allFrnds) {
                if (Status.PENDING.equals(frnds.getStatus())) {
                    pendingReq.add(frnds);
                }
            }
            logger.info("Found {} pending friend requests for user with ID: {}", pendingReq.size(), userID);
            return pendingReq;
        }
        logger.warn("User with ID: {} not found.", userID);
        throw new UserNotFoundException("User not found with ID: " + userID);
    }

	



	/*@Override
	public Set<Friends> getAllFrndsUsr(int userID) {
		Optional<Users> usropt = ur.findById(userID);
		if(usropt.isPresent())
		{
			return usropt.get().getFriendsrec();
		}
		return null;
	}*/

	
	
}
