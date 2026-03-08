package com.learning.journalApp.controller;

import com.learning.journalApp.entity.User;
import com.learning.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.findAllUsers();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    @GetMapping("id/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable String userId){
        User user = userService.findUserById(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName){
        userService.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody User newUser){
        User user = userService.updateUser(newUser,userName);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
