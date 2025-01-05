package com.sprint.app.services;

import java.util.List;

import com.sprint.app.model.Comments;

/**
 * Service interface for handling operations related to comments.
 * Provides methods to get, create, update, and delete comments, as well as manage comments on specific posts.
 */
public interface CommentService {

    /**
     * Retrieves all comments for a specific post.
     * 
     * @param postID The ID of the post whose comments are to be retrieved.
     * @return A list of comments on the specified post, or an empty list if no comments are found.
     */
    public List<Comments> getAllCmts(int postID);

    /**
     * Retrieves all comments across all posts.
     * 
     * @return A list of all comments in the system, or an empty list if no comments are found.
     */
    public List<Comments> getAllCmts();

    /**
     * Retrieves a specific comment by its ID.
     * 
     * @param commentID The ID of the comment to retrieve.
     * @return The comment with the specified ID, or null if the comment is not found.
     */
    public Comments getCmtsBYId(int commentID);

    /**
     * Creates a new comment.
     * 
     * @param cmts The comment object to be created.
     */
    public void createCmt(Comments cmts);

    /**
     * Updates an existing comment.
     * 
     * @param commentID The ID of the comment to update.
     * @param cmts The comment object containing the updated details.
     */
    public void updateCmt(int commentID, Comments cmts);

    /**
     * Deletes a comment by its ID.
     * 
     * @param commentID The ID of the comment to be deleted.
     */
    public void deleteCmtById(int commentID);

    /**
     * Adds a comment to a specific post.
     * 
     * @param postID The ID of the post to add the comment to.
     * @param cmts The comment object to be added to the post.
     * @return A message indicating the success or failure of the operation.
     */
    public String addCmtSpecificPost(int postID, Comments cmts);

    /**
     * Deletes a specific comment from a specific post.
     * 
     * @param postID The ID of the post from which to delete the comment.
     * @param commentID The ID of the comment to be deleted.
     */
    public void deleteCmtSpecificPost(int postID, int commentID);

	public List<Comments> findCommentsByPostId(int postID);
}
