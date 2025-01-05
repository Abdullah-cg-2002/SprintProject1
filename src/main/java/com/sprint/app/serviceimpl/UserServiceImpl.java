package com.sprint.app.serviceimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.*;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.*;

import com.sprint.app.exception.UserException;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.LikeRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.UserService;
import com.sprint.app.dto.MessageDTO;
import com.sprint.app.exception.UserException;

/**
 * Service implementation for managing user-related operations.
 * Provides functionalities for user retrieval, addition, deletion, and group management.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepo ur;
	

	
	
	 private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	//send msg to the frnd
	public String sendMsgFrnd(int userID, int frdID, MessageDTO msgdto) {
		Optional<Users> usropt = ur.findById(userID);
		Optional<Users> frdopt = ur.findById(frdID);
		
		if(usropt.isPresent() && frdopt.isPresent())
		{
			msgdto.setSender(usropt.get());
			msgdto.setReceiver(frdopt.get());
			ms.createMsg(msgdto);
			return "success";
		}
		
		else
		{
			throw new UserException("User or Friend not found");
		}
	}
	
	/**
	 * @param userID
	 * @param frdID
	 * @return success if friend request sent 
	 */
	
	public String sendFrdReq(int userID, int frdID)
	{
		Optional<Users> user = ur.findById(userID);
		Optional<Users> frd = ur.findById(frdID);
		if(user.isPresent() && frd.isPresent())
			return fs.addFrnd(userID, frdID);
		else
			throw new UserException("UserId or FriendId not found");
	}
	
	/**
	 * @param userID
	 * @param otherID
	 * @return list of message between users
	 */
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
			throw new UserException("User or Receiver not found");
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
		
		else
		{
			throw new UserException("User not found");
		}
	}
	
	//get all likes done by a user
	public List<Likes> getAllLikesUsr(int userID)
	{
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			return usropt.get().getLikes();
		}
		
		else
		{
			throw new UserException("User not found");
		}
	}

	/**
	 * Retrieves all users from the repository.
	 *
	 * @return List of all users.
	 */
	public List<Users> getAllUsers() {
		logger.info("Fetching all users");
		return ur.findAll();
	}

	/**
	 * Retrieves a specific user by their ID.
	 *
	 * @param userID ID of the user to be retrieved.
	 * @return The user with the specified ID.
	 * @throws RuntimeException if the user ID is not found.
	 */

	public Users getSpecificUser(int userID) {
		logger.info("Fetching specific user");
		Optional<Users> opt = ur.findById(userID);
		if(opt.isPresent())
		{
			logger.info("User found");
			return opt.get();
		}
		else
		{
			logger.error("User ID not found");
			throw new UserException("userid not found");
		}
	}

	/**
	 * Searches for users by their username.
	 *
	 * @param username The username to search for.
	 * @return List of users matching the given username.
	 * @throws RuntimeException if no users with the username are found.
	 */

	public List<Users> searchForUserByName(String username) {
		logger.info("Searching for user by username");
		List<Users> lis = new ArrayList<>();
		lis.addAll(ur.findByUsername(username));
		if(lis.isEmpty())
		{
			logger.warn("Username not found");
			throw new UserException("Username not found");
		}
		logger.info("Users found");
		return lis;
	}

	/**
	 * Adds a new user to the repository.
	 *
	 * @param user The user object to be added.
	 * @throws RuntimeException if the email or password format is invalid.
	 */

	public void addUser(Users user) {
		logger.info("Adding a new user");
		String regemail = "^[a-z0-9]{5,}@[a-z]{2,}+\\.[a-z]{2,}$";
		Pattern pe = Pattern.compile(regemail);
 
		String email = user.getEmail();
		Matcher mt = pe.matcher(email);
 
		if(!mt.matches())
		{
			logger.error("Invalid email format");
			throw new UserException("Email not Valid");
		}
		String regpass = "^(?=.*[@#$%*]).{5,}";
		Pattern pp = Pattern.compile(regpass);
 
		String password = user.getPassword();
		mt = pp.matcher(password);
 
		if(!mt.matches())
		{
			logger.error("Invalid password format");
			throw new UserException("Password not valid");
		}
		ur.save(user);
		logger.info("User added successfully");
	}

	/**
	 * Deletes a user from the repository.
	 *
	 * @param userID ID of the user to be deleted.
	 */

	public void deleteUser(int userID) {
		logger.info("Deleting user");
		Optional<Users> opt = ur.findById(userID);
		if(opt.isPresent())
		{
			System.out.println(opt.get().getUserID());
		   ur.deleteById(userID);
		   logger.info("User has been Deleted");
		}
		else
		{
			throw new UserException("UserID doesn't exist");
		}
		
	}

	/**
	 * Retrieves all groups associated with a specific user.
	 *
	 * @param userID ID of the user whose groups are to be retrieved.
	 * @return List of groups associated with the user.
	 * @throws RuntimeException if the user ID is not found.
	 */

	public List<Groups> getAllGroupsofUser(int userID) {
		logger.info("Fetching all groups of user");
		Optional<Users> opt = ur.findById(userID);
		if(opt.isPresent())
		{
			logger.info("User found");
			Users usr = opt.get();
            return usr.getGroups();
		}
		else
			logger.error("User not found");
			throw new UserException("User not found");
	}

	public void removeLikeFromPost(int postId, int likesId) {
        // Find the like by its ID
        Likes like = lr.findById(likesId)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        // Ensure the like is associated with the correct post
        if (like.getPosts().getPostID() != postId) {
            throw new RuntimeException("This like does not belong to the specified post");
        }

        // Remove the like from the database
        Posts post = like.getPosts();
        post.getLikes().remove(like);
        lr.delete(like);
        
    }

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
	

}