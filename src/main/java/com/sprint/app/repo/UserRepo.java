package com.sprint.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.sprint.app.model.Users;
import java.util.*;

/**
 * Repository interface for managing User entities.
 * <p>
 * This interface extends JpaRepository to provide CRUD operations for Users and custom query methods
 * to retrieve users by their username and email.
 * </p>
 */
@Repository
public interface UserRepo extends JpaRepository<Users, Integer>
{
	/**
     * Retrieves a list of users with the specified username.
     *
     * @param username the username of the users to be retrieved. Must not be null.
     * @return a list of Users with the specified username. If no users are found, an empty list is returned.
     */
	List<Users> findByUsername(String username);
	/**
     * Retrieves a list of users with the specified email.
     *
     * @param email the email of the users to be retrieved. Must not be null.
     * @return a list of Users with the specified email. If no users are found, an empty list is returned.
     */
	List<Users> findByEmail(String email);
}
