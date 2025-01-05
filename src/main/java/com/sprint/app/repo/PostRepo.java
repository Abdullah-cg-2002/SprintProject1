package com.sprint.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Posts;
import com.sprint.app.model.Users;

/**
 * Repository interface for performing CRUD operations on the {@link Posts} entity.
 * It extends {@link JpaRepository} to leverage Spring Data JPA's built-in functionality.
 * This repository includes a custom query method to find posts by a {@link Users} entity.
 */
@Repository
public interface PostRepo extends JpaRepository<Posts, Integer> {

    /**
     * Finds a list of {@link Posts} associated with a given {@link Users} entity.
     * 
     * @param user the {@link Users} entity whose posts are to be retrieved
     * @return a list of {@link Posts} created by the specified user
     */
    List<Posts> findByUser(Users user);
}
