package com.sprint.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.app.dto.MessageDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Messages;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.services.UserService;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc
public class UserControllerTesting {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objmap;
	
	@MockitoBean
	private UserService userService;
	
	private MessageDTO messageDto;
	private Messages message;
	private Users sender;
	private Users receiver;
	private List<Messages> messages;
	private Likes like;
	
	@BeforeEach
	void setup()
	{
		sender = new Users();
		receiver = new Users();
		
		sender.setUserID(1);
		receiver.setUserID(5);
		
		messageDto = new MessageDTO();
		
		messageDto.setMessage_text("Hi user5 from user1");
		messageDto.setReceiver(receiver);
		messageDto.setSender(sender);
		
		message = new Messages();
		message.setMessage_text("Messages Edited by User1");
		
		messages = List.of(message);
		
		like = new Likes();
		like.setLikesID(100);
		like.setTimestamp(LocalDateTime.now());
	}
	
	@Test
	void testGetAllMsgsBtwnUsers() throws Exception
	{
		when(userService.msgBtwUsers(1, 3)).thenReturn(messages);
		
		mockMvc.perform(get("/api/users/1/messages/3"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value("success"))
		.andExpect(jsonPath("$.data[0].message_text").value("Messages Edited by User1"));	
	}
	
	@Test
	void testGetAllLikesPost() throws Exception
	{
		when(userService.getAllLikesPst(1)).thenReturn(Arrays.asList(like));
		mockMvc.perform(get("/api/users/1/posts/likes"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value("success"))
		.andExpect(jsonPath("$.data[0].likesID").value(100));
	}
	
	@Test
	void testGetAllLikesUser() throws Exception
	{
		when(userService.getAllLikesUsr(1)).thenReturn(Arrays.asList(like));
		
		mockMvc.perform(get("/api/users/1/likes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data[0].likesID").value(100));
	}
	
	@Test
	void testSendMsgToUser() throws Exception
	{
		when(userService.sendMsgFrnd(1, 3, messageDto)).thenReturn(null);
		mockMvc.perform(post("/api/users/1/messages/send/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objmap.writeValueAsString(messageDto)))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.message").value("message sent to friend successfully"));
	}
	
	@Test
	void testSendFrndReq() throws Exception
	{
		when(userService.sendFrdReq(7, 10)).thenReturn(null);
		mockMvc.perform(post("/api/users/7/friend-request/send/10"))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.status").value("success"))
		.andExpect(jsonPath("$.message").value("friend request sent successfully"));
			
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

   @Test
    void testUpdateUser() throws Exception {
        int userID = 1;
        Users user = new Users();
        user.setUsername("UpdatedUser");
        user.setEmail("updateduser@example.com");
        user.setPassword("newpassword");

        String successMessage = "User updated successfully";
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setStatus("success");
        successResponse.setMessage(successMessage);

        when(userService.updateUser(eq(userID), any(Users.class))).thenReturn(successMessage);

       
        mockMvc.perform(put("/api/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value(successMessage))
                .andDo(print());

        verify(userService, times(1)).updateUser(eq(userID), any(Users.class));
    }

    
    @Test
    void testGetNotificationByUserID() throws Exception {
        int userID = 1;

        SuccessResponseGet successResponseGet = new SuccessResponseGet();
        successResponseGet.setStatus("success");

        // Create mock notifications as Map
        List<Object> notifications = new ArrayList<>();
        Map<String, String> notification1 = new HashMap<>();
        notification1.put("title", "Notification 1");
        notification1.put("message", "This is the first notification");
        notifications.add(notification1);

        Map<String, String> notification2 = new HashMap<>();
        notification2.put("title", "Notification 2");
        notification2.put("message", "This is the second notification");
        notifications.add(notification2);

        successResponseGet.setData(notifications);

        when(userService.getNotificationByUserID(userID)).thenReturn(successResponseGet);

        mockMvc.perform(get("/api/users/1/notification"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].title").value("Notification 1"))
                .andExpect(jsonPath("$.data[0].message").value("This is the first notification"))
                .andExpect(jsonPath("$.data[1].title").value("Notification 2"))
                .andExpect(jsonPath("$.data[1].message").value("This is the second notification"))
                .andDo(print());

        verify(userService, times(1)).getNotificationByUserID(userID);
    }

}
