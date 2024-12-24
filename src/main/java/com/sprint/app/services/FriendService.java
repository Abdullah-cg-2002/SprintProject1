package com.sprint.app.services;

import java.util.List;
import java.util.Set;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Messages;

public interface FriendService
{
	public Set<Friends> getAllFrnds(int userID);
	public List<Messages> getAllMsgBtwFrnds(int friendshipID);
	public String addFrnd(int userID, int frdID);
	public String sendMsg(int friendshipID, MessageDTO msgdto);
	public String deleteFrnd(int userID, int frdID);
}