package com.xr.treehole.controller;

import com.xr.treehole.config.selfdef.MailConfig;
import com.xr.treehole.entity.User;
import com.xr.treehole.middleware.jwt.JwtUtils;
import com.xr.treehole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(path = "/user")
@EnableConfigurationProperties(MailConfig.class)
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping(path = "/login", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public Map<String, String> login(User user) {

        boolean isPasswordRight = userService.isPasswordRight(user.getEmailAddress(), user.getPassword());
        if (!isPasswordRight){
            return null;
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getEmailAddress());
        String token = jwtUtils.GenerateToken(claims);

        Map<String, String> returnToken = new HashMap<>();
        returnToken.put("token", token);
        return returnToken;
    }

    @PostMapping(path = "/register", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public Map<String, String> register(User user) {

        boolean isUserExist = userService.isUserExist(user.getEmailAddress());
        if (isUserExist){
            return null;
        }

        user = userService.saveUser(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getEmailAddress());
        String token = jwtUtils.GenerateToken(claims);

        Map<String, String> returnToken = new HashMap<>();
        returnToken.put("token", token);
        return returnToken;
    }

}
