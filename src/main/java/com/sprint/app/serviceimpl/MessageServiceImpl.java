package com.sprint.app.serviceimpl;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.repo.MessageRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.MessageService;
import com.sprint.app.services.NotificationService;

@Service
public class MessageServiceImpl implements MessageService
{

	private MessageRepo mr;
	private UserRepo ur;
	private NotificationService ns;
	
	public MessageServiceImpl(MessageRepo mr, UserRepo ur, NotificationService ns)
	{
		this.mr = mr;
		this.ur = ur;
		this.ns = ns;
	}
	
	//create a message
	public String createMsg(MessageDTO msgdto)
	{
		Messages msg = new Messages();
		msg.setMessage_text(msgdto.getMessage_text());
		msg.setReceiver(msgdto.getReceiver());
		msg.setSender(msgdto.getSender());
		Optional<Users> sendopt = ur.findById(msg.getSender().getUserID());
		Optional<Users> recopt = ur.findById(msg.getReceiver().getUserID());
		
		if(sendopt.isPresent() && recopt.isPresent())
		{
			Users sender = sendopt.get();
			Users rec = recopt.get();
			msg.setTimestamp(LocalDateTime.now());
			sender.getSentmsg().add(msg);
			rec.getReceivedmsg().add(msg);
			mr.save(msg);
			
			ns.createNotif(rec.getUserID());
			return "success";
		}
		
		else
		{
			throw new RuntimeException("sender or reciever doesn't exists");
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
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			Users usr = usropt.get();
			List<Messages> msgs = new ArrayList<>();
			msgs.addAll(usr.getSentmsg());
			msgs.addAll(usr.getReceivedmsg());
			return msgs;
		}
		else
		{
			throw new RuntimeException("UsersId doesn't exists");
		}
	}
	
	//get msg using id
	public Messages getSpecificMsg(int messageID)
	{
		Optional<Messages> msgopt = mr.findById(messageID);
		
		if(msgopt.isPresent())
		{
			return msgopt.get();
		}
		else
			throw new RuntimeException("messageid doesn't Exists");
	}
	
	//update message
	public String updateMsg(int messageID, Messages msg)
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
				return "success";
			}
			else
				throw new RuntimeException("No changes given");
		}
		else
			throw new RuntimeException("messageid doesn't Exists");
	}
	
	//delete specific msg
	public String deleteMsg(int messageID)
	{
		Optional<Messages> msgopt = mr.findById(messageID);
		
		if(msgopt.isPresent())
		{
			Users sender = msgopt.get().getSender();
			Users rec = msgopt.get().getReceiver();
			sender.getSentmsg().remove(msgopt.get());
			rec.getReceivedmsg().remove(msgopt.get());
//			ur.save(sender);
			mr.deleteById(messageID);
			return "success";
		}
		else
			throw new RuntimeException("messageid doesn't Exists");
	}

}
