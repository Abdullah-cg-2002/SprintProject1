package com.sprint.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.services.UserService;
import com.sprint.app.success.*;
import com.sprint.app.model.Users;
import org.slf4j.*;

/**
 * UserController handles all HTTP requests related to User management.
 * It provides endpoints to retrieve, add, delete, and search users,
 * as well as fetch groups associated with a specific user.
 */
@RestController
@RequestMapping("/api/")
public class UserRestController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    
    @Autowired
    private UserService us;

    /**
     * Retrieves all users.
     * 
     * @return SuccessResponseGet containing the status and list of users.
     */
    @GetMapping("users/all")
    public SuccessResponseGet getAllUsers()
    {
        logger.info("Retrieving all users");
        SuccessResponseGet srg = new SuccessResponseGet();
        List<Object> lis = new ArrayList<>();
        lis.addAll(us.getAllUsers());
        srg.setStatus("success");
        srg.setData(lis);
        logger.info("Retrieved all users");
        return srg;
    }

    /**
     * Retrieves a specific user based on user ID.
     * 
     * @param userID The ID of the user to retrieve.
     * @return SuccessResponseGet containing the status and user details.
     */
    @GetMapping("users/{userID}")
    public SuccessResponseGet getSpecificUser(@PathVariable int userID)
    {
        logger.info("Retrieving a specific user");
        SuccessResponseGet srg = new SuccessResponseGet();
        List<Object> lis = new ArrayList<>();
        lis.add(us.getSpecificUser(userID));
        srg.setStatus("success");
        srg.setData(lis);
        logger.info("Retrieved the specific user");
        return srg;
    }

    /**
     * Searches for users by their username.
     * 
     * @param username The username to search for.
     * @return SuccessResponseGet containing the status and matching users.
     */
    @GetMapping("users/search/{username}")
    public SuccessResponseGet searchForUserByName(@PathVariable String username)
    {
        logger.info("Searching for user by username");
        SuccessResponseGet srg = new SuccessResponseGet();
        List<Object> lis = new ArrayList<>();
        lis.addAll(us.searchForUserByName(username));
        srg.setStatus("success");
        srg.setData(lis);
        logger.info("Users retrieved");
        return srg;
    }

    /**
     * Adds a new user.
     * 
     * @param user The user object containing user details.
     * @return SuccessResponse with status and success message.
     */
    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse addUser(@RequestBody Users user)
    {
    	logger.info("Adding new user");
    	us.addUser(user);
    	SuccessResponse sr = new SuccessResponse();
    	sr.setStatus("Success");
    	sr.setMessage("User added Successfully");
    	logger.info("New User added Successfully");
    	return sr;
    }

    /**
     * Deletes a user based on user ID.
     * 
     * @param userID The ID of the user to be deleted.
     * @return SuccessResponse with status and success message.
     */
    @DeleteMapping("users/delete/{userID}")
    public SuccessResponse deleteUser(@PathVariable int userID)
    {
        logger.info("Deleting user");
        us.deleteUser(userID);
        SuccessResponse sr= new SuccessResponse();
        sr.setStatus("success");
        
        sr.setMessage("User removed successfully");
        logger.info("User removed successfully");
        return sr;
    }

    /**
     * Fetches all groups associated with a specific user.
     * 
     * @param userID The ID of the user whose groups are to be retrieved.
     * @return SuccessResponseGet containing the status and list of groups.
     */
    @GetMapping("users/{userID}/groups")
    public SuccessResponseGet getAllGroupsofUser(@PathVariable int userID)
    {
        logger.info("Fetching groups for user");
        SuccessResponseGet srg = new SuccessResponseGet();
        List<Object> lis = new ArrayList<>();
        lis.addAll(us.getAllGroupsofUser(userID));
        srg.setStatus("success");
        srg.setData(lis);
        logger.info("Fetched groups successfully for user");
        return srg;
    }
}
