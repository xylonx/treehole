package com.xr.treehole.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageRouteController {


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

}
