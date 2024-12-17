package com.sprint.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Messages;

@Repository
public interface MessageRepo extends JpaRepository<Messages, Integer>
{

}
