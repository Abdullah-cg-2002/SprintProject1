package com.sprint.app.services;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.repo.FriendsRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Status;
import com.sprint.app.model.Users;

@Service
public class FriendService {
	
	@Autowired
	private FriendsRepo fr;
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private MessageService ms;
	
	//get friends of a user
	public Set<Friends> getAllFrnds(int userID)
	{
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			return usropt.get().getFriends();
		}
		
		return null;
	}
	
	//get all messages between frnds
	public List<Messages> getAllMsgBtwFrnds(int friendshipID)
	{
		Optional<Friends> frdopt = fr.findById(friendshipID);
		
		if(frdopt.isPresent())
		{
			Friends frd = frdopt.get();
			List<Messages> user1 = ms.getMsgSpecificUser(frd.getUser1().getUserID());
			List<Messages> user2 = ms.getMsgSpecificUser(frd.getUser2().getUserID());
			
			List<Messages> chats = new ArrayList<>();
			
			for(Messages m1 : user1)
			{
				if(m1.getReceiver().getUserID() == frd.getUser2().getUserID())
				{
					System.out.println(m1.getMessage_text());
					chats.add(m1);
				}
			}
			
			for(Messages m2 : user2)
			{
				if(m2.getReceiver().getUserID() == frd.getUser1().getUserID())
				{
					System.out.println("inside user 2 :"+m2.getMessage_text());
					chats.add(m2);
				}
			}
			
			return chats;
		}
		
		return null;
	}
	
	//add a friend
	public void addFrnd(int userID, int frdID)
	{
		Optional<Users> usropt = ur.findById(userID);
		Optional<Users> frdopt = ur.findById(frdID);
		
		if(usropt.isPresent() && frdopt.isPresent())
		{
			Users user = usropt.get();
			Users frd = frdopt.get();
			
			Friends frds = new Friends();
			frds.setStatus(Status.PENDING);
			frds.setUser1(user);
			frds.setUser2(frd);
			
			user.getFriends().add(frds);
			fr.save(frds);
		}
	}
	
	
	//send a msg to the frnd
	public void sendMsg(int friendshipID, Messages msg)
	{
		Optional<Friends> frdopt = fr.findById(friendshipID);
		
		if(frdopt.isPresent())
		{
			Friends frd = frdopt.get();
			msg.setSender(frd.getUser1());
			msg.setReceiver(frd.getUser2());
			
			ms.createMsg(msg);
		}
	}
	
	//delete a frnd
	public void deleteFrnd(int userID, int frdID)
	{
		Optional<Users> usropt = ur.findById(userID);
		Optional<Users> frdopt = ur.findById(frdID);
		
		if(usropt.isPresent() && frdopt.isPresent())
		{
			Set<Friends> frds = usropt.get().getFriends();
			
			
			
			for(Friends f : frds)
			{
				if(f.getUser1().getUserID() == frdID)
				{
					System.out.println("removed");
					fr.deleteById(f.getFriendshipID());
				}
			}
		}
	}

}
