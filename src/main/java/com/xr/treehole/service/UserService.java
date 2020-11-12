package com.xr.treehole.service;

import com.xr.treehole.entity.User;
import com.xr.treehole.repositories.UserRepository;
import com.xr.treehole.util.Encrypt;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        String hashedEmail = Encrypt.Hashing(user.getEmailAddress());
        user.setEmailHash(hashedEmail);

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        String hashedEmail = Encrypt.Hashing(email);
        return userRepository.findByEmailHash(hashedEmail);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public boolean isPasswordRight(String emailAddress, String password) {

        String hashedEmail = Encrypt.Hashing(emailAddress);

        User userInfo = userRepository.findByEmailHash(hashedEmail);
        if (userInfo == null) {
            return false;
        }

        return BCrypt.checkpw(password, userInfo.getPassword());
    }

    public boolean isUserExist(String emailAddress) {

        String hashedEmail = Encrypt.Hashing(emailAddress);

        User userInfo = userRepository.findByEmailHash(hashedEmail);
        return userInfo != null;
    }
}
