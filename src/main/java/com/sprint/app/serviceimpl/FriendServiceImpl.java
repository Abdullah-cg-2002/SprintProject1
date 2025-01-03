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
import com.sprint.app.dto.MessageDTO;
import com.sprint.app.exception.FriendException;
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
	
	/**
	 * @param userID
	 * @return set of user for a user
	 */
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
			throw new FriendException("UserId not found");
		}
	}
	
	/**
	 * @param friendshipID
	 * @return list of messages between friends
	 */
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
			throw new FriendException("FriendShip doesn't exists");
		}
	}
	
	/**
	 * @param userID
	 * @param frdID
	 * @return success if friend request is sent to the friend
	 */
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
				throw new FriendException("FriendShip Already Exists");
			
		}
		else
		{
			throw new FriendException("UserId or FriendId not found");
		}
	}
	
	
	/**
	 * @param friendshipID
	 * @param MessageDTO
	 * @return success message if message sent
	 */
	public String sendMsg(int friendshipID, MessageDTO msgdto)
	{
		Optional<Friends> frdopt = fr.findById(friendshipID);
		
		if(frdopt.isPresent())
		{
			Friends frd = frdopt.get();
			msgdto.setSender(frd.getUser1());
			msgdto.setReceiver(frd.getUser2());
			
			ms.createMsg(msgdto);
			return "Message Sent Successfully!";
		}
		else
		{
			throw new FriendException("FriendShip doesn't Exists");
		}
	}

}

