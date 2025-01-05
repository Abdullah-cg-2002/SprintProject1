package com.sprint.app.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Groups;
import com.sprint.app.model.Users;

/**
 * Repository interface for managing Group entities.
 * <p>
 * This interface extends JpaRepository to provide CRUD operations for Groups and custom query methods
 * to retrieve users and friends associated with groups.
 * </p>
 */
@Repository
public interface GroupRepo extends JpaRepository<Groups, Integer>
{
	/**
     * Retrieves the admin user of a specific group by its ID.
     *
     * @param groupID the unique identifier of the group. Must be a positive integer.
     * @return the Users object representing the admin of the specified group.
     */
	  @Query("SELECT u FROM Users u WHERE u.userID = (SELECT g.admin.userID FROM Groups g WHERE g.groupID = :groupID)")
	  Users findUsersByGroupID(@Param("groupID") int groupID);
	  /**
	     * Retrieves a list of friends who are members of a specific group.
	     *
	     * @param groupID the unique identifier of the group. Must be a positive integer.
	     * @return a list of Users who are friends of the group members and have an accepted friendship status.
	     */
	  @Query("SELECT u FROM Users u WHERE u.userID IN (SELECT f.user1.userID FROM Friends f WHERE f.user2.userID IN (SELECT g.admin.userID FROM Groups g WHERE g.groupID = :groupID) AND f.status = 'accepted') OR u.userID IN (SELECT f.user2.userID FROM Friends f WHERE f.user1.userID IN (SELECT g.admin.userID FROM Groups g WHERE g.groupID = :groupID) AND f.status = 'accepted')")
	  List<Users> findFriendsOfGroupMembers(@Param("groupID") int groupID);
	  
	  /**
	     * Retrieves a list of groups managed by a specific admin user.
	     *
	     * @param user the Users object representing the admin user. Must not be null.
	     * @return a list of Groups associated with the specified admin user.
	     */
	  List<Groups> findByAdmin(Users user);
	  
//	  @Query("SELECT g FROM Groups g JOIN g.members m WHERE m.id IN (SELECT f.friendId FROM Friend f WHERE f.groupId = g.id)")
//	  public List<Groups> findFriendsOfGroupMembers1(int groupId);
	  
	  /**
     * Finds the {@link Users} associated with a given group ID.
     * 
     * @param groupID the ID of the group to find the user for
     * @return the {@link Users} associated with the specified group ID
     */
    // Users findUsersByGroupID(int groupID);

	 

    
}
