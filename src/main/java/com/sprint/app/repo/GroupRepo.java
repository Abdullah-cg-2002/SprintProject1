package com.sprint.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.app.model.Groups;

@Repository
public interface GroupRepo extends JpaRepository<Groups, Integer>
{

}
