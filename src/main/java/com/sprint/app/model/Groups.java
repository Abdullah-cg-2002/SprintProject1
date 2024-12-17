package com.sprint.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="`groups`")
public class Groups {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int groupID;
	private String groupName;
	
	@ManyToOne
	@JoinColumn(name="adminID", referencedColumnName = "userID")
	@JsonIgnore
	private Users admin;

	public int getGroupID() {
		return groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Users getAdmin() {
		return admin;
	}

	public void setAdmin(Users admin) {
		this.admin = admin;
	}
	
	
	
	
	

}
