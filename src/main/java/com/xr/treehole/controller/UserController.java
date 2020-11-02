package com.xr.treehole.controller;

import com.xr.treehole.entity.User;
import com.xr.treehole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping(path = "", consumes = "application/x-www-form-urlencoded")
    public String getUserHomepage(String status, User user, Model model, HttpServletResponse response) {

        String returnPage;

        if (status.equals("login")) {
            returnPage = userLogin(user, model, response);
        } else if (status.equals("register")) {
            returnPage = userRegister(user, model, response);
        } else {
            returnPage = "error";
        }

        return returnPage;
    }

    /**
     * send register data to backend ad route to user main page if no error occur
     *
     * @param user     user login info, containing email_address and password
     * @param model    thymeleaf unique object to bind data
     * @param response set to add cookie for user authorized
     * @return user homepage
     */
    public String userRegister(User user, Model model, HttpServletResponse response) {

        boolean isUserExist = userService.isUserExist(user.getEmailAddress());

        model.addAttribute("isUserExist", isUserExist);

        if (!isUserExist) {

            userService.saveUser(user);

            // FIXME: UID should be generated automatically and check
            Cookie cookie = new Cookie("uid", "12345678");
            response.addCookie(cookie);

            return "index";
        }

        return "register";
    }


    /**
     * send login data to backend and reroute to index page
     *
     * @param user     user login info, containing email_address and password
     * @param model    thymeleaf unique object to bind data
     * @param response set to add cookie for user authorized
     * @return user homepage or error page
     * return "index";
     */
    public String userLogin(User user, Model model, HttpServletResponse response) {

        boolean isPasswordRight = userService.isPasswordRight(user.getEmailAddress(), user.getPassword());

        model.addAttribute("isPasswordRight", isPasswordRight);
        model.addAttribute("name", user.getUserName());

        if (isPasswordRight) {
            // FIXME: UID should be generated automatically and check
            Cookie cookie = new Cookie("uid", "12345678");
            response.addCookie(cookie);
            return "index";
        }

        return "login";
    }

}
