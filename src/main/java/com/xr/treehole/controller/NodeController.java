package com.xr.treehole.controller;

import java.util.List;
import java.util.Optional;

import com.xr.treehole.service.NodeService;
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

  @GetMapping(path = "/p/node")
  public ModelAndView routeNode(@RequestParam(name="id", required=true)String nodeId) {
      Optional<Node> node = nodeService.getNodeById(nodeId);
      ModelAndView modelAndView = new ModelAndView();
      if (node.isEmpty()) {
        modelAndView.setViewName("error");
        return modelAndView;
      }
      modelAndView.setViewName("node");
      modelAndView.addObject("node", node.get());
      return modelAndView;
  }

    @GetMapping(path = "/index")
    public ModelAndView routeIndex(@RequestParam(name="page", required=false, defaultValue="0") int page) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("index");
      final int PAGE_LEN = 10;
      List<Node> nodes = nodeService.getNodeListWithinRange(page*PAGE_LEN, (PAGE_LEN+1)*10);
      modelAndView.addObject("nodes", nodes);
      return modelAndView;
    }

    @PostMapping(path = "/p/node/new", consumes = "application/x-www-form-urlencoded")
    public String postNode(Node node) {
      System.out.println(node);
      node = nodeService.saveNode(node);
      return "redirect:/p/node?id=" + node.getNodeId(); 
    }
}
