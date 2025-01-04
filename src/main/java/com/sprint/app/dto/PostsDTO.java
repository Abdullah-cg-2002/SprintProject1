package com.sprint.app.dto;

import javax.validation.constraints.NotBlank;
import com.sprint.app.model.Users;

/**
 * Data Transfer Object (DTO) for representing a Post.
 * This class is used to transfer post data between layers (such as from the controller to service).
 * It contains information about the post's content and the user who is posting.
 */
public class PostsDTO {

    /**
     * The content of the post.
     * This field cannot be blank.
     */
    @NotBlank(message = "Content cannot be blank") // Content cannot be null or empty
    private String content;

    /**
     * The user who is posting the content.
     */
    private Users user;

    /**
     * Retrieves the content of the post.
     *
     * @return the content of the post
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the post.
     *
     * @param content the content to set for the post
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Retrieves the user who is posting the content.
     *
     * @return the user who is posting the content
     */
    public Users getUser() {
        return user;
    }

    /**
     * Sets the user who is posting the content.
     *
     * @param user the user to set for the post
     */
    public void setUser(Users user) {
        this.user = user;
    }
}
