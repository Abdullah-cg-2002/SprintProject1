package com.sprint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.dto.GroupDTO;
import com.sprint.app.services.GroupService;
import com.sprint.app.model.Groups;

@RestController
public class GroupController {
	
	@Autowired
	private GroupService gs;
	
	@PostMapping("/api/groups")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody GroupDTO gdto)
	{
		gs.createNewGroup(gdto);
	}
	
	@GetMapping("/api/friends-group/{userID}")
	public List<Groups> getGrps(@PathVariable int userID)
	{
		return gs.findGroupsofFrnds(userID);
	}

}
