package com.sprint.app.controller;

import com.sprint.app.controller.PostRestController;
import com.sprint.app.dto.PostsDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts;
import com.sprint.app.services.LikesService;
import com.sprint.app.services.PostService;
import com.sprint.app.services.UserService;
import com.sprint.app.success.SuccessResponse;
import com.sprint.app.success.SuccessResponseGet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc

public class PostControllerTesting {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private LikesService likeService;

    @InjectMocks
    private PostRestController postRestController;

    private PostsDTO postDTO;
    private Posts post;



    @BeforeEach
    public void setUp() {
        // Mock data
        postDTO = new PostsDTO();
        postDTO.setContent("Post content by user1");

        post = new Posts();
        post.setPostID(1);
        post.setContent("Post content by user1");
    }
//    @Test
//    void testAddPost() throws Exception {
//        Posts post = new Posts();
//        post.setContent("New Post Title");  // Set content for the post
//
//        when(postService.savepost(any(PostsDTO.class))).thenReturn(post);  // Return the post as a mock response
//
//
//        // Perform the POST request for creating a post
//        mockMvc.perform(post("/api/post")  // Ensure this matches the correct endpoint
//                .contentType(MediaType.APPLICATION_JSON))  // Convert the PostsDTO to JSON string
//                .andExpect(status().isOk())  // Assert that the HTTP status is 201 (Created)
//                .andExpect(jsonPath("$.status").value("Success"))  // Assert that the "status" is "Success"
//                .andExpect(jsonPath("$.message").value("Post with title 'New Post Title' created successfully"));  // Assert success message with title
//    }

//        @Test
//        public void testAddPost() throws Exception {
//            String postJson = "{\"content\":\"New Post Title\"}";
//            mockMvc.perform(post("/post")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(postJson))
//                    .andExpect(status().isCreated())
//                    .andExpect(jsonPath("$.status").value("Success"))
//                    .andExpect(jsonPath("$.message").value("Post with title 'New Post Title' created successfully"));
//
//        }

    @Test
    public void testGetPostById() throws Exception {
        SuccessResponseGet successResponseGet = new SuccessResponseGet();
        successResponseGet.setStatus("Success");
        successResponseGet.setData(List.of(post));

        when(postService.getByPostID(101)).thenReturn(post);

        mockMvc.perform(get("/api/post/101", 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.data[0].postID").value(101))
                .andExpect(jsonPath("$.data[0].content").value("Post content by user1"));

    }
    @Test
    public void testGetAllPosts() throws Exception {
        // Create a list of posts to be returned by the service
        List<Posts> posts = new ArrayList<>();
        Posts post = new Posts();  // Assuming 'post' is an instance of Posts or DTO
        post.setPostID(101);
        post.setContent("Post content by user1");
        posts.add(post);

        // Mock the service to return the posts list when getAllPosts() is called
        when(postService.getAllPosts()).thenReturn(posts);  // Correct mock syntax

        // Perform the GET request to the '/api/posts' endpoint
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())  // Assert that the status is 200 OK
                .andExpect(jsonPath("$.status").value("Success"))  // Assert the "status" field is "Success"
                .andExpect(jsonPath("$.data[0].postID").value(101))  // Assert the first post's postId is 101
                .andExpect(jsonPath("$.data[0].content").value("Post content by user1"));  // Assert the first post's content
    }


    @Test
    public void testUpdatePost() throws Exception {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setStatus("Success");
        successResponse.setMessage("Post with ID: 102 updated successfully.");

        when(postService.updatePosts(eq(102), any(Posts.class))).thenReturn(post);

        mockMvc.perform(put("/api/post/update/102", 102)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"Updated Test Post\"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("Post with ID: 102 updated successfully."));

    }

        @Disabled
        @Test
        public void testDeletePost() throws Exception {
            SuccessResponse successResponse = new SuccessResponse();
            successResponse.setStatus("Success");
            successResponse.setMessage("Post with ID: 161 deleted successfully.");

            doNothing().when(postService).deletePosts(161);

            mockMvc.perform(delete("/api/post/delete/161"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.status").value("Success"))
                   .andExpect(jsonPath("$.data[0]").value("Post with ID: 161 deleted successfully."));
        }
    


    @Test
    public void testAddLike() throws Exception {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setStatus("Success");
        successResponse.setMessage("Like added to post with ID: 101 from user with ID: 1");

        doNothing().when(userService).addLikeToPost(101, 1);

        mockMvc.perform(post("/api/posts/101/likes/add/1", 101, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("Like added to post by userID: 1"));

    }
    

    @Test
    public void testGetLikesByPostId() throws Exception{
    	List<Likes> likes = new ArrayList<> ();
    	Likes like = new Likes();
    	likes.add(like);
    	
    	when(postService.getLikesByPostId(105)).thenReturn(likes);
    	
    	mockMvc.perform(get("/api/posts/105/likes"))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.data.length()").value(1));
    	
    	
    }
    
    
    @Disabled
    @Test
    
    public void testRemoveLike() throws Exception {
        // Setup the response from the controller
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setStatus("Success");
        successResponse.setMessage("Like with ID: 10006 removed from post with ID: 106");

        // Mock the service layer to simulate removing a like
        doNothing().when(userService).removeLikeFromPost(1, 1);

        // Perform the DELETE request for removing a like
        mockMvc.perform(delete("/api/posts/106/likes/remove/10006"))
                .andExpect(status().isOk())  // Expect status code 200 OK
                .andExpect(jsonPath("$.status").value("Success"))  // Check if the status is Success
                .andExpect(jsonPath("$.data[0]").value("Like with ID: 10006 removed from post with ID: 106"));  // Check response data
    }
//    @Test
//    public void testGetAllLikesOnPostsByUserId() throws Exception {
//        // Prepare the list of likes that will be returned by the service
//        List<Likes> likes = new ArrayList<>();
//        likes.add(new Likes());  // Adding a sample like
//
//        // Mock the service to return the list of likes when the method is called
//        when(likeService.getLikeByspecfuser(125)).thenReturn(likes);  // Mocking service for user ID 2
//
//        // Perform the GET request to retrieve likes by user ID
//        mockMvc.perform(get("/api/user/125/posts/likes"))  // Updated the URL to match the controller's mapping
//                .andExpect(status().isOk())  // Expect status code 200 OK
//                .andExpect(jsonPath("$.status").value("Success"))  // Assert that status is "Success"
//                .andExpect(jsonPath("$.data[0]").value(1));  // Assert that there is one like in the data
//    }
}

