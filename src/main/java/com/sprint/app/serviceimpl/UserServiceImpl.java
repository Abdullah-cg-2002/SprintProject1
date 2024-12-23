package com.sprint.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.FriendService;
import com.sprint.app.services.MessageService;
import com.sprint.app.services.UserService;

@Service
public class UserServiceImpl implements UserService
{
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private MessageService ms;
	
	@Autowired
	private FriendService fs;
	
	//send msg to the frnd
	public void sendMsgFrnd(int userID, int frdID) {
		Optional<Users> usropt = ur.findById(userID);
		Optional<Users> frdopt = ur.findById(frdID);
		
		if(usropt.isPresent() && frdopt.isPresent())
		{
			Messages msg = new Messages();
			msg.setMessage_text("Hello, How are you?");
			msg.setReceiver(frdopt.get());
			msg.setSender(usropt.get());
			ms.createMsg(msg);
		}
	}
	
	//send a frnd request
	public void sendFrdReq(int userID, int frdID)
	{
		fs.addFrnd(userID, frdID);
	}
	
	//msg between 2 users
	public List<Messages> msgBtwUsers(int userID, int otherID)
	{
		Optional<Users> usropt = ur.findById(userID);
		Optional<Users> othopt = ur.findById(otherID);
		
		List<Messages> chats = new ArrayList<>();
		
		if(usropt.isPresent() && othopt.isPresent())
		{
			for(Messages mgs : ms.getMsgSpecificUser(userID))
			{
				if(mgs.getReceiver().getUserID() == otherID || mgs.getSender().getUserID() == otherID)
					chats.add(mgs);
			}
			
			return chats;
		}
		
		else
		{
			return null;
		}
		
		
	}
	
	//get all likes get by user on all posts
	public List<Likes> getAllLikesPst(int userID)
	{
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			List<Likes> likes = new ArrayList<>();
			Users usr = usropt.get();
			for(Posts pst : usr.getPosts())
			{
				likes.addAll(pst.getLikes());
			}
			
			return likes;
		}
		
		return null;
	}
	
	//get all likes done by a user
	public List<Likes> getAllLikesUsr(int userID)
	{
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			return usropt.get().getLikes();
		}
		
		return null;
	}
	

}
