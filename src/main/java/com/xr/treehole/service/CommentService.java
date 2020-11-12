package com.xr.treehole.service;

import com.xr.treehole.entity.Node;
import com.xr.treehole.entity.NodeTreePath;
import com.xr.treehole.repositories.NodeRepository;
import com.xr.treehole.repositories.NodeTreePathRepository;
import com.xr.treehole.util.GenerateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    NodeTreePathRepository nodeTreePathRepository;

    public Node SaveComment(String parentId, Node node) {

        String nodeId = GenerateId.GenerateIdWithTime(node.getNodeContent(), node.getPublisherHash());
        node.setNodeId(nodeId);

        Node parent = nodeRepository.getOne(parentId);
        node.setNodeDepth(parent.getNodeDepth() + 1);

        nodeRepository.save(node);


        // build up and save the new associations
        List<NodeTreePath> paths = nodeTreePathRepository.findAllByDescendantId(parentId);

        List<NodeTreePath> savedNodeTP = new ArrayList<>();
        for (NodeTreePath path : paths) {

            String ntpId = GenerateId.GenerateIdByItems(path.getAncestorId(), nodeId);

            NodeTreePath nodeTreePath = new NodeTreePath(ntpId, path.getAncestorId(), nodeId);
            savedNodeTP.add(nodeTreePath);
        }

        nodeTreePathRepository.saveAll(savedNodeTP);

        return node;
    }

    public void DeleteComment(String commentId) {
        nodeTreePathRepository.deleteNodeTreePathsByAncestorId(commentId);
    }

    public void GetCommentTree(String commentId) {

    }
}
