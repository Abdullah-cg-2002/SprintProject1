package com.sprint.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.exception.CommentsByPostIdNotFoundException;
import com.sprint.app.exception.CommentsNotFoundException;
import com.sprint.app.exception.PostNotFoundException;
import com.sprint.app.exception.UserNotFoundException;
import com.sprint.app.model.Comments;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.CommentRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.CommentService;

/**
 * Service implementation for managing comments in the application.
 * Provides methods to create, update, delete, and retrieve comments.
 */
@Service
public class CommentServiceImpl implements CommentService {
    
    private static final Logger logger = LogManager.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentRepo crepo;

    @Autowired
    private PostRepo prepo;

    @Autowired
    private UserRepo urepo;

    /**
     * Retrieves all comments for a specific post.
     * 
     * @param postID The ID of the post to get comments for.
     * @return A list of comments for the specified post.
     */
    @Override
    public List<Comments> getAllCmts(int postID) {
        Optional<Posts> opt = prepo.findById(postID);
        
        if (opt.isPresent()) {
            Posts post = opt.get();
            logger.info("Retrieved comments for post ID: " + postID);
            return post.getComments();
        }
        
        logger.warn("No post found with ID: " + postID);
        throw new PostNotFoundException("Post not found with ID: " + postID);
    }

    /**
     * Retrieves all comments from the database.
     * 
     * @return A list of all comments.
     */
    @Override
    public List<Comments> getAllCmts() {
        logger.info("Retrieving all comments from the database.");
        return crepo.findAll();
    }

    /**
     * Retrieves a comment by its ID.
     * 
     * @param commentID The ID of the comment to retrieve.
     * @return The comment with the specified ID, or null if not found.
     */
    @Override
    public Comments getCmtsBYId(int commentID) {
        Optional<Comments> copt = crepo.findById(commentID);
        if (copt.isPresent()) {
            logger.info("Retrieved comment with ID: " + commentID);
            return copt.get();
        }
        logger.warn("No comment found with ID: " + commentID);
        throw new CommentsNotFoundException("Comment not found with ID: " + commentID);
    }

    /**
     * Creates a new comment and associates it with a user and a post.
     * 
     * @param cmts The comment to create.
     */
    @Override
    public void createCmt(Comments cmts) {
        Optional<Users> usropt = urepo.findById(cmts.getUsers().getUserID());
        
        if (usropt.isPresent()) {
            Users user = usropt.get();
            Optional<Posts> pstopt = prepo.findById(cmts.getPost().getPostID());
            
            if (pstopt.isPresent()) {
                Posts post = pstopt.get();
                cmts.setTimestamp(LocalDateTime.now());
                post.getComments().add(cmts);
                user.getComments().add(cmts);
                crepo.save(cmts);
                logger.info("Comment created successfully for post ID: " + post.getPostID());
            } else {
                logger.error("Post with ID " + cmts.getPost().getPostID() + " not found.");
                throw new PostNotFoundException("Post with ID " + cmts.getPost().getPostID() + " not found.");
            }
        } else {
            logger.error("User with ID " + cmts.getUsers().getUserID() + " not found.");
            throw new UserNotFoundException("User with ID " + cmts.getUsers().getUserID() + " not found.");
        }
    }

  
   

    /**
     * Deletes a comment by its ID.
     * 
     * @param commentID The ID of the comment to delete.
     */
    @Override
    public void deleteCmtById(int commentID) {
        Optional<Comments> cmtopt = crepo.findById(commentID);
        if (cmtopt.isPresent()) {
            Comments cmt = cmtopt.get();
            Posts post = cmt.getPost();
            Users user = cmt.getUsers();
            
            post.getComments().remove(cmt);
            user.getComments().remove(cmt);
            crepo.deleteById(commentID);
            logger.info("Comment with ID " + commentID + " deleted successfully.");
        } else {
            logger.warn("No comment found with ID: " + commentID);
            throw new CommentsNotFoundException("Comment not found with ID: " + commentID);
        }
    }

    /**
     * Adds a comment to a specific post by its ID.
     * 
     * @param postID The ID of the post to add the comment to.
     * @param cmts The comment to add.
     * @return A success message if the comment is added.
     */
    @Override
    public String addCmtSpecificPost(int postID, Comments cmts) {
        Optional<Posts> pstopt = prepo.findById(postID);
        
        if (pstopt.isPresent()) {
            Posts post = pstopt.get();
            cmts.setUsers(post.getUser());
            cmts.setTimestamp(LocalDateTime.now());
            cmts.setPost(post);
            crepo.save(cmts);
            logger.info("Comment added to post with ID: " + postID);
            return "Comment added successfully";
        } else {
            logger.error("Post with ID " + postID + " not found.");
            throw new PostNotFoundException("PostID does not exist: " + postID);
        }
    }
    public List<Comments> findCommentsByPostId(int postID) {
        return crepo.findByPost_PostID(postID); // Assuming you have a method like this in your repository
    }

    /**
     * Deletes a comment for a specific post by post ID and comment ID.
     * 
     * @param postID The ID of the post.
     * @param commentID The ID of the comment to delete.
     */
    @Override
    public void deleteCmtSpecificPost(int postID, int commentID) {
        Optional<Posts> pstopt = prepo.findById(postID);
        Optional<Comments> copt = crepo.findById(commentID);
        
        if (pstopt.isPresent() && copt.isPresent()) {
            Posts post = pstopt.get();
            Users user = post.getUser();
            Comments comment = copt.get();
            
            user.getComments().remove(comment);
            post.getComments().remove(comment);
            crepo.deleteById(commentID);
            logger.info("Comment with ID " + commentID + " deleted from post ID: " + postID);
        } else {
            logger.warn("No post or comment found with the provided IDs: PostID=" + postID + ", CommentID=" + commentID);
            throw new PostNotFoundException("No post found with ID " + postID);
        }
    }

	@Override
	public void updateCmt(int commentID, Comments cmts) {
		Optional<Comments> copt = crepo.findById(commentID);
		if(copt.isPresent())
		{
			Comments existingcmts = copt.get();
		
			if(cmts.getCommentText()!=null)
			{
				existingcmts.setCommentText(cmts.getCommentText());
				existingcmts.setTimestamp(LocalDateTime.now());
				crepo.save(existingcmts);
				logger.info("Comments with ID"+commentID+"updated successfully");
			}
			else
			{
				logger.warn("Comment ID is not found"+commentID);
				throw new CommentsNotFoundException("Comment Not found with the ID"+commentID);
			}
		}
		
	}
}
