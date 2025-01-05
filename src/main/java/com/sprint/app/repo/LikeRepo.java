package com.sprint.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Likes;
import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;

/**
 * Repository interface for performing CRUD operations on the {@link Likes} entity.
 * It extends {@link JpaRepository} to leverage Spring Data JPA's built-in functionality.
 * This repository includes custom query methods to find likes by {@link Posts} or {@link Users}.
 */
@Repository
public interface LikeRepo extends JpaRepository<Likes, Integer> {

    /**
     * Finds a list of {@link Likes} associated with a given {@link Posts} entity.
     * 
     * @param post the {@link Posts} entity whose likes are to be retrieved
     * @return a list of {@link Likes} associated with the given post
     */
    List<Likes> findByPosts(Posts post);

    /**
     * Finds a list of {@link Likes} associated with a given {@link Users} entity.
     * 
     * @param user the {@link Users} entity whose likes are to be retrieved
     * @return a list of {@link Likes} associated with the given user
     */
    List<Likes> findByUser(Users user);

	Likes findByPostsAndUser(Posts post, Users user);

}
