package com.sprint.app.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Friends;
import com.sprint.app.model.Users;

@Repository
public interface FriendsRepo extends JpaRepository<Friends, Integer>
{
	@Query("SELECT f FROM Friends f WHERE f.user1.userID = :userID OR f.user2.userID = :userID")
    List<Friends> findFriendsByUserId(@Param("userID") int userID);
	
	
	    
	   
	}


