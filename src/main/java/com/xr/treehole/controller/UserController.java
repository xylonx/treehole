package com.xr.treehole.controller;

import com.xr.treehole.config.selfdef.MailConfig;
import com.xr.treehole.entity.User;
import com.xr.treehole.middleware.jwt.JwtUtils;
import com.xr.treehole.service.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping(path = "/login")
    public String routeLoginPage(){
        return "login";
    }

    // for hand up JWT token
    @PostMapping(path = "/login")
//    @ResponseBody
    public String IssueJwtToken(User user, Model model, ServletRequest request) {

        boolean isPasswordRight = userService.isPasswordRight(user.getEmailAddress(), user.getPassword());
        if (!isPasswordRight){
            return "redirect:/user/login";
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getEmailAddress());
        String token = jwtUtils.GenerateToken(claims);

        model.addAttribute("token", token);

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;


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

}
