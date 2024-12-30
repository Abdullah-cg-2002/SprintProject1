package com.sprint.app.services;

import java.util.List;

import com.sprint.app.dto.GroupDTO;
import com.sprint.app.model.Groups;

public interface GroupService {
	
	public void createNewGroup(GroupDTO gdto);
	public List<Groups> findGroupsofFrnds(int userID);

}
