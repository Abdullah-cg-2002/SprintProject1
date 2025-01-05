package com.sprint.app.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts; // Changed Post to Posts
import com.sprint.app.model.Users;
import com.sprint.app.repo.LikeRepo;
import com.sprint.app.repo.PostRepo;
import com.sprint.app.repo.UserRepo;
import com.sprint.app.services.LikesService;

@Service
public class LikesServiceImpl implements LikesService {
    @Autowired
    private LikeRepo likesrepository;
    @Autowired
    private PostRepo postrepository;
    @Autowired
    private UserRepo userrepository;
    

    // Method to get likes by Post ID
    @Override
    public List<Likes> getLikeByPostID(int postID) {  // Changed PostID to postID for consistency
        Posts posts = postrepository.findById(postID).orElseThrow(() -> new RuntimeException("Post Not Found..."));
        List<Likes> likeList = likesrepository.findByPosts(posts);  // Changed Post to Posts
        if (likeList.isEmpty()) {
            throw new RuntimeException("No likes Found.");
        }
        return likeList;
    }

    // Method to save a like
    @Override
    public Likes savelike(int likeID, int postId, int userId) {
        Users user = userrepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with ID '" + userId + "' not found."));
        Posts posts = postrepository.findById(postId).orElseThrow(() -> new RuntimeException("Post Not Found..."));

        Likes like = new Likes();
        like.setPosts(posts);  // Changed Post to Posts
        like.setUser(user);
        return likesrepository.save(like);
    }

    // Method to get likes by specific user
    @Override
    public List<Likes> getLikeByspecfuser(int userID) {
        Users user = userrepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User with ID '" + userID + "' not found."));
        
        List<Posts> userPosts = postrepository.findByUser(user);
        if (userPosts == null || userPosts.isEmpty()) {
            // Instead of throwing an exception, we can just return an empty list
            return new ArrayList<>();
        }

        List<Likes> allLikes = new ArrayList<>();
        for (Posts posts : userPosts) {
            List<Likes> likeList = likesrepository.findByPosts(posts);
            if (likeList != null) {
                allLikes.addAll(likeList);
            }
        }

        return allLikes;
    }


    
   
    // Method to get a like by its ID
    @Override
    public Likes getByLikeID(int likeId) {
        Likes like = likesrepository.findById(likeId).orElseThrow(() -> new RuntimeException("Like Not Found..."));
        return like;
    }

    // Method to delete a like
    @Override
    public void deleteLikes(int likeID) {
        likesrepository.deleteById(likeID);
    }

    // Method to get likes by user
    @Override
    public List<Likes> getLikesByUser(Users user) {
        List<Likes> allLikes = likesrepository.findByUser(user);
        if (allLikes == null || allLikes.isEmpty()) {
            throw new RuntimeException("Likes Not given by the user");
        }
        return allLikes;
    }

    
    @Override
    public Likes findByPostsAndUser(Posts post, Users user) {
        return likesrepository.findByPostsAndUser(post, user);  // Directly use the repository method
    }


	
}
