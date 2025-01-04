package com.sprint.app.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.dto.PostsDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.services.PostService;
import com.sprint.app.services.LikesService;
import com.sprint.app.services.UserService;

@Controller
@RequestMapping("/views")
public class PostWebController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private LikesService likesService;


    // Add a Post (Form for adding a post)
    @RequestMapping("/post/add")
    public String showAddPostForm(Model model) {
        PostsDTO post = new PostsDTO();
        model.addAttribute("post", post);
        return "post-add";
    }
//>><< adding 
    @PostMapping("/post/added")  
    public String addPost(@ModelAttribute PostsDTO post, @RequestParam int userID, RedirectAttributes redirectAttributes) {
        // Set the user to the post
        Users user = new Users();
        user.setUserID(userID);
        post.setUser(user);

        // Save the post to the database
        postService.savepost(post);

        // Optionally, you can add a success message in the redirect
        redirectAttributes.addFlashAttribute("message", "Post added successfully!");

        // Redirect to the posts list page, which will reload the list of posts from the database
        return "redirect:/views/posts";
    }

    // View all Posts                >>
    @RequestMapping("/posts") 
    public String viewAllPosts(Model model) {
        List<Posts> posts = postService.getAllPosts(); // Assuming `postService` fetches the posts from the database
        model.addAttribute("posts", posts);  // Pass the posts list to the model

        // Add any additional attributes (like success or error messages) if needed
        return "posts-list";  // This corresponds to the posts-list.html template
    }


    // View a specific Post by ID            >
    @RequestMapping("/post/{postId}")
    public String viewPostById( @PathVariable("postId") int postId, Model model) {
        Posts post = postService.getByPostID(postId);
        model.addAttribute("post", post);
        return "post-view";
    }

    // View Likes for a specific Post             >
    @RequestMapping("/post/{postId}/likes")
    public String viewLikesForPost(@PathVariable("postId") int postId, Model model) {
        List<Likes> likes = postService.getLikesByPostId(postId);
        model.addAttribute("likes", likes);
        return "post-likes";
    }
   
    
    
 // Add a Like to a Post 
    @RequestMapping("/post/{postId}/like/{userId}") 
    public String addLikeToPost(@PathVariable("postId") int postId, 
                                 @PathVariable("userId") int userId) {
        // Add the like to the post
        userService.addLikeToPost(postId, userId);
        System.out.println(userId);

        // Redirect to the post detail page
        return "redirect:/views/post/"+postId;  // Adjust this path to match your application
    }


    
    // Remove a Like from a Post  >
    @RequestMapping("/post/{postId}/like/remove/{likesId}")
    public String removeLikeFromPost(@PathVariable("postId") int postId, @PathVariable("likesId") int likesId, Model model) {
        userService.removeLikeFromPost(postId, likesId);
        return "redirect:/views/post/"+postId;
    }
    
    
 // Delete a Post  >
    @RequestMapping("/post/delete/{postId}")
    public String deletePost(@PathVariable("postId") int postId, Model model) {
        postService.deletePosts(postId);
        return "redirect:/views/posts"; // Redirect to the post view after deletion
    }
    
    
 // Display the update form for the post
    @GetMapping("/post/edit/{postId}")
    public String showUpdatePostForm(@PathVariable("postId") int postId, Model model) {
        // Get the post data by ID
        Posts post = postService.getByPostID(postId);  // Assuming you have a service method to get the post by ID
        model.addAttribute("post", post);  // Add the post data to the model
        return "post-edit";  // Thymeleaf template for the post update form
    }

    @PostMapping("/post/update/{postId}")
    public String updatePosts(@PathVariable("postId") int postId, @ModelAttribute Posts post) {
        // Update the post using a service method
        postService.updatePosts(postId, post);  // Assuming the update method is implemented in the service
        return "redirect:/views/posts";  // Redirect to the posts list after updating
    }


    
    
}

