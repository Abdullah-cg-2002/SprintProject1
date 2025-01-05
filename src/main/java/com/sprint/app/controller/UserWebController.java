package com.sprint.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sprint.app.services.UserService;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Comments;
import com.sprint.app.model.Friends;

import java.util.List;

@Controller
@RequestMapping("/views")
public class UserWebController {

    @Autowired
    private UserService us;

    /**
     * Fetches all posts created by a specific user and returns a view to display them.
     * 
     * @param userID The unique identifier of the user whose posts are to be fetched.
     * @return A view name that displays the user's posts.
     */
    @GetMapping("/users/{userID}/posts")
    public String getAllPostsByUser(@PathVariable int userID, Model model) {
        List<Posts> posts = us.getAllPostsUsr(userID);
        model.addAttribute("posts", posts);
        return "posts";  
    }

    /**
     * Fetches all comments on posts created by a specific user and returns a view to display them.
     * 
     * @param userID The unique identifier of the user whose post comments are to be fetched.
     * @return A view name that displays the comments on the user's posts.
     */
    @GetMapping("/users/{userID}/posts/comments")
    public String getAllCommentsOnPosts(@PathVariable int userID, Model model) {
        List<Comments> comments = us.getAllCmtsPst(userID);
        model.addAttribute("comments", comments);
        return "comments";  // Return the view 'comments.html'
    }
    
    /**
     * Fetches all pending friend requests for a specific user.
     * 
     * @param userID the ID of the user whose pending friend requests are to be retrieved.
     * @return A view name that displays the pending friend requests.
     */
    @GetMapping("/users/{userID}/friend-requests/pending")
    public String getPendingFriendRequests(@PathVariable int userID, Model model) {
        List<Object> pendingRequests = us.getPendingFrndReq(userID);
        model.addAttribute("pendingRequests", pendingRequests);
        return "pendingRequests";  // Return the view 'pendingRequests.html'
    }
}
