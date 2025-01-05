package com.sprint.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Comments;

@Repository
public interface CommentRepo extends JpaRepository<Comments, Integer>
{

	List<Comments> findByPost_PostID(int postID);

}
