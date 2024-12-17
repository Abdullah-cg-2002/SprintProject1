package com.sprint.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Likes;

@Repository
public interface LikeRepo extends JpaRepository<Likes, Integer>
{

}
