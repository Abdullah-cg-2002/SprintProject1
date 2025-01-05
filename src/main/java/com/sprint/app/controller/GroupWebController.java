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

    
}
