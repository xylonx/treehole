package com.xr.treehole.service;

import com.xr.treehole.entity.User;
import com.xr.treehole.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public boolean isPasswordRight(String emailAddress, String password){

        User userInfo = userRepository.findByEmailAddress(emailAddress);
        if (userInfo == null){
            return false;
        }

        return userInfo.getPassword().equals(password);
    }

    public boolean isUserExist(String emailAddress){

        User userInfo = userRepository.findByEmailAddress(emailAddress);
        return userInfo != null;
    }
}
