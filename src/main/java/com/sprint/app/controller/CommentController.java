package com.sprint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.dto.CommentDTO;
import com.sprint.app.model.Comments;

import com.sprint.app.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService cs;
	
	@GetMapping("/posts/{postID}/comments")
	public List<Comments> getAllCmtsForPost(@PathVariable int  postID)
	{
		return cs.CmtSpecificPost(postID);
	}
	
	@GetMapping("/comments")
	public List<Comments> getAllCmts()
	{
		return cs.getAllCmt();
	}
	
	@GetMapping("comment/{commentID}")
	public Comments getCmt(@PathVariable int commentID)
	{
		return cs.getSpecificCmt(commentID);
	}
	
	@PutMapping("comments/{commentID}")
	public void updateCmt(@PathVariable int commentID, @RequestBody Comments cmts)
	{
		cs.updateCmt(commentID, cmts);
	}
	
	@DeleteMapping("comments/{commentID}")
	public void deleteById(@PathVariable int commentID)
	{
		cs.deleteCmt(commentID);
	}
	
	@PostMapping("comments")
	@ResponseStatus(HttpStatus.CREATED)
	public void createCmt(@RequestBody CommentDTO cmdto)
	{
		Comments cmts = new Comments();
		cmts.setComment_text(cmdto.getComment_text());
		cmts.setPost(cmdto.getPost());
		cmts.setUsers(cmdto.getUsers());
		cs.createCmt(cmts);
	}
	
	@PostMapping("posts/{postID}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public void createCmtOnPost(@PathVariable int postID, @RequestBody CommentDTO cmdto)
	{
		Comments cmts = new Comments();
		cmts.setComment_text(cmdto.getComment_text());
		cmts.setUsers(cmdto.getUsers());
		cs.createCmtSpecificPost(postID, cmts);
	}

}
