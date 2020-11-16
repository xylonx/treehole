package com.xr.treehole.controller;

import com.xr.treehole.config.selfdef.MailConfig;
import com.xr.treehole.middleware.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class PageRouteController {

    @Autowired
    MailConfig mailConfig;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping(path = "/index")
    public ModelAndView routeIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(path = "/test")
    public String routeTest() {
        return "redirect:/index";
    }

    /**
     * route to user register page
     *
     * @return register page
     */
    @GetMapping(path = "/user/register")
    public ModelAndView routeUserRegisterPage() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("register");

        modelAndView.addObject("isUserExist", null);

        return modelAndView;
    }


    /**
     * route to the user login page
     *
     * @return login page
     */
    @GetMapping(path = "/user/login")
    public ModelAndView routeLoginPage() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("isPasswordRight", null);

        modelAndView.setViewName("login");

        return modelAndView;
    }

    /**
     * simply route to the error page
     *
     * @return error page
     */
    @GetMapping(path = "/error")
    public ModelAndView routeErrorPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("error");

        return modelAndView;
    }

    @GetMapping(path = "/comment")
    public ModelAndView routeCommentPage() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("comment");

        return modelAndView;
    }

}
