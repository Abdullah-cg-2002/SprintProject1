package com.sprint.app.dto;

import com.sprint.app.model.Users;

public class GroupDTO {
	
	private String groupName;
	private Users admin;
	
	
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
