package com.sprint.app.services;

import java.util.List;
import java.util.Set;

import com.sprint.app.model.Comments;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Posts;

/**
 * Service interface for handling user-related operations,
 * including sending messages, friend requests, managing posts, comments, likes, and retrieving user-related data.
 */
public interface UserService {

    /**
     * Sends a friend request from one user to another.
     * 
     * @param userID The ID of the user sending the friend request.
     * @param frdID The ID of the friend to whom the request is sent.
     */
      //public void sendMsgFrnd(int userID, int frdID);

    /**
     * Sends a friend request from one user to another.
     * 
     * @param userID The ID of the user sending the friend request.
     * @param frdID The ID of the friend to whom the request is sent.
     */
     //public void sendFrdReq(int userID, int frdID);

    /**
     * Retrieves all the likes on posts made by a specific user.
     * 
     * @param userID The ID of the user whose post likes are to be retrieved.
     * @return A list of likes associated with the user's posts.
     */
   // public List<Likes> getAllLikesPst(int userID);

    /**
     * Retrieves all messages exchanged between two users.
     * 
     * @param userID The ID of the first user.
     * @param otherID The ID of the second user.
     * @return A list of messages exchanged between the two users.
     */
    //public List<Messages> msgBtwUsers(int userID, int otherID);

    /**
     * Retrieves all likes by a specific user on various posts.
     * 
     * @param userID The ID of the user whose likes are to be retrieved.
     * @return A list of likes made by the user.
     */
   // public List<Likes> getAllLikesUsr(int userID);

    /**
     * Retrieves all posts made by a specific user.
     * 
     * @param userID The ID of the user whose posts are to be retrieved.
     * @return A list of posts made by the user.
     */
    public List<Posts> getAllPostsUsr(int userID);

    /**
     * Retrieves all comments on posts made by a specific user.
     * 
     * @param userID The ID of the user whose post comments are to be retrieved.
     * @return A list of comments made on the user's posts.
     */
    public List<Comments> getAllCmtsPst(int userID);

    /**
     * Retrieves all pending friend requests for a specific user.
     * 
     * @param userID The ID of the user for whom the pending friend requests are to be retrieved.
     * @return A list of pending friend requests (either sent or received).
     */
    public List<Object> getPendingFrndReq(int userID);
}
