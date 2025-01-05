package com.sprint.app.controller;


import com.sprint.app.model.Friends;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.success.SuccessResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import com.sprint.app.services.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/web/")
@Validated
public class GroupWebController {

    private static final Logger logger = LoggerFactory.getLogger(GroupWebController.class);

    @Autowired
    private GroupService gs;

    /**
     * Retrieves the list of groups associated with a specific user.
     *
     * @param userID the unique identifier of the user. Must be a positive integer.
     * @return a list of Groups associated with the specified user.
     */
    @GetMapping("users/{userID}/groups")
    public String getUserGroup(@PathVariable  int userID, Model model) {
        logger.info("Retrieving groups for user with ID: {}", userID);
        List<Groups> groups = gs.getUserGroup(userID);
        logger.info("Found {} groups for user with ID: {}", groups.size(), userID);
        model.addAttribute("groups", groups);
        return "userGroups";
    }

    /**
     * Retrieves all messages in a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @return a list of Messages associated with the specified group.
     */
    @GetMapping("groups/{groupID}/message")
    public String getAllMessagesInGroup(@PathVariable int groupID, Model model) {
        logger.info("Retrieving all messages for group with ID: {}", groupID);
        List<Messages> messages = gs.getAllMessagesInGroup(groupID);
        logger.info("Retrieved {} messages for group with ID: {}", messages.size(), groupID);
        model.addAttribute("messages", messages);
        return "groupMessages";
    }

    
    /**
     * Adds a user as a member of a specific group.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @param userID  the unique identifier of the user to be added. Must be a positive integer.
     * @return the success status and message for adding the user.
     */
//    @PostMapping("groups/{groupID}/join/{userID}")
//    public String addUserAsMember(@PathVariable  int groupID, @PathVariable   int userID, Model model) {
//        logger.info("Adding user with ID: {} as member of group with ID: {}", userID, groupID);
//        String message = gs.addUserAsMember(groupID, userID);
//        model.addAttribute("status", "success");
//        model.addAttribute("message", message);
//        return "groupSuccess";
//    }
//
//    /**
//     * Sends a message to a specific group.
//     *
//     * @param groupID the unique identifier of the group. Must be a positive integer.
//     * @param userID  the unique identifier of the user sending the message. Must be a positive integer.
//     * @param message the message content to be sent.
//     * @return the success status and message for sending the message.
//     */
//    @PostMapping("groups/{groupID}/messages/send/{userID}")
//    public String sendMessageToGroup(@PathVariable @Min(1) int groupID, @PathVariable @Min(1) int userID, @RequestBody @Valid Messages message, Model model) {
//        logger.info("Sending message to group with ID: {} from user with ID: {}", groupID, userID);
//        Messages sentMessage = gs.sendMessageToGroup(groupID, userID, message);
//        logger.info("Message successfully sent to group with ID: {} from user with ID: {}", groupID, userID);
//        model.addAttribute("sentMessage", sentMessage);
//        return "messageSent";
//    }
//
//    /**
//     * Removes a user from a specific group.
//     *
//     * @param groupID the unique identifier of the group. Must be a positive integer.
//     * @param userID  the unique identifier of the user to be removed. Must be a positive integer.
//     * @return the success status and message for removing the user from the group.
//     */
//    @DeleteMapping("groups/{groupID}/leave/{userID}")
//    public String removeUserFromGroup(@PathVariable("groupID") @Min(1) int groupID, @PathVariable("userID") @Min(1) int userID, Model model) {
//        logger.info("Removing user with ID: {} from group with ID: {}", userID, groupID);
//        String message = gs.removeUserFromGroup(groupID, userID);
//        model.addAttribute("status", "success");
//        model.addAttribute("message", message);
//        return "groupSuccess";
//    }
//
//    /**
//     * Removes a group by its ID.
//     *
//     * @param groupID the unique identifier of the group to be removed.
//     * @return the success status and message for removing the group.
//     */
//    @DeleteMapping("groups/{groupID}")
//    public String removeGroupById(@PathVariable @Min(1) int groupID, Model model) {
//        logger.info("Removing group with ID: {}", groupID);
//        String message = gs.removeGroupById(groupID);
//        model.addAttribute("status", "success");
//        model.addAttribute("message", message);
//        return "deleteUser";
//    }
//
//    /**
//     * Removes a specific user from a group.
//     *
//     * @param groupID the unique identifier of the group. Must be a positive integer.
//     * @param userID  the unique identifier of the user to be removed. Must be a positive integer.
//     * @return the success status and message for removing the user from the group.
//     */
//    @DeleteMapping("groups/{groupID}/members/remove/{userID}")
//    public String removeUserFromAGroup(@PathVariable("groupID") @Min(1) int groupID, @PathVariable("userID") @Min(1) int userID, Model model) {
//        logger.info("Removing user with ID: {} from group with ID: {}", userID, groupID);
//        String message = gs.removeUserFromAGroup(groupID, userID);
//        model.addAttribute("status", "success");
//        model.addAttribute("message", message);
//        return "groupSuccess";
//    }

    @GetMapping("groups/{groupID}")
    public String removeGroup(@PathVariable  int groupID, Model model) {
        model.addAttribute("groupID", groupID);
        return "deleteGroup"; // Renders add-user-to-group.html
    }
    
    @PostMapping("groups/{groupID}")
  public String removeGroupById(@PathVariable @Min(1) int groupID, Model model) {
      logger.info("Removing group with ID: {}", groupID);
      String message = gs.removeGroupById(groupID);
      model.addAttribute("status", "success");
      model.addAttribute("message", message);
      return "groupSuccess";
    }

        /**
         * Displays a form for adding a user as a member of a group.
         *
         * @param groupID the ID of the group
         * @param model   the model object to pass data to the template
         * @return the Thymeleaf view name for adding a user to a group
         */
        @GetMapping("groups/{groupID}/join")
        public String showAddUserToGroupForm(@PathVariable  int groupID, Model model) {
            model.addAttribute("groupID", groupID);
            model.addAttribute("user", new Users());
            return "addUserToGroup"; // Renders add-user-to-group.html
        }

        /**
         * Adds a user as a member to a group.
         *
         * @param groupID the ID of the group
         * @param userID  the ID of the user to add
         * @param model   the model object to pass data to the template
         * @return the Thymeleaf view after adding the user
         */
        @PostMapping("groups/{groupID}/join/user")
        public String addUserAsMember(@PathVariable int groupID, @RequestParam  int userID, Model model) {
            String message = gs.addUserAsMember(groupID, userID);
            model.addAttribute("status", "success");
            model.addAttribute("message", message);
            return "groupSuccess"; // Renders group-success.html
        }

        /**
         * Displays a form for sending a message to a group.
         *
         * @param groupID the ID of the group
         * @param model   the model object to pass data to the template
         * @return the Thymeleaf view name for sending a message to a group
         */
        @GetMapping("groups/{groupID}/messages/send")
        public String showSendMessageForm(@PathVariable int groupID, Model model) {
            model.addAttribute("groupID", groupID);
            model.addAttribute("message", new Messages());
            return "messageSent"; // Renders send-message-to-group.html
        }

        /**
         * Sends a message to a specific group.
         *
         * @param groupID the ID of the group
         * @param userID  the ID of the user sending the message
         * @param message the message content
         * @param model   the model object to pass data to the template
         * @return the Thymeleaf view after sending the message
         */
//        @PostMapping("groups/{groupID}/messages/send/user")
//        public String sendMessageToGroup(@PathVariable int groupID, @RequestParam  int userID, @ModelAttribute Messages message, Model model) {
//            Messages sentMessage = gs.sendMessageToGroup(groupID, userID, message);
//            model.addAttribute("status", "success");
//            model.addAttribute("sentMessage", sentMessage);
//            return "sentMsg"; // Renders message-sent.html
//        }
        
        @PostMapping("groups/{groupID}/messages/send/userID")
        public String sendMessageToGroup(
                @PathVariable int groupID,
                @RequestParam int userID,
                @ModelAttribute Messages message,
                Model model) {

            // Create and send the message
            Messages message1 = new Messages();
            
            Messages sentMessage = gs.sendMessageToGroup(groupID, userID, message1);

            model.addAttribute("status", "success");
            model.addAttribute("sentMessage", sentMessage);
            return "sentMsg"; // Renders message-sent.html
        }

        /**
         * Displays a confirmation page for removing a user from a group.
         *
         * @param groupID the ID of the group
         * @param userID  the ID of the user to remove
         * @param model   the model object to pass data to the template
         * @return the Thymeleaf view name for removing a user from a group
         */
        @GetMapping("groups/{groupID}/members/remove/{userID}")
        public String confirmRemoveUserFromGroup(@PathVariable int groupID, @PathVariable  int userID, Model model) {
            model.addAttribute("groupID", groupID);
            model.addAttribute("userID", userID);
            logger.info("userid");
            return "deleteUser"; // Renders confirm-remove-user.html
        }

        /**
         * Removes a user from a group.
         *
         * @param groupID the ID of the group
         * @param userID  the ID of the user to remove
         * @param model   the model object to pass data to the template
         * @return the Thymeleaf view after removing the user
         */
        @PostMapping("groups/{groupID}/members/remove/{userID}")
        public String removeUserFromGroup(@PathVariable  int groupID, @PathVariable int userID, Model model) {
            String message = gs.removeUserFromAGroup(groupID, userID);
            model.addAttribute("status", "success");
            model.addAttribute("message", message);
            return "groupSuccess"; // Renders group-success.html
        }
    

    /**
     * Retrieves the list of all groups.
     *
     * @return a list of all groups.
     */
    @GetMapping("groups")
    public String getAllGroups(Model model) {
        logger.info("Retrieving all groups.");
        List<Groups> allGroups = gs.getAllGroups();
        model.addAttribute("allGroups", allGroups);
        return "allGroups";
    }
    
   


    
}
