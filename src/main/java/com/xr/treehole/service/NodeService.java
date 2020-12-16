package com.xr.treehole.service;

import com.xr.treehole.entity.User;
import com.xr.treehole.entity.Node;
import com.xr.treehole.repositories.NodeRepository;
import com.xr.treehole.repositories.NodeTreePathRepository;
import com.xr.treehole.util.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NodeService {

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    NodeTreePathRepository nodeTreePathRepository;

    public Node saveNode(Node node) {
        return nodeRepository.save(node);
    }

    public Optional<Node> getNodeById(String id) {
        return nodeRepository.findById(id);
    }

    public List<Node> getNodeListWithinRange(int start, int end) {
        List<Node> nodes = nodeRepository.findAll();
        if (start >= nodes.size()) return null;
        return nodes.subList(start, Math.min(nodes.size(), end));
    }
}
