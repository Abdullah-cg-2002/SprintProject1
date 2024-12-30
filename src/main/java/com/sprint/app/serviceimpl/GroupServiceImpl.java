package com.sprint.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.dto.GroupDTO;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Groups;
import com.sprint.app.model.Users;
import com.sprint.app.repo.GroupRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.GroupService;

@Service
public class GroupServiceImpl implements GroupService
{
	@Autowired
	private GroupRepo gr;
	
	@Autowired
	private UserRepo ur;

	@Override
	public void createNewGroup(GroupDTO gdto) {
		Users usr = ur.findById(gdto.getAdmin().getUserID()).get();
		Groups grp = new Groups();
		grp.setGroupName(gdto.getGroupName());
		grp.setAdmin(usr);
		usr.getGroups().add(grp);
		gr.save(grp);
		
	}

	@Override
	public List<Groups> findGroupsofFrnds(int userID) {
		List<Groups> grps = new ArrayList<>();
		
		Users usr = ur.findById(userID).get();
		for(Friends frds : usr.getFriendsent()) {
			grps.addAll(frds.getUser2().getGroups());
		}
		
		for(Friends frds : usr.getFriendsrec()) {
			grps.addAll(frds.getUser1().getGroups());
		}
		return grps;
	}

}
