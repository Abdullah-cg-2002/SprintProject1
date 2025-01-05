package com.sprint.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sprint.app.exception.GroupException;
import com.sprint.app.model.Groups;
import com.sprint.app.repo.GroupRepo;
import com.sprint.app.services.GroupsService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupRepo groupsRepository;

    // Create a new group
    @Override
    public void createNewGroup(Groups group) {
        group.setGroupName(group.getGroupName());
        // Save the new group to the repository
        group.getAdmin().getGroups().add(group);
        groupsRepository.save(group);
    }

    // Retrieve a group by its ID
    @Override
    public Groups getGroupDataByID(int groupId) {
        return groupsRepository.findById(groupId).orElseThrow(() -> 
            new GroupException("Group with id " + groupId + " does not exist."));
    }

    @Override
    public List<Object> getAllGroupsData() {
        return new ArrayList<>(groupsRepository.findAll());
    }

    

    // Update an existing group by its ID
    @Override
    public Groups updateGroupName(String groupName, int groupId) {
        Groups group = groupsRepository.findById(groupId).orElseThrow(() -> 
            new GroupException("Group with id " + groupId + " does not exist."));

        group.setGroupName(groupName);
        return groupsRepository.save(group); // Save the updated group to the repository
    }

	


}