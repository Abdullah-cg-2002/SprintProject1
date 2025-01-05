package com.sprint.app.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.app.dto.PostsDTO;
import com.sprint.app.exception.PostException;

import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;
import com.sprint.app.repo.LikeRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.PostService;

@Service
public class PostServiceImpl implements PostService {
    
    @Autowired
    private PostRepo postrepository;

    @Autowired
    private LikeRepo likerepository;
    
    @Autowired
    private UserRepo ur;
    
    
    @Override 
    public Posts savepost(PostsDTO post) {
        Users usrs = post.getUser();
        Posts pst = new Posts();
        pst.setContent(post.getContent());
        pst.setUser(usrs);
        usrs.getPosts().add(pst);
        return postrepository.save(pst);
    }
    
    public Posts getByPostID(int postId) {
        // Retrieve the post by ID, or throw a PostNotFoundException if not found
        return postrepository.findById(postId)
            .orElseThrow(() -> new PostException("Post with ID " + postId + " not found"));
    }

    @Override
    public Posts updatePosts(int postId, Posts post) {
        Posts oldpost = getByPostID(postId);
        if (oldpost != null) {
            oldpost.setContent(post.getContent());
            return postrepository.save(oldpost);
        }
        return null;
    }
    
    @Override
    public void deletePosts(int postID) {
        Posts post = postrepository.findById(postID)
            .orElseThrow(() -> new PostException("Post with ID " + postID + " not found"));
        post.getUser().getPosts().remove(post);
        postrepository.delete(post);
    }

    @Override
    public List<Likes> getLikesByPostId(int postId) {
        // Find the post by its ID, or throw a PostNotFoundException if not found
        Posts post = postrepository.findById(postId)
            .orElseThrow(() -> new PostException("Post with ID " + postId + " not found"));

        // Retrieve the likes associated with the post
        return likerepository.findByPosts(post);
    }

    @Override
    public List<Posts> getAllPosts() {
        List<Posts> posts = postrepository.findAll();
        if (posts.isEmpty()) {
            throw new PostException("No posts found");
        }
        return posts;
    }

	

}
