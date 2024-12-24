package com.sprint.app.services;

import java.util.List;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Messages;

public interface MessageService{
	
	public void createMsg(MessageDTO msgdto);
	public List<Messages> getAllMsgs();
	public List<Messages> getMsgSpecificUser(int userID);
	public Messages getSpecificMsg(int messageID);
	public void updateMsg(int messageID, Messages msg);
	public void deleteMsg(int messageID);
}
