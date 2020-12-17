package com.xr.treehole.controller;

import com.xr.treehole.config.selfdef.MailConfig;
import com.xr.treehole.middleware.jwt.JwtUtils;
import com.xr.treehole.repositories.RegisterCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;



@Controller
public class PageRouteController {

    @Autowired
    MailConfig mailConfig;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RegisterCodeRepository registerCodeRepository;

    @GetMapping(path = "/")
    public String routeDefault() {
        return "redirect:/index";
    }

    @GetMapping(path = "/test")
    public String routeTest() {
        return "";
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
