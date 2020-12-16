package com.xr.treehole.controller;

import java.util.Optional;

import com.xr.treehole.service.NodeService;
import com.xr.treehole.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
      //modelAndView.addObject("node", node);
      return modelAndView;
  }
}
