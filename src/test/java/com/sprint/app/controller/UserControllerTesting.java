package com.sprint.app.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.sprint.app.model.Comments;
import com.sprint.app.model.Friends;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Status;
import com.sprint.app.model.Users;
import com.sprint.app.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTesting {

    @Autowired
    private MockMvc mvc;

    @Mock
    private UserService userv;

    @InjectMocks
    private UserRestController usercontroller;

    private int userID = 2;

    @BeforeEach
    void setUp() {
      
    }

    @Test
    public void testGetPostsByUser() throws Exception {
        Posts post1 = new Posts();
        post1.setPostID(101);
        post1.setContent("Post content by user1");

        List<Posts> posts = Arrays.asList(post1);
        when(userv.getAllPostsUsr(userID)).thenReturn(posts);

        mvc.perform(get("/api/users/1/posts", 1)) 
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2)) 
            .andExpect(jsonPath("$.data[0].postID").value(101))
            .andExpect(jsonPath("$.data[0].content").value("Post content by user1"));
        
       
    }

    
    @Test
    void testGetAllCommentsOnPost() throws Exception {
        Comments comment1 = new Comments();
        comment1.setCommentid(1002);
        comment1.setCommentText("Comment by user2 on user2's post");

        List<Comments> comments = Arrays.asList(comment1);
        when(userv.getAllCmtsPst(userID)).thenReturn(comments);

        mvc.perform(get("/api/users/2/posts/comments", 2)) 
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2)) 
            .andExpect(jsonPath("$.data[0].commentid").value(1002))
            .andExpect(jsonPath("$.data[0].commentText").value("updated comment for the postID 102"));
    }

   @Test
  
   void testGetPendingReq() throws Exception {
       Users user1 = new Users();
       user1.setUserID(2);

       Friends friend1 = new Friends();
       friend1.setFriendshipID(100002);
       friend1.setStatus(Status.PENDING);
       friend1.setUser1(user1);
       friend1.setUser2(new Users());

       List<Object> pendingReq = new ArrayList<>();
       pendingReq.add(friend1);

       int userID = 3;
       when(userv.getPendingFrndReq(userID)).thenReturn(pendingReq);

       mvc.perform(get("/api/users/2/friend-requests/pending", 2)) 
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.data[0].friendshipID").value(100002)) 
           .andExpect(jsonPath("$.data[0].status").value("PENDING")); 
   }

}
