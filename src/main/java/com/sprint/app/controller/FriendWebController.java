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
}
