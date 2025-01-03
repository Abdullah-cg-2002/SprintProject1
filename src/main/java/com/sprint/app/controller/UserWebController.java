package com.sprint.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sprint.app.model.Users;
import com.sprint.app.services.UserService;

import org.slf4j.*;
import java.util.List;

/**
 * ThymeleafUserController handles HTTP requests for rendering views 
 * related to user management and groups using Thymeleaf.
 */
@Controller
@RequestMapping("/users")
public class UserWebController {

    private static final Logger logger = LoggerFactory.getLogger(UserWebController.class);

    @Autowired
    private UserService userService;

    /**
     * Displays all users in the "users.html" view.
     *
     * @param model Model object to add attributes for rendering.
     * @return The "users" template view.
     */
    @GetMapping
    public String getAllUsers(Model model) {
        logger.info("Fetching all users for Thymeleaf view");
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users", users); 
        logger.info("Loaded all users into model");
        return "users"; 
    }

    /**
     * Displays details of a specific user in the "user-details.html" view.
     *
     * @param userID ID of the user to fetch details for.
     * @param model Model object for rendering data.
     * @return The "user-details" template view.
     */
    @GetMapping("/{userID}")
    public String getSpecificUser(@PathVariable int userID, Model model) {
        logger.info("Fetching specific user details for Thymeleaf view");
        Users user = userService.getSpecificUser(userID);
        model.addAttribute("user", user); 
        logger.info("Loaded specific user details into model");
        return "user-details"; 
    }

    /**
     * Displays groups associated with a specific user in "user-groups.html".
     *
     * @param userID ID of the user.
     * @param model Model object for rendering groups.
     * @return The "user-groups" template view.
     */
    @GetMapping("/{userID}/groups")
    public String getAllGroupsOfUser(@PathVariable int userID, Model model) {
        logger.info("Fetching groups for user with ID: " + userID);
        Users user = userService.getSpecificUser(userID);
        model.addAttribute("groups", user.getGroups());
        model.addAttribute("username", user.getUsername());
        logger.info("Loaded groups into model for user-groups view");
        return "user-groups"; 
    }

    /**
     * Handles form submission for adding a new user.
     *
     * @param user User object populated from the form.
     * @return Redirects to the users page after adding.
     */
    @GetMapping("/add")
    public String setUser(Model model)
    {
    	Users user = new Users();
    	model.addAttribute("user", user);
    	return "addUser";
    }
    @PostMapping("/added")
    public String addUser(@ModelAttribute Users user) {
        logger.info("Adding a new user via form");
        userService.addUser(user);
        logger.info("User added successfully");
        return "redirect:/users"; 
    }

    /**
     * Deletes a user based on their ID and redirects to the users page.
     *
     * @param userID ID of the user to delete.
     * @return Redirects to the users list after deletion.
     */
    @GetMapping("/delete/{userID}")
    public String deleteUser(@PathVariable int userID) {
        logger.info("Deleting user with ID: " + userID);
        userService.deleteUser(userID);
        logger.info("User deleted successfully");
        return "redirect:/users"; 
    }

    /**
     * Searches for users by username and displays results in "users.html".
     *
     * @param username Username to search for.
     * @param model Model object to add results.
     * @return The "users" template with search results.
     */
    @GetMapping("/search")
    public String searchUsers(@RequestParam("username") String username, Model model) {
        logger.info("Searching for users with username: " + username);
        List<Users> users = userService.searchForUserByName(username);
        model.addAttribute("users", users); 
        logger.info("Search results added to model");
        return "users"; 
    }
}
