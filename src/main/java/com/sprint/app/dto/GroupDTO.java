package com.sprint.app.dto;

import com.sprint.app.model.Users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for representing a Group.
 * This class is used to transfer group data between layers, such as from the controller to service.
 * It contains the group's ID, name, and the admin responsible for the group.
 */
public class GroupDTO {

    /**
     * The unique identifier of the group.
     */
    private int groupID;

    /**
     * The name of the group.
     * This field cannot be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Group name cannot be blank")
    @Size(max = 255, message = "Group name should not exceed 255 characters")
    private String groupName;

    /**
     * The admin of the group.
     */
    private Users admin;

    /**
     * Retrieves the group ID.
     *
     * @return the ID of the group
     */
    public int getGroupID() {
        return groupID;
    }

    /**
     * Sets the group ID.
     *
     * @param groupID the ID to set for the group
     */
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    /**
     * Retrieves the name of the group.
     *
     * @return the name of the group
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the name of the group.
     *
     * @param groupName the name to set for the group
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Retrieves the admin of the group.
     *
     * @return the admin user of the group
     */
    public Users getAdmin() {
        return admin;
    }

    /**
     * Sets the admin for the group.
     *
     * @param admin the admin user to set for the group
     */
    public void setAdmin(Users admin) {
        this.admin = admin;
    }
}
