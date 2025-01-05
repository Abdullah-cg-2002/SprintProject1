package com.sprint.app.serviceimpl;

import java.util.Set;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sprint.app.repo.FriendsRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.FriendService;
import com.sprint.app.services.MessageService;
import com.sprint.app.dto.MessageDTO;
import com.sprint.app.exception.FriendException;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Status;
import com.sprint.app.model.Users;

/**
 * Implementation of FriendService to manage user friendships.
 * Provides methods to get friends, add a friend, and delete a friend.
 */
@Service
public class FriendServiceImpl implements FriendService
{
	private static final Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);
	
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
		logger.info("Fetching all friends for user");
		Optional<Users> usropt = ur.findById(userID);
		Set<Friends> frds = new HashSet<>();
		
		if(usropt.isPresent())
		{
			frds.addAll(usropt.get().getFriendsent());
			frds.addAll(usropt.get().getFriendsrec());
			logger.info("Friends fetched successfully for user");
			return frds;
		}
		else
		{
			logger.error("User ID not found");
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
		logger.info("Adding friend request");
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
				logger.info("Friend request sent successfully");
				return "Friend Request Sent Successfully";
			}
			else
			{
				logger.warn("Friendship already exists");
				throw new FriendException("FriendShip Already Exists");
			}
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
	
	/**
	 * Deletes a friend for a given user.
	 * @param userID The ID of the user.
	 * @param frdID The ID of the friend to remove.
	 * @return A message indicating success or failure.
	 */
	public String deleteFrnd(int userID, int frdID)
	{
		logger.info("Deleting friend ");
		Optional<Users> usropt = ur.findById(userID);
		Optional<Users> frdopt = ur.findById(frdID);
		
		if(usropt.isPresent() && frdopt.isPresent())
		{
			Set<Friends> frdsent = usropt.get().getFriendsent();
			Set<Friends> frdrec = usropt.get().getFriendsrec();
			
			boolean found = false;
			
			System.out.println(frdsent.size());

			for(Friends f : frdsent)
			{
				if(f.getUser2().getUserID() == frdID)
				{
					System.out.println("deleted");
					f.getUser1().getFriendsent().remove(f);
					f.getUser2().getFriendsrec().remove(f);
					fr.deleteById(f.getFriendshipID());
					logger.info("Friend removed successfully");
					found=true;
					break;
				}
			}
			
			for(Friends f : frdrec)
			{
				if(f.getUser1().getUserID() == frdID)
				{
					System.out.println("deleted");
					f.getUser1().getFriendsrec().remove(f);
					f.getUser2().getFriendsent().remove(f);
					fr.deleteById(f.getFriendshipID());
					found=true;
					logger.info("Friend removed successfully");
					break;
				}
			}
			
			if(!found)
				throw new FriendException("FriendShip doesn't Exists");
			else
			{
				return "success";
			}
			
		}
		else
		{
			logger.error("User ID or Friend ID does not exist");
			throw new FriendException("UserId or FriendId doesn't exists");
		}
	}
}
