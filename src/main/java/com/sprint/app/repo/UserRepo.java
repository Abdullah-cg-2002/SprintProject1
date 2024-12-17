package com.sprint.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>
{

}
