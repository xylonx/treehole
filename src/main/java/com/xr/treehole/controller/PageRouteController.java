package com.xr.treehole.controller;

import com.xr.treehole.config.selfdef.MailConfig;
import com.xr.treehole.middleware.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping(path = "/")
    public String routeDefault() {
        return "redirect:/index";
    }

    @GetMapping(path = "/test")
    public String routeTest() {
        return "redirect:/index";
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
