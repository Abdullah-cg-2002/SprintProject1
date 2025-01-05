package com.sprint.app.serviceimpl;

import java.util.Set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.repo.FriendsRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.FriendService;
import com.sprint.app.services.MessageService;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Status;
import com.sprint.app.model.Users;

@Service
public class FriendServiceImpl implements FriendService
{
	
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
		
		Set<Friends> frds = new HashSet<>();
		
		if(usropt.isPresent())
		{
			frds.addAll(usropt.get().getFriendsent());
			frds.addAll(usropt.get().getFriendsrec());
			return frds;
		}
		
		else
		{
			throw new RuntimeException("UserId not found");
		}
	}
	
	//get all messages between frnds
	public List<Messages> getAllMsgBtwFrnds(int friendshipID)
	{
		Optional<Friends> frdopt = fr.findById(friendshipID);
		
		if(frdopt.isPresent())
		{
			Friends frd = frdopt.get();
			List<Messages> user1 = ms.getMsgSpecificUser(frd.getUser1().getUserID());
			
			List<Messages> chats = new ArrayList<>();
			
			for(Messages m1 : user1)
			{
				if(m1.getReceiver().getUserID() == frd.getUser2().getUserID() || m1.getSender().getUserID() == frd.getUser2().getUserID())
				{
					chats.add(m1);
				}
			}
			
			return chats;
		}
		
		else
		{
			throw new RuntimeException("FriendShip doesn't exists");
		}
	}
	
	//add a friend
	public String addFrnd(int userID, int frdID)
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
			
			if(user.getFriendsent().add(frds))
			{
				fr.save(frds);
				return "Friend Request Sent Successfully";
			}
				
			else
				throw new RuntimeException("FriendShip Already Exists");
			
		}
		else
		{
			throw new RuntimeException("UserId or FriendId not found");
		}
	}
	
	
	//send a msg to the frnd
	public String sendMsg(int friendshipID, Messages msg)
	{
		Optional<Friends> frdopt = fr.findById(friendshipID);
		
		if(frdopt.isPresent())
		{
			Friends frd = frdopt.get();
			msg.setSender(frd.getUser1());
			msg.setReceiver(frd.getUser2());
			
			ms.createMsg(msg);
			return "Message Sent Successfully!";
		}
		else
		{
			throw new RuntimeException("FriendShip doesn't Exists");
		}
	}
	
	//delete a frnd
	public String deleteFrnd(int userID, int frdID)
	{
		Optional<Users> usropt = ur.findById(userID);
		Optional<Users> frdopt = ur.findById(frdID);
		
		if(usropt.isPresent() && frdopt.isPresent())
		{
			Set<Friends> frds = usropt.get().getFriendsent();

			
			for(Friends f : frds)
			{
				if(f.getUser1().getUserID() == frdID || f.getUser2().getUserID() == frdID)
				{
					fr.deleteById(f.getFriendshipID());
					return "Friend Removed Successfully!";
				}
			}
			
			throw new RuntimeException("FriendShip doesn't Exists");	
			
		}
		else
		{
			throw new RuntimeException("UserId or FriendId doesn't exists");
		}
		
	}

}

