package com.learning.journalApp.service;

import com.learning.journalApp.entity.User;
import com.learning.journalApp.exception.BadRequestException;
import com.learning.journalApp.exception.ResourceNotFoundException;
import com.learning.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        if(user == null){
            throw new BadRequestException("User payload cannot be null");
        }
        try{
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex){
            throw new BadRequestException("Username already exists or user data is invalid");
        }
    }

    public User findUserById(String id){
        if(id == null || id.isBlank()){
            throw new BadRequestException("User id cannot be blank");
        }
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + id));
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findByUserName(String userName){
        if(userName == null || userName.isBlank()){
            throw new BadRequestException("Username cannot be blank");
        }
        User user = userRepository.findByUserName(userName);
        if(user == null){
            throw new ResourceNotFoundException("User not found for username: " + userName);
        }
        return user;
    }

    public User updateUser(User newUser, String userName){
        if(newUser == null){
            throw new BadRequestException("User payload cannot be null");
        }
        User existingUser = findByUserName(userName);
        existingUser.setUserName(newUser.getUserName());
        existingUser.setPassword(newUser.getPassword());
        try{
            return userRepository.save(existingUser);
        } catch (DataIntegrityViolationException ex){
            throw new BadRequestException("Cannot update user due to invalid or duplicate username");
        }
    }

    @Transactional
    public void deleteByUserName(String userName) {
        findByUserName(userName);
        userRepository.deleteByUserName(userName);
    }
}
