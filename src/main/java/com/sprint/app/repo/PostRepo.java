package com.sprint.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Posts;

@Repository
public interface PostRepo extends JpaRepository<Posts, Integer>
{

}
