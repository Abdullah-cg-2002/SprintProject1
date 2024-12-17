package com.sprint.app.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.repo.MessageRepo;
import com.sprint.app.repo.UserRepo;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepo mr;
	
	@Autowired
	private UserRepo ur;
	
	//create a message
	public void createMsg(Messages msg)
	{
		Optional<Users> sendopt = ur.findById(msg.getSender().getUserID());
		Optional<Users> recopt = ur.findById(msg.getReceiver().getUserID());
		
		if(sendopt.isPresent() && recopt.isPresent())
		{
			Users sender = sendopt.get();
			Users rec = recopt.get();
			msg.setTimestamp(LocalDateTime.now());
			sender.getMessages().add(msg);
			rec.getMessages().add(msg);
			
			ur.save(sender);
			ur.save(rec);
			mr.save(msg);
			
		}
	}
	
	//get all msgs
	public List<Messages> getAllMsgs()
	{
		return mr.findAll();
	}
	
	
	//get All messages of a user
	public List<Messages> getMsgSpecificUser(int userID)
	{
		Users usr = ur.findById(userID).get();
		
		return usr.getMessages();
	}
	
	//get msg using id
	public Messages getSpecificMsg(int messageID)
	{
		Optional<Messages> msgopt = mr.findById(messageID);
		
		if(msgopt.isPresent())
		{
			return msgopt.get();
		}
		
		return null;
	}
	
	//update message
	public void updateMsg(int messageID, Messages msg)
	{
		Optional<Messages> msgopt = mr.findById(messageID);
		
		if(msgopt.isPresent())
		{
			Messages exmsg = msgopt.get();
			
			if(msg.getMessage_text()!=null)
			{
				exmsg.setMessage_text(msg.getMessage_text());
				exmsg.setTimestamp(LocalDateTime.now());
				mr.save(exmsg);
			}
		}
	}
	
	//delete specific msg
	public void deleteMsg(int messageID)
	{
		Optional<Messages> msgopt = mr.findById(messageID);
		
		if(msgopt.isPresent())
		{
			Users sender = msgopt.get().getSender();
			sender.getMessages().remove(msgopt.get());
			ur.save(sender);
			mr.deleteById(messageID);
		}
	}

}
