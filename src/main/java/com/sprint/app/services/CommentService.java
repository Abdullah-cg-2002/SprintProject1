package com.sprint.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.model.Comments;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.CommentRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepo cr;
	
	@Autowired
	private PostRepo pr;
	
	@Autowired
	private UserRepo ur;
	
	//get all comments for a post
	public List<Comments> CmtSpecificPost(int postID)
	{
		Optional<Posts> opt =  pr.findById(postID);
		
		if(opt.isPresent())
		{
			Posts p = opt.get();
			return p.getComments();
		}
		
		return null;
	}
	
	//get all comments
	public List<Comments> getAllCmt()
	{
		if(!cr.findAll().isEmpty())
		{
			return cr.findAll();
		}
		
		return null;
	}
	
	//get specific comment
	public Comments getSpecificCmt(int commentID)
	{
		Optional<Comments> opt = cr.findById(commentID);
		
		if(opt.isPresent())
		{
			return opt.get();
		}
		
		return null;
	}
	
	//create comments
	public void createCmt(Comments cmts)
	{
		Optional<Users> usropt = ur.findById(cmts.getUsers().getUserID());
		
		if(usropt.isPresent())
		{
			Users usr = usropt.get();
			Optional<Posts> pstopt = pr.findById(cmts.getPost().getPostID());
			
			if(pstopt.isPresent())
			{
				Posts post = pstopt.get();
				cmts.setTimestamp(LocalDateTime.now());
				post.getComments().add(cmts);
				usr.getComments().add(cmts);
				cr.save(cmts);
			}
		}
	}
	
	//create comments on specific post
	public void createCmtSpecificPost(int postID, Comments cmts)
	{
		Optional<Posts> opt = pr.findById(postID);
		
		if(opt.isPresent())
		{
			Posts post = opt.get();
			Users usrpst = cmts.getUsers();
			cmts.setTimestamp(LocalDateTime.now());
			cmts.setPost(post);
			cmts.setUsers(usrpst);
			usrpst.getComments().add(cmts);
			post.getComments().add(cmts);
			cr.save(cmts);
		}
	}
	
	//update existing comment
	public void updateCmt(int commentID, Comments cmt)
	{
		Optional<Comments> cmtopt = cr.findById(commentID);
		
		if(cmtopt.isPresent())
		{
			Comments cmts = cmtopt.get();
			
			if(cmt.getComment_text() != null)
			{
				cmts.setComment_text(cmt.getComment_text());
				cmts.setTimestamp(LocalDateTime.now());
				cr.save(cmts);
			}
		}
	}
	
	//delete existing comment
	public void deleteCmt(int commentID)
	{
		Optional<Comments> cmtopt = cr.findById(commentID);
		
		if(cmtopt.isPresent())
		{
			Comments comment = cmtopt.get();
			Posts post = comment.getPost();
			Users usr = comment.getUsers();
			
			post.getComments().remove(comment);
			usr.getComments().remove(comment);
			cr.deleteById(commentID);
		}
	}
	
	//delete comment using postid and commentid
	
	public void deleteCmtSpeciifcPost(int postID, int commentID)
	{
		Optional<Posts> pstopt = pr.findById(postID);
		Optional<Comments> cmtopt = cr.findById(commentID);
		
		if(pstopt.isPresent())
		{
			if(cmtopt.isPresent())
			{
				Posts post = pstopt.get();
				Users usr = post.getUser();
				Comments cmt = cmtopt.get();
				
				usr.getComments().remove(cmt);
				post.getComments().remove(cmt);
				
				ur.save(usr);
				pr.save(post);
				
				cr.deleteById(commentID);
			}
		}
	}

}
