package com.sprint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.app.services.UserService;
import com.sprint.app.model.Likes;

@RestController
@RequestMapping("/api/")
public class UserRestController {
	
	@Autowired
	private UserService us;
	

	
	@GetMapping("users/{userID}/posts/likes")
	public List<Likes> getAllLikes(@PathVariable int userID)
	{
		return us.getAllLikesPst(userID);
	}
	
	
	
	
}


