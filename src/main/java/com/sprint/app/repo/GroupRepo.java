package com.sprint.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Groups;
import com.sprint.app.model.Users;

/**
 * Repository interface for performing CRUD operations on the {@link Groups} entity.
 * It extends {@link JpaRepository} to leverage Spring Data JPA's built-in functionality.
 * This repository provides a method to find a {@link Users} object based on the group ID.
 */
@Repository
public interface GroupRepo extends JpaRepository<Groups, Integer> {

    /**
     * Finds the {@link Users} associated with a given group ID.
     * 
     * @param groupID the ID of the group to find the user for
     * @return the {@link Users} associated with the specified group ID
     */
    Users findUsersByGroupID(int groupID);
}
