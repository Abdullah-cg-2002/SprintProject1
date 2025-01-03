package com.sprint.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.*;

import com.sprint.app.exception.UserException;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Users;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.UserService;
import com.sprint.app.dto.MessageDTO;
import com.sprint.app.exception.UserException;

/**
 * Service implementation for managing user-related operations.
 * Provides functionalities for user retrieval, addition, deletion, and group management.
 */
@Service
public class UserServiceImpl implements UserService
{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepo ur;
	
	@Autowired
	private MessageService ms;
	
	@Autowired
	private FriendService fs;
	
	//send msg to the frnd
	public void sendMsgFrnd(int userID, int frdID) {
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
	

}