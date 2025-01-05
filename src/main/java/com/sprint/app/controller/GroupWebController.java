package com.sprint.app.controller;

import com.sprint.app.model.Groups;
import com.sprint.app.model.Users;
import com.sprint.app.services.GroupsService;
import com.sprint.app.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/views")
public class GroupWebController {

    @Autowired
    private GroupsService groupsService;

    /**
     * Displays the list of all groups using Thymeleaf template.
     */


    @GetMapping("/groups")
    public String showGroupsList(Model model) {
        List<Object> groupsList = groupsService.getAllGroupsData();  // Assuming you have this method
        model.addAttribute("groups", groupsList);  // Add the list of groups to the model
        return "groups-list";  // Thymeleaf template for the groups list
    }

    /**
     * Displays a single group by its ID using Thymeleaf template.
     *
     * @param groupId The ID of the group to be displayed.
     */
    @GetMapping("/group/{groupId}")
    public String getGroupById(@PathVariable int groupId, Model model) {
        model.addAttribute("group", groupsService.getGroupDataByID(groupId));
        return "group-view";  // Thymeleaf template for displaying group details
    }

    /**
     * Displays the form to create a new group.
     */
 
    @GetMapping("/group/add")
    public String showCreateGroupForm(Model model) {
        Groups group = new Groups();  // Initialize an empty GroupDTO object
        Users admin = new Users();
        group.setAdmin(admin);
        model.addAttribute("group", group);  // Adding the empty groupDTO to the model
        return "group-add";  // Thymeleaf template for the group creation form
    }


    /**
     * Handles the form submission for creating a new group.
     *
     * @param group The group object to be created.
     * @param bindingResult The binding result to check for validation errors.
     */

    @PostMapping("/group/added")
    public String createGroup(@Valid @ModelAttribute("group") Groups group, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "group-add";  // Return the form with validation errors
        }

        // Convert DTO to the entity (if necessary) and then create the group
        
        groupsService.createNewGroup(group);  // Create the group in the database

        // Optionally, you can add a success message to the model if you need to display it
        model.addAttribute("successMessage", "Group created successfully!");

        // Redirect to the groups list after successful creation
        return "redirect:/views/groups";
    }


    

    /**
     * Displays the form to update an existing group.
     *
     * @param groupId The ID of the group to be updated.
     */
    @GetMapping("/group/edit/{groupId}")
    public String showUpdateGroupForm(@PathVariable("groupId") int groupId, Model model) {
        Groups group = groupsService.getGroupDataByID(groupId);
        model.addAttribute("group", group);  // Adding the group data to the model
        return "group-edit";  // Thymeleaf template for the group update form
    }

    /**
     * Handles the form submission for updating a group.
     *
     * @param groupId The ID of the group to be updated.
     * @param groupDTO The DTO containing the new group name.
     */
    
    
    @PostMapping("/group/update/{groupId}")
    public String updateGroup(@PathVariable("groupId") int groupId, @ModelAttribute Groups group) {
        groupsService.updateGroupName(group.getGroupName(), groupId);  // Update the group using a service
        return "redirect:/views/groups";  // Redirect to groups list after updating
    }

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
