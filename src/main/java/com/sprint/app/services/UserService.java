package com.sprint.app.services;

import java.util.List;
import com.sprint.app.model.*;

public interface UserService
{
	public List<Users> getAllUsers();
	public Users getSpecificUser(int userID);
	public List<Users> searchForUserByName(String username);
	public void addUser(Users user);
	public void deleteUser(int userID);
	public List<Groups> getAllGroupsofUser(int userID);
	
	
}