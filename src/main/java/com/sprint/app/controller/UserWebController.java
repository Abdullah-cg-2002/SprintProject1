package com.sprint.app.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sprint.app.services.UserService;

import com.sprint.app.success.SuccessResponseGet;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;

//@Controller
//@RequestMapping("/users/")
//@Validated
//public class UserThymeleaf {
//    private static final Logger logger = LoggerFactory.getLogger(UserThymeleaf.class);
//
//    @Autowired
//    private UserService us;
//
//
//    @PutMapping("update/{userID}")
//    public String updateUser(@PathVariable @Min(1) int userID, @Valid @RequestBody Users user, Model model) {
//        logger.info("Received update request for user with ID: {}", userID);
//        String message = us.updateUser(userID, user);
//
//        logger.info("User with ID {} updated successfully. Message: {}", userID, message);
//        model.addAttribute("message", "User updated successfully: " + message);
//        return "success";
//    }
//
//    @GetMapping("{userID}/notifications")
//    public String getNotificationByUserID(@PathVariable @Min(1) int userID, Model model) {
//        logger.info("Retrieving notifications for user with ID: {}", userID);
//        SuccessResponseGet response = us.getNotificationByUserID(userID);
//        model.addAttribute("notifications", response);
//        logger.info("Successfully retrieved notifications for user with ID: {}", userID);
//        return "notifications";
//    }
//
//    @GetMapping("getAll")
//    public String getAllUsers(Model model) {
//        List<Users> users = us.getAllUsers();
//        model.addAttribute("users", users);
//        return "users";
//    }
//
//}


@Controller
@Validated
@RequestMapping("/users")
public class UserWebController {

    @Autowired
    private UserService userService;

    /**
     * Displays all users in a Thymeleaf template.
     *
     * @param model the model object to pass data to the template
     * @return the Thymeleaf view name for displaying all users
     */
    @GetMapping("/getAll")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users"; // Renders users.html in the templates directory
    }

    /**
     * Displays notifications for a specific user in a Thymeleaf template.
     *
     * @param userID the ID of the user whose notifications are to be displayed
     * @param model  the model object to pass data to the template
     * @return the Thymeleaf view name for displaying notifications
     */
    @GetMapping("/{userID}/notifications")
    public String getNotificationsByUserID(@PathVariable @Min(1) int userID, Model model) {
        try {
            SuccessResponseGet response = userService.getNotificationByUserID(userID);
            model.addAttribute("notifications", response.getData());
            model.addAttribute("status", response.getStatus());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "notifications"; // Renders notifications.html in the templates directory
    }
    


    /**
     * Handles user update requests from a form.
     *
     * @param userID the ID of the user to update
     * @param user   the user object with updated data
     * @param model  the model object to pass data to the template
     * @return the Thymeleaf view name after updating the user
     */
    @PostMapping("/update/{userID}")
    public String updateUser(@PathVariable @Min(1) int userID, @Valid @ModelAttribute("user") Users user, Model model) {
        try {
            String message = userService.updateUser(userID, user);
            model.addAttribute("message", message);
            return "redirect:/users/getAll"; // Redirect to the user list page
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "update-user"; // Redirect to an error page or update form
        }
    }

    /**
     * Displays a form for editing a user's details.
     *
     * @param userID the ID of the user to edit
     * @param model  the model object to pass data to the template
     * @return the Thymeleaf view name for editing user details
     */
    @GetMapping("/edit/{userID}")
    public String editUser(@PathVariable @Min(1) int userID, Model model) {
        Users user = userService.getUserById(userID);
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "error"; // Error page
        }
        model.addAttribute("user", user);
        return "edit-user"; // Renders edit-user.html in the templates directory
    }
    
    @GetMapping("/{userID}/notifications/redirect")
    public String redirectFromNotifications() {
        return "redirect:/users/getAll";
    }
}