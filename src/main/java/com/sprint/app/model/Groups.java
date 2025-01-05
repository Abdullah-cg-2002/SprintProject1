package com.sprint.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * Represents a group in the application.
 * <p>
 * This class is mapped to the "groups" table in the database and contains
 * information about the group, including its ID, name, and the admin user.
 * </p>
 */
@Entity
@Table(name="`groups`")
public class Groups {
	/**
     * The unique identifier for the group.
     */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int groupID;
	
	/**
     * The name of the group.
     * <p>
     * This field is required and must not exceed 100 characters.
     * </p>
     */
	@NotBlank(message = "Group name is required")
	@Size(max = 100, message = "Group name must not exceed 100 characters")
	private String groupName;
	
	/**
     * The admin user of the group.
     * <p>
     * This field is required and is a many-to-one relationship with the Users entity.
     * </p>
     */
	@NotNull(message = "Admin is required")
	@ManyToOne  
	@JoinColumn(name="adminID", referencedColumnName = "userID")
	@JsonIgnore
	private Users admin;
	
	 /**
     * Gets the unique identifier for the group.
     *
     * @return the group ID
     */
    public int getGroupID() {
        return groupID;
    }

    /**
     * Gets the name of the group.
     *
     * @return the group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the name of the group.
     *
     * @param groupName the new group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Gets the admin user of the group.
     *
     * @return the admin user
     */
    public Users getAdmin() {
        return admin;
    }

    /**
     * Sets the admin user of the group.
     *
     * @param admin the new admin user
     */
    public void setAdmin(Users admin) {
        this.admin = admin;
    }

    /**
     * Sets the unique identifier for the group.
     *
     * @param groupID the new group ID
     */
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
	
	
	
	

}
