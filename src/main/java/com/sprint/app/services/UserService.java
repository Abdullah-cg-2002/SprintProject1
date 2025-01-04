package com.sprint.app.services;

import java.util.List;

import com.sprint.app.model.Likes;
//import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;

public interface UserService
{
	
	
	public void addLikeToPost(int postId, int userId);
	public void removeLikeFromPost(int postId, int userId);
	public List<Likes> getAllLikesPst(int userID);
}