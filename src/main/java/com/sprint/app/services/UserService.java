package com.sprint.app.services;

import java.util.List;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;

public interface UserService
{
	public String sendMsgFrnd(int userID, int frdID, MessageDTO msgdto);
	public String sendFrdReq(int userID, int frdID);
	public List<Likes> getAllLikesPst(int userID);
	public List<Messages> msgBtwUsers(int userID, int otherID);
	public List<Likes> getAllLikesUsr(int userID);
}