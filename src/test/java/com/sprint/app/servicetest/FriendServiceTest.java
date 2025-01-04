package com.sprint.app.servicetest;

import static org.junit.jupiter.api.Assertions.*;

import com.sprint.app.exception.FriendException;
import com.sprint.app.model.*;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.serviceimpl.FriendServiceImpl;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
class FriendServiceTest {

    @Autowired
    private FriendServiceImpl friendService;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void testGetAllFrnds_UserExists() {
        Users user = new Users();
        user.setFriendsent(new HashSet<>());
        user.setFriendsrec(new HashSet<>());

        userRepo.save(user); 
        Set<Friends> result = friendService.getAllFrnds(user.getUserID());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllFrnds_UserNotFound() {
        Exception exception = assertThrows(FriendException.class, () -> {
            friendService.getAllFrnds(999);
        });

        assertEquals("UserId not found", exception.getMessage());
    }

    @Test
    public void testAddFrnd_Success() {
        Users user = new Users();
        user.setFriendsent(new HashSet<>());

        Users friend = new Users();

        userRepo.save(user); 
        userRepo.save(friend); 

        String result = friendService.addFrnd(user.getUserID(), friend.getUserID()); 
        assertEquals("Friend Request Sent Successfully", result);
        assertEquals(1, user.getFriendsent().size());
    }

    @Test
    public void testAddFrnd_FriendshipAlreadyExists() {
        Users user = new Users();
        user.setFriendsent(new HashSet<>());

        Users friend = new Users();

        userRepo.save(user); 
        userRepo.save(friend); 

        friendService.addFrnd(user.getUserID(), friend.getUserID());

        Exception exception = assertThrows(FriendException.class, () -> {
            friendService.addFrnd(user.getUserID(), friend.getUserID());
        });

        assertEquals("FriendShip Already Exists", exception.getMessage());
    }

    @Test
    public void testDeleteFrnd_Success() {
        String result = friendService.deleteFrnd(1, 2);
        assertEquals("Friend Removed Successfully!", result);
    }

    @Test
    public void testDeleteFrnd_FriendshipDoesNotExist() {
        Users user = new Users();
        user.setFriendsent(new HashSet<>());

        userRepo.save(user); 

        Exception exception = assertThrows(FriendException.class, () -> {
            friendService.deleteFrnd(user.getUserID(), 999); 
        });

        assertEquals("UserId or FriendId doesn't exists", exception.getMessage());
    }

    @Test
    public void testDeleteFrnd_UserNotFound() {
        Exception exception = assertThrows(FriendException.class, () -> {
            friendService.deleteFrnd(999, 2); 
        });

        assertEquals("UserId or FriendId doesn't exists", exception.getMessage());
    }
}