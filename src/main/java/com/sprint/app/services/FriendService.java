package com.sprint.app.services;

import java.util.Set;

import com.sprint.app.model.Friends;

public interface FriendService
{
	public Set<Friends> getAllFrnds(int userID);
	public String addFrnd(int userID, int frdID);
	public String deleteFrnd(int userID, int frdID);
}