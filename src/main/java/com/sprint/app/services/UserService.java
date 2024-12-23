package com.sprint.app.services;

import java.util.List;

import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;

public interface UserService
{
	public void sendMsgFrnd(int userID, int frdID);
	public void sendFrdReq(int userID, int frdID);
	public List<Likes> getAllLikesPst(int userID);
	public List<Messages> msgBtwUsers(int userID, int otherID);
	public List<Likes> getAllLikesUsr(int userID);
}