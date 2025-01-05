package com.sprint.app.controller;

import com.sprint.app.model.Friends;
import com.sprint.app.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendWebController {

    @Autowired
    private FriendService friendService;

    // Display all friends of a specific user
    @GetMapping("/{userID}")
    public String getFriends(@PathVariable int userID, Model model) {
        List<Friends> friends = new ArrayList<>();
        friends.addAll(friendService.getAllFrnds(userID));
        model.addAttribute("friends", friends);
        model.addAttribute("userID", userID);
        return "friends";
    }

    // Add a friend
    @PostMapping("/{userID}/add")
    public String addFriend(@PathVariable int userID, @RequestParam int friendID, Model model) {
        friendService.addFrnd(userID, friendID);
        return "redirect:/friends/" + userID;
    }

    // Delete a friend
    @PostMapping("/{userID}/delete")
    public String deleteFriend(@PathVariable int userID, @RequestParam int friendID, Model model) {
        friendService.deleteFrnd(userID, friendID);
        System.out.println(userID+" "+friendID);
        return "redirect:/friends/" + userID;
    }
	
    @GetMapping("/{userID}/messages/{friendshipID}")
    public String getMessages(@PathVariable int friendshipID, Model model) {
        // Fetch the messages between the two friends using friendshipID
        List<Messages> messages = friendService.getAllMsgBtwFrnds(friendshipID); // Call the service method to fetch messages
        model.addAttribute("messages", messages);  // Pass messages to the Thymeleaf template
        return "messages";  // Return the Thymeleaf view name to show the messages
    }

    // Send a new message between two friends
    @GetMapping("/{userID}/messages/{friendshipID}/send")
    public String sendMessageForm(@PathVariable int userID, @PathVariable int friendshipID, Model model) {
    	model.addAttribute("userID", userID);
        model.addAttribute("friendshipID", friendshipID);
        return "sendMsgFriend"; // This will show the form to send a message
    }
    
    @PostMapping("/{userID}/messages/{friendshipID}/sent")
    public String sendMessage(@PathVariable int userID, @PathVariable int friendshipID, 
                              @RequestParam String messageContent, Model model) {
        // Call the service method to save the new message for the friendshipID
    	MessageDTO messageDto = new MessageDTO();
    	messageDto.setMessage_text(messageContent);
        friendService.sendMsg(friendshipID, messageDto);
   
        // Redirect to the message list page after sending the message
        return "redirect:/friends/{userID}/messages/{friendshipID}";
    }
}
