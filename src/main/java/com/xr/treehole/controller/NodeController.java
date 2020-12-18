package com.xr.treehole.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.xr.treehole.service.NodeService;
import com.xr.treehole.service.UserService;
import com.xr.treehole.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NodeController {

  @Autowired
  NodeService nodeService;

  @Autowired
  UserService userService;

  @GetMapping(path = "/index")
  public ModelAndView routeIndex(@RequestParam(name = "page", required = false, defaultValue = "0") int page, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");
    final int PAGE_LEN = 10;
    List<Node> nodes = nodeService.getPostsWithinRange(page * PAGE_LEN, (PAGE_LEN + 1) * 10);
    fillHasThumbedUp(userService.getCurrentUser(request), nodes);
    modelAndView.addObject("nodes", nodes);
    return modelAndView;
  }

  @GetMapping(path = "/p/userHomepage")
  public ModelAndView routeHomepage(@RequestParam(name = "page", required = false, defaultValue = "0") int page, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");
    final int PAGE_LEN = 100;
    List<Node> nodes = nodeService.getNodeByUser(userService.getCurrentUser(request));
    nodes = nodes.subList(0, Math.min(nodes.size(), PAGE_LEN));
    fillHasThumbedUp(userService.getCurrentUser(request), nodes);
    modelAndView.addObject("nodes", nodes);
    return modelAndView;
  }
  
  @GetMapping(path = "/p/node/hotest")
  public ModelAndView routeHotest(@RequestParam(name = "page", required = false, defaultValue = "0") int page, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");
    final int PAGE_LEN = 10;
    List<Node> nodes = nodeService.getHotestPostsWithinRange(page * PAGE_LEN, (PAGE_LEN + 1) * 10);
    fillHasThumbedUp(userService.getCurrentUser(request), nodes);
    modelAndView.addObject("nodes", nodes);
    return modelAndView;
  }

  private void fillHasThumbedUp(User user, List<Node> nodes) {
    for (Node node : nodes) {
      node.setHasThumbedUp(nodeService.hasThumbedUpNode(user, node));
    }
  }

  @GetMapping(path = "/p/node")
  public ModelAndView routeNode(@RequestParam(name = "id", required = true) String nodeId, HttpServletRequest request) {
    Optional<Node> node = nodeService.getNodeById(nodeId);
    ModelAndView modelAndView = new ModelAndView();
    if (node.isEmpty()) {
      modelAndView.setViewName("error");
      return modelAndView;
    }
    modelAndView.setViewName("node");
    modelAndView.addObject("node", node.get());
    List<Node> comments = nodeService.GetAllSubNodes(nodeId);
    modelAndView.addObject("comments", comments);
    fillHasThumbedUp(userService.getCurrentUser(request), comments);
    return modelAndView;
  }

  @PostMapping(path = "/p/node/new", consumes = "application/x-www-form-urlencoded")
  public String postNode(Node node, HttpServletRequest request) {
    User user = userService.getCurrentUser(request);
    node.setPublisherHash(user.getEmailHash());
    node = nodeService.saveNode(null, node);
    return "redirect:/p/node?id=" + node.getNodeId();
  }

  @GetMapping(path = "/p/node/reply")
  public ModelAndView routeReply(@RequestParam(name = "id", required = true) String nodeId, HttpServletRequest request) {
    Optional<Node> node = nodeService.getNodeById(nodeId);
    ModelAndView modelAndView = new ModelAndView();
    if (node.isEmpty()) {
      modelAndView.setViewName("error");
      return modelAndView;
    }
    modelAndView.setViewName("node");
    modelAndView.addObject("node", node.get());
    List<Node> comments = new ArrayList<>();
    comments.add(node.get());
    modelAndView.addObject("comments", comments);
    fillHasThumbedUp(userService.getCurrentUser(request), comments);
    return modelAndView;
  }

  @GetMapping(path = "/p/node/thumbUp")
  public String thumbUp(@RequestParam(name = "id", required = true) String nodeId, HttpServletRequest request) {
    Optional<Node> node = nodeService.getNodeById(nodeId);
    if (node.isEmpty()) {
      return "error";
    }
    if (nodeService.thumbUpNode(userService.getCurrentUser(request), node.get())) {
      return "index";
    }
    return "error";
  }

  @PostMapping(path = "/p/node/reply", consumes = "application/x-www-form-urlencoded")
  public String replyNode(Node node, @RequestParam(name="id", required=true) String parentId, HttpServletRequest request) {
    System.out.println(request);
    User user = userService.getCurrentUser(request);
    System.out.println(user);
    node.setPublisherHash(user.getEmailHash());
    node = nodeService.saveNode(parentId, node);
    return "redirect:/p/node?id=" + node.getNodeId();
  }
}
