package com.xr.treehole.controller;


import com.xr.treehole.entity.User;
import com.xr.treehole.middleware.jwt.JwtUtils;
import com.xr.treehole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping(path = "/login")
    public String routeLoginPage(){
        return "login";
    }

    // for hand up JWT token
    @PostMapping(path = "/login", consumes = "application/x-www-form-urlencoded")
    //@ResponseBody
    public String IssueJwtTokenInCookie(User user, HttpServletResponse response) {

        boolean isPasswordRight = userService.isPasswordRight(user.getEmailAddress(), user.getPassword());
        if (!isPasswordRight){
            return "redirect:/user/login";
        }

        IssueJwtTokenInCookie(response, user.getEmailAddress());

        return "redirect:/index";
    }


    @GetMapping(path = "/register")
    public String routeRegisterPage(){
        return "register";
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

    @PostMapping(path = "/code")
    public void SendRegisterCode(String emailAddress){

    }

    private void IssueJwtTokenInCookie(HttpServletResponse response, String emailAddress){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", emailAddress);
        String token = jwtUtils.GenerateToken(claims);

        Cookie cookie = new Cookie(jwtUtils.CookieName, token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
