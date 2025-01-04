package com.sprint.app.services;

import java.util.List;
import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;

public interface UserService
{
	
	public void sendMsgFrnd(int userID, int frdID);
	public void sendFrdReq(int userID, int frdID);
	public List<Likes> getAllLikesPst(int userID);
	public List<Messages> msgBtwUsers(int userID, int otherID);
	public List<Likes> getAllLikesUsr(int userID);
	public List<Users> getAllUsers();
	public Users getSpecificUser(int userID);
	public List<Users> searchForUserByName(String username);
	public void addUser(Users user);
	public void deleteUser(int userID);
	public List<Groups> getAllGroupsofUser(int userID);
	public void addLikeToPost(int postId, int userId);
	public void removeLikeFromPost(int postId, int userId);

}