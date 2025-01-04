package com.sprint.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.model.Likes;
//import com.sprint.app.model.Messages;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.LikeRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;
//import com.sprint.app.services.FriendService;
//import com.sprint.app.services.MessageService;
import com.sprint.app.services.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService
{
	
	@Autowired
	private UserRepo ur;
//	posts,likes,user
	
	@Autowired
	private LikeRepo lr;
	
	@Autowired
	private PostRepo pr;
	

	//get all likes get by user on all posts
	public List<Likes> getAllLikesPst(int userID)
	{
		Optional<Users> usropt = ur.findById(userID);
		
		if(usropt.isPresent())
		{
			List<Likes> likes = new ArrayList<>();
			Users usr = usropt.get();
			for(Posts pst : usr.getPosts())
			{
				likes.addAll(pst.getLikes());
			}
			
			return likes;
		}
		
		return null;
	}

	
	@Override
	 public void addLikeToPost(int postId, int userId) {
	        // Retrieve the post and user from the database (using repositories)
	        Posts post = pr.findById(postId)
	                .orElseThrow(() -> new RuntimeException("Post not found"));
	        Users user = ur.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        // Logic to add a like to the post
	        Likes like = new Likes();
	        like.setPosts(post);
	        like.setUser(user);
	        post.getLikes().add(like);
	        user.getLikes().add(like);
	        lr.save(like);
	    }
	 
	 
	

	@Override
	public void removeLikeFromPost(int postId, int likesId) {
        // Find the like by its ID
        Likes like = lr.findById(likesId)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        // Ensure the like is associated with the correct post
        if (like.getPosts().getPostID() != postId) {
            throw new RuntimeException("This like does not belong to the specified post");
        }

        // Remove the like from the database
        Posts post = like.getPosts();
        post.getLikes().remove(like);
        lr.delete(like);
        
    }
	
}