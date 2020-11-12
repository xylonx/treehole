package com.xr.treehole.controller;

import com.xr.treehole.config.selfdef.MailConfig;
import com.xr.treehole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/user")
@EnableConfigurationProperties(MailConfig.class)
public class UserController {

    @Autowired
    UserService userService;


    // FIXME: rebuilding...
//    @PostMapping(path = "", consumes = "application/x-www-form-urlencoded")
//    public String getUserHomepage(String status, User user, Model model, HttpServletResponse response) {
//
//        String returnPage;
//
//        if (status.equals("login")) {
//            returnPage = userLogin(user, model, response);
//        } else if (status.equals("register")) {
//            returnPage = userRegister(user, model, response);
//        } else {
//            returnPage = "error";
//        }
//
//        user = userService.getUserByEmail(user.getEmailAddress());
//        model.addAttribute("user", user);
//
//        return returnPage;
//    }

    // FIXME: rebuilding
    /**
     * send register data to backend ad route to user main page if no error occur
     *
     * @param user     user login info, containing email_address and password
     * @param model    thymeleaf unique object to bind data
     * @param response set to add cookie for user authorized
     * @return user homepage
     */
//    public String userRegister(User user, Model model, HttpServletResponse response) {
//
//        boolean isUserExist = userService.isUserExist(user.getEmailAddress());
//
//        model.addAttribute("isUserExist", isUserExist);
//
//        if (!isUserExist) {
//
//            userService.saveUser(user);
//
//            // FIXME: UID should be generated automatically and check
//            Cookie cookie = new Cookie("uid", "12345678");
//            response.addCookie(cookie);
//
//            return "userHomepage";
//        }
//
//        return "register";
//    }


    /**
     * send login data to backend and reroute to index page
     *
     * @param user     user login info, containing email_address and password
     * @param model    thymeleaf unique object to bind data
     * @param response set to add cookie for user authorized
     * @return user homepage or error page
     * return "index";
     */
    // FIXME: rebuilding....
//    public String userLogin(User user, Model model, HttpServletResponse response) {
//
//        boolean isPasswordRight = userService.isPasswordRight(user.getEmailAddress(), user.getPassword());
//
//        model.addAttribute("isPasswordRight", isPasswordRight);
//        model.addAttribute("name", user.getUserName());
//
//        if (isPasswordRight) {
//            // FIXME: UID should be generated automatically and check
//            Cookie cookie = new Cookie("uid", "12345678");
//            response.addCookie(cookie);
//            return "userHomepage";
//        }
//
//        return "login";
//    }

}
