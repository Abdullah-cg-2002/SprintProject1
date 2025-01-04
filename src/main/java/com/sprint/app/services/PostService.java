package com.sprint.app.services;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Positive;

import com.sprint.app.dto.PostsDTO;
import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts;

public interface PostService {

	public Posts savepost(PostsDTO post);


	Posts updatePosts(int postId, Posts post);

	void deletePosts(int postID);

	List<Likes> getLikesByPostId(int postId);

	List<Posts> getAllPosts();

	public Posts getByPostID(int postId);



	
}
