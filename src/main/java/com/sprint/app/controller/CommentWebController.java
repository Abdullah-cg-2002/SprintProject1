package com.sprint.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.aspectj.util.LangUtil.ProcessController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sprint.app.services.CommentService;
import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.dto.CommentDTO;
import com.sprint.app.model.Comments;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.CommentRepo;
import com.sprint.app.repo.PostRepo;

@Controller
@RequestMapping("/comments")
public class CommentWebController {

    @Autowired
    private CommentService cserv;
    
    private UserService userv;
    
    private PostRepo prepo;
    
    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    /**
     * Show all comments from the system.
     *
     * @param model the model to pass data to the view
     * @return the name of the Thymeleaf template (HTML file)
     */
    @GetMapping("/")
    public String getAllComments(Model model) {
        model.addAttribute("comments", cserv.getAllCmts());  // Fetch all comments
        return "commentsList";  // Thymeleaf template name for displaying all comments
    }

    /**
     * Show all comments for a specific post.
     *
     * @param postID the ID of the post to fetch comments for
     * @param model  the model to pass data to the view
     * @return the name of the Thymeleaf template (HTML file)
     */
    @GetMapping("/post/{postID}")
    public String getCommentsForPost(@PathVariable int postID, Model model) {
        model.addAttribute("comments", cserv.getAllCmts(postID));  // Fetch comments for the specific post
        model.addAttribute("postID", postID);  // Pass postID for future operations
        return "commentsForPost";  // Thymeleaf template for displaying comments of a specific post
    }

    /**
     * Show a specific comment by its ID.
     *
     * @param commentID the ID of the comment to retrieve
     * @param model     the model to pass data to the view
     * @return the name of the Thymeleaf template (HTML file)
     */
    @GetMapping("/{commentID}")
    public String getCommentById(@PathVariable int commentID, Model model) {
        Comments comment = cserv.getCmtsBYId(commentID);  // Fetch a specific comment by ID
        model.addAttribute("comment", comment);
        return "viewComment";  // Thymeleaf template for viewing a single comment
    }
    
    
    /**
     * Displays the form for creating a new comment on a specific post.
     * 
     * @param postID the ID of the post
     * @param model the model to be passed to the view
     * @return the Thymeleaf view name for creating a comment
     */
    @GetMapping("/post/{postID}/new")
    public String createCommentForm(@PathVariable int postID, Model model) {
        Comments newComment = new Comments();
        model.addAttribute("comment", newComment);
        model.addAttribute("postID", postID);
        return "create"; 
    }

    /**
     * Creates a new comment for a post.
     * 
     * @param postID the ID of the post
     * @param comment the comment data from the form
     * @return redirect to the post's comment list page
     */
    @PostMapping("/post/{postID}/create")
    public String createComment(@PathVariable int postID, @ModelAttribute("comment") Comments comment) {
        cserv.addCmtSpecificPost(postID, comment);
        return "redirect:/comments/";
    }
    

        // Delete a comment by its ID
     // Delete a comment by its ID
        @RequestMapping("/comments/delete/{commentID}")
        public String deleteComment(@PathVariable("commentID") int commentID, Model model) {
           // logger.info("Received request to delete comment with ID: {}", commentID);
            cserv.deleteCmtById(commentID);  // Calling the service to delete the comment
            //logger.info("Comment with ID: {} deleted successfully", commentID);
            
            model.addAttribute("message", "Comment successfully deleted!");
            return "redirect:/comments/";  // Redirecting to the list of comments page (e.g., /comments)
        }
    
      
      
      @GetMapping("/comment/edit/{commentID}")
      public String showUpdateCommentForm(@PathVariable("commentID") int commentID, Model model) {
          // Fetch the comment by ID
          Comments comment = cserv.getCmtsBYId(commentID);  
          model.addAttribute("comment", comment); 
          return "comment-edit";  
      }

      // Handle the form submission for updating the comment
      @PostMapping("/comment/update/{commentID}")
      public String updateComment(@PathVariable("commentID") int commentID, @ModelAttribute Comments comment) {
          cserv.updateCmt(commentID, comment);  
          return "redirect:/comments/";  
      }

   

          // Delete comment from a specific post
          @RequestMapping("/posts/{postID}/comments/{commentID}/delete")
          public String deleteCommentFromPost(@PathVariable("postID") int postID, 
                                              @PathVariable("commentID") int commentID, 
                                              Model model) {
             logger.info("Received request to delete comment with ID: {} from post with ID: {}", commentID, postID);
              
              
              cserv.deleteCmtSpecificPost(postID, commentID);
              
              logger.info("Comment with ID: {} deleted from post with ID: {}", commentID, postID);

              
              model.addAttribute("message", "Comment successfully deleted!");
              
              
              return "redirect:/comments/" ;
          }
          
          
          @GetMapping("/create")
          public String showCreateCommentForm(Model model) {
              // Create a new empty comment to bind the form fields
              model.addAttribute("comment", new Comments());
              return "create-comment";
          }

          @PostMapping("/create")
          @ResponseBody
          public SuccessResponse createComment(@ModelAttribute Comments comment) {
              // This is a simplified version where the comment will be processed here.
              // You can save it to the database if needed. Here we will just simulate it.

              // Set timestamp for when the comment was created
              comment.setTimestamp(LocalDateTime.now());
              cserv.createCmt(comment);
              // Simulate saving the comment (no actual database call in this version)
              SuccessResponse response = new SuccessResponse();
              response.setMessage("Comment added successfully!");
              response.setStatus("Success");

              return response;
          }
      }
          
          
          
       
      

      
      
  
      
      
      
      
      

    

    
   

      
    

    
    

        
       
    
        
    

   

    
    
    
    

