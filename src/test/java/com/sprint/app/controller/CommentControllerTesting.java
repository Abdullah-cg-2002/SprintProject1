package com.sprint.app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.app.dto.CommentDTO;
import com.sprint.app.model.Comments;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.services.CommentService;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTesting {
	
	@Autowired
	private MockMvc mvc;
	
	@Mock
	private CommentService cserv;
	
	@InjectMocks
	private CommentRestController cmtcontroller;
	
	private ObjectMapper obj;
	
	private List<Comments> licom;
	
	private Comments cmts;
	
	
	@BeforeEach
	void setUp()
	{
		obj=new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(cmtcontroller).build();
		
		Comments cmt1 = new Comments();
		cmt1.setCommentid(1003);
		cmt1.setCommentText("Comment by user3 on user3's post");
		
		 Posts post = new Posts();
		 post.setPostID(103);
		 cmt1.setPost(post);
		 
		licom = new ArrayList<>();
		licom.add(cmt1);
	
	}
	
	
	@Test
	void testGetCommentsbyPostID() throws Exception
	{
		when(cserv.getAllCmts(anyInt())).thenReturn(licom);
		mvc.perform(get("/api/posts/103/comments")).andExpect(status().isOk())
		                                         .andExpect(jsonPath("$.status").value("Success"))
		                                         
		                                         .andExpect(jsonPath("$.data").exists());
		                                        
	}
	
	@Test
	void testGetAllComments() throws Exception
	{
		when(cserv.getAllCmts(anyInt())).thenReturn(licom);
		mvc.perform(get("/api/comments")).andExpect(status().isOk())
		                                 .andExpect(jsonPath("$.status").value("Success"))
		                                 .andExpect(jsonPath("$.data").exists());
	}
	
	@Test
	void testGetCommentsById() throws Exception
	{
		when(cserv.getCmtsBYId(anyInt())).thenReturn(cmts);
		mvc.perform(get("/api/comments/1003")).andExpect(status().isOk());
		                                      
	}
	
	@Test
	  
	    void testAddComments() throws Exception {
	      
	        CommentDTO cmtdto = new CommentDTO();
	        cmtdto.setComment_text("This is a test comment");
	        cmtdto.setUsers(new Users());  
	        cmtdto.setPost(new Posts());   
	        doNothing().when(cserv).createCmt(any(Comments.class)); 

	       
	        mvc.perform(post("/api/comments")  
	                .contentType(MediaType.APPLICATION_JSON) 
	                .content(obj.writeValueAsString(cmtdto)))  
	                .andExpect(status().isCreated())  
	                .andExpect(jsonPath("$.status").value("Success"))  
	                .andExpect(jsonPath("$.message").value("Comment added Successfully")); 

	        verify(cserv).createCmt(any(Comments.class));
	    }

	@Test
	 void testAddCommentsForPost() throws Exception {
	    Comments comment = new Comments();
	    comment.setCommentText("This is a test comment");
	    comment.setCommentid(1003);
	    String json = new ObjectMapper().writeValueAsString(comment);
	    
	    mvc.perform(post("/api/posts/103/comments")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	            .andExpect(status().isCreated());
	}
	@Test
	 void testUpdateComment() throws Exception {
	    Comments comment = new Comments();
	    comment.setCommentText("This is a test comment");
	    
	    String json = new ObjectMapper().writeValueAsString(comment);
	    
	    mvc.perform(put("/api/comments/1007")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	            .andExpect(status().isOk());
	}
	
	   @Test
	    void testDeleteCommentsDataById() throws Exception {
	        doNothing().when(cserv).deleteCmtById(anyInt());
	 
	        mvc.perform(delete("/api/comments/1004"))
	                .andExpect(status().isNoContent())
	                .andExpect(jsonPath("$.status").value("Success"))
	                .andExpect(jsonPath("$.message").value("Comment is Successfully Deleted"));
	    }
	
	   @Test
	    void testDeleteCommentByPostId() throws Exception {
	        doNothing().when(cserv).deleteCmtSpecificPost(anyInt(), anyInt());
	 
	        mvc.perform(delete("/api/posts/107/comments/1007"))
	                .andExpect(status().isNoContent())
	                .andExpect(jsonPath("$.status").value("Success"))
	                .andExpect(jsonPath("$.message").value("Comment is Successfully deleted"));
	    }
	}
	   
	   
	   
	   
	
	

