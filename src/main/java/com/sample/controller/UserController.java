package com.sample.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

	static Map<String, User> users = new HashMap();
	static Integer userId = 1;
	
	//http://localhost:8080/users/allusers	
	@GetMapping(path="/allusers")
	public Map<String, User> getAllUsers(){
		return users;
	}
	
	//http://localhost:8080/users/1
	@GetMapping(path="/{userId}",produces =  { MediaType.APPLICATION_JSON_VALUE	} )
	public ResponseEntity<User> getUser(@PathVariable String userId){
		if(users.containsKey(userId))
		{
			return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//http://localhost:8080/users/newuser
	@PostMapping(path="/{newuser}", consumes =  { MediaType.APPLICATION_JSON_VALUE	}, produces =  { MediaType.APPLICATION_JSON_VALUE } )
	public ResponseEntity<User> createUser(@Valid @RequestBody User userRest){

		User user = new User();
		user.setFirstName(userRest.getFirstName());
		user.setLastName(userRest.getLastName());
		user.setEmail(userRest.getEmail());
		user.setUserId(userId);
		users.put(""+userId, user);
		userId++;
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	//http://localhost:8080/users/1
	@PutMapping(path="/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE	}, produces =  {MediaType.APPLICATION_JSON_VALUE })
	public User updateUser(@PathVariable String userId, @Valid @RequestBody User userRest){
		 User user = users.get(userId);
		 user.setFirstName(userRest.getFirstName());
		 user.setLastName(userRest.getLastName());		 
		 users.put(userId, user);
		 
		 return user;
	}
	
	//http://localhost:8080/users/2
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id){
		users.remove(id);
		
		return ResponseEntity.noContent().build();
	}
}
