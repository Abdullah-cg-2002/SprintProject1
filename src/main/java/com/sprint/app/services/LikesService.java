package com.sprint.app.services;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;


public interface LikesService {
	
	public List<Likes> getLikeByPostID(int PostID); //
	public Likes savelike(int likeID, int postId, int userId);//
	public Likes getByLikeID(int likeId);//
	public List<Likes> getLikeByspecfuser(int UserID); //
	public void deleteLikes(int likeID); //
	List<Likes> getLikesByUser(Users user);

	Likes findByPostsAndUser(Posts post, Users user);
}