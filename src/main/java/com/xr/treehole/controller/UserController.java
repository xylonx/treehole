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

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping(path = "/login")
    public String routeLoginPage() {
        return "login";
    }

    // for hand up JWT token
    @PostMapping(path = "/login", consumes = "application/x-www-form-urlencoded")
    //@ResponseBody
    public String IssueJwtTokenInCookie(User user, HttpServletResponse response) {

        boolean isPasswordRight = userService.isPasswordRight(user.getEmailAddress(), user.getPassword());
        if (!isPasswordRight) {
            return "redirect:/user/login";
        }

        jwtUtils.IssueJwtTokenInCookie(response, user.getEmailAddress());

        return "redirect:/index";
    }


    @GetMapping(path = "/register")
    public String routeRegisterPage() {
        return "register";
    }

    @PostMapping(path = "/register", consumes = "application/x-www-form-urlencoded")
    public String register(User user, String registerCode, HttpServletResponse response) {

        boolean isUserExist = userService.isUserExist(user.getEmailAddress());
        boolean isRegisterCodeValid = userService.isRegisterCodeValid(registerCode);
        if (isUserExist) {
            return "redirect:/user/login";
        }
        if (!isRegisterCodeValid){
            return "redirect:/user/register";
        }

        userService.saveUser(user);
        jwtUtils.IssueJwtTokenInCookie(response, user.getEmailAddress());
        return "redirect:/index";
    }

    @PostMapping(path = "/register-code")
    @ResponseBody
    public void SendRegisterCode(String emailAddress) {

        if (!userService.isPostfixValid(emailAddress)) {
            return;
        }

        userService.SendRegisterCode(emailAddress);
    }


}
