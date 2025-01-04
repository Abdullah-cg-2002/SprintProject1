package com.sprint.app.serviceimpl;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.exception.MessageException;
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
			throw new MessageException("sender or reciever doesn't exists");
		}
	}
	
	/**
	 * @return list of all messages
	 */
	
	public List<Messages> getAllMsgs()
	{
		return mr.findAll();
	}
	/**
	 * @param userID
	 * @return "list of messages of user"
	 */
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
			throw new MessageException("UsersId doesn't exists");
		}
	}
	
	/**
	 * @param messageID
	 * @return specific message 
	 */
	public Messages getSpecificMsg(int messageID)
	{
		Optional<Messages> msgopt = mr.findById(messageID);
		
		if(msgopt.isPresent())
		{
			return msgopt.get();
		}
		else
			throw new MessageException("messageid doesn't Exists");
	}
	
	/**
	 * @param messageID
	 * @param Message
	 * @return success if updated successfully
	 */
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
				throw new MessageException("No changes given");
		}
		else
			throw new MessageException("messageid doesn't Exists");
	}
	
	/**
	 * @param messageID
	 * @return success if deleted
	 */
	public String deleteMsg(int messageID)
	{
		Optional<Messages> msgopt = mr.findById(messageID);
		
		if(msgopt.isPresent())
		{
			Users sender = msgopt.get().getSender();
			Users rec = msgopt.get().getReceiver();
			sender.getSentmsg().remove(msgopt.get());
			rec.getReceivedmsg().remove(msgopt.get());
			mr.deleteById(messageID);
			return "success";
		}
		else
			throw new MessageException("messageid doesn't Exists");
	}

}
