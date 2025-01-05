package com.sprint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Users;
import com.sprint.app.services.UserService;

/**
 * ThymeleafUserController handles HTTP requests for rendering views 
 * related to user management and groups using Thymeleaf.
 */
@Controller
@RequestMapping("/users")
public class UserWebController {

    private static final Logger logger = LoggerFactory.getLogger(UserWebController.class);

    @Autowired
    private UserService userService;

    /**
     * Displays all users in the "users.html" view.
     *
     * @param model Model object to add attributes for rendering.
     * @return The "users" template view.
     */
    @GetMapping
    public String getAllUsers(Model model) {
        logger.info("Fetching all users for Thymeleaf view");
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users", users); 
        logger.info("Loaded all users into model");
        return "users"; 
    }

    /**
     * Displays details of a specific user in the "user-details.html" view.
     *
     * @param userID ID of the user to fetch details for.
     * @param model Model object for rendering data.
     * @return The "user-details" template view.
     */
    @GetMapping("/{userID}")
    public String getSpecificUser(@PathVariable int userID, Model model) {
        logger.info("Fetching specific user details for Thymeleaf view");
        Users user = userService.getSpecificUser(userID);
        model.addAttribute("user", user); 
        logger.info("Loaded specific user details into model");
        return "user-details"; 
    }

    /**
     * Displays groups associated with a specific user in "user-groups.html".
     *
     * @param userID ID of the user.
     * @param model Model object for rendering groups.
     * @return The "user-groups" template view.
     */
    @GetMapping("/{userID}/groups")
    public String getAllGroupsOfUser(@PathVariable int userID, Model model) {
        logger.info("Fetching groups for user with ID: " + userID);
        Users user = userService.getSpecificUser(userID);
        model.addAttribute("groups", user.getGroups());
        model.addAttribute("username", user.getUsername());
        logger.info("Loaded groups into model for user-groups view");
        return "user-groups"; 
    }

    /**
     * Handles form submission for adding a new user.
     *
     * @param user User object populated from the form.
     * @return Redirects to the users page after adding.
     */
    @GetMapping("/add")
    public String setUser(Model model)
    {
    	Users user = new Users();
    	model.addAttribute("user", user);
    	return "addUser";
    }
    @PostMapping("/added")
    public String addUser(@ModelAttribute Users user) {
        logger.info("Adding a new user via form");
        userService.addUser(user);
        logger.info("User added successfully");
        return "redirect:/users"; 
    }

    /**
     * Deletes a user based on their ID and redirects to the users page.
     *
     * @param userID ID of the user to delete.
     * @return Redirects to the users list after deletion.
     */
    @GetMapping("/delete/{userID}")
    public String deleteUser(@PathVariable int userID) {
        logger.info("Deleting user with ID: " + userID);
        userService.deleteUser(userID);
        logger.info("User deleted successfully");
        return "redirect:/users"; 
    }

    /**
     * Searches for users by username and displays results in "users.html".
     *
     * @param username Username to search for.
     * @param model Model object to add results.
     * @return The "users" template with search results.
     */
    @GetMapping("/search")
    public String searchUsers(@RequestParam String username, Model model) {
        logger.info("Searching for users with username: " + username);
        List<Users> users = userService.searchForUserByName(username);
        model.addAttribute("users", users); 
        logger.info("Search results added to model");
        return "users"; 
    }
	
	//send msg to frnd
	@GetMapping("/send/message/friend")
	public String getDetailsForSendMsg(Model m)
	{
		MessageDTO messageDto = new MessageDTO();
		m.addAttribute("messageDto", messageDto);
		return "formSendMessage";
	}
	
	@PostMapping("/send/message/post")
	public String sendMsgToOtherUser(@RequestParam int userID, @RequestParam int frdID, @ModelAttribute MessageDTO messageDto)
	{
		userService.sendMsgFrnd(userID, frdID, messageDto);
		return "redirect:/users/send/message/friend";
	}
	
	//send friend request
	@GetMapping("/send/friend-req")
	public String sendFrndReq(Model m)
	{
		return "sendFrndReq";
	}
	
	@PostMapping("/sent/friend-req")
	public String frndReqSent(@RequestParam int userID, @RequestParam int frdID)
	{
		userService.sendFrdReq(userID, frdID);
		return "redirect:/users/send/friend-req";
	}
	
	//get msgs between users
	@GetMapping("/{userID}/messages/{otherID}")
	public String msgsBtwnUsers(@PathVariable int userID, @PathVariable int otherID, Model m)
	{
		List<Messages> messages = userService.msgBtwUsers(userID, otherID);
		m.addAttribute("messages", messages);
		return "msgBtwnUsers";
	}
	
	//get all likes for a user
	@GetMapping("/{userID}/posts/likes")
	public String getAllLikesUserGot(@PathVariable int userID, Model m)
	{
		List<Likes> likes = userService.getAllLikesPst(userID);
		m.addAttribute("likes", likes);
		return "getLikesUser";
	}
	
	//get all likes user likes
	@GetMapping("/{userID}/likes")
	public String getAllLikesByUser(@PathVariable int userID, Model m)
	{
		List<Likes> likes = userService.getAllLikesUsr(userID);
		m.addAttribute("likes", likes);
		return "getLikesUser";
	}

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
