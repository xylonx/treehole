package com.xr.treehole.service;

import com.xr.treehole.config.selfdef.JwtConfig;
import com.xr.treehole.config.selfdef.MailConfig;
import com.xr.treehole.config.selfdef.RedisKeyConfig;
import com.xr.treehole.entity.User;
import com.xr.treehole.middleware.jwt.JwtUtils;
import com.xr.treehole.repositories.RegisterCodeRepository;
import com.xr.treehole.repositories.UserRepository;
import com.xr.treehole.util.Encrypt;
import com.xr.treehole.util.GenerateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@EnableConfigurationProperties({MailConfig.class, RedisKeyConfig.class})
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RegisterCodeRepository registerCodeRepository;

    @Autowired
    MailConfig mailConfig;

    @Autowired
    RedisKeyConfig redisKeyConfig;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MailService mailService;


    public User saveUser(User user) {
        user.setPassword(Encrypt.SecurityHash(user.getPassword()));

        String hashedEmail = Encrypt.Hashing(user.getEmailAddress());
        user.setEmailHash(hashedEmail);

        return userRepository.save(user);
    }

    public User getUserByEmailHash(String hashedEmail) {
        Optional<User> optionalUser = userRepository.findById(hashedEmail);
        return optionalUser.orElse(null);
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

        return Encrypt.CheckSecurityHash(password, userInfo.getPassword());
    }

    public boolean isUserExist(String emailAddress) {

        String hashedEmail = Encrypt.Hashing(emailAddress);

        User userInfo = userRepository.findByEmailHash(hashedEmail);
        return userInfo != null;
    }

    public User getCurrentUser(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(jwtUtils.CookieName)) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null) {
            return null;
        }

        String hashedEmail = jwtUtils.getUsername(token);
        return getUserByEmailHash(hashedEmail);

    }

    public boolean isPostfixMeet(String emailAddress) {
        String postfix = mailConfig.getPostfix();
        String pattern = ".*" + postfix + "$";

        return emailAddress.matches(pattern);
    }

    public String SendRegisterCode(String emailAddress){

        String registerCode = GenerateId.GenerateRandomId(8, true, true);

        registerCodeRepository.Save(registerCode, redisKeyConfig.getExp());

        mailService.SendTemplateMail(emailAddress, "树洞注册码", registerCode);

        return registerCode;
    }

    public boolean isRegisterCodeValid(String registerCode){
        return registerCodeRepository.FindRegisterCode(registerCode);
    }
}