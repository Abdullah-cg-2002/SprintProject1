package com.sprint.app.services;

import java.util.List;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;

public interface MessageService{
	
	public String createMsg(MessageDTO msgdto);
	public List<Messages> getAllMsgs();
	public List<Messages> getMsgSpecificUser(int userID);
	public Messages getSpecificMsg(int messageID);
	public String updateMsg(int messageID, Messages msg);
	public String deleteMsg(int messageID);
}
