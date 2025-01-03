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

	/**
	 * Retrieves all users from the repository.
	 *
	 * @return List of all users.
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
