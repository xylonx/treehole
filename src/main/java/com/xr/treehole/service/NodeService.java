package com.xr.treehole.service;

import com.xr.treehole.entity.NodeTreePath;
import com.xr.treehole.entity.User;
import com.xr.treehole.entity.Node;
import com.xr.treehole.repositories.NodeRepository;
import com.xr.treehole.repositories.NodeTreePathRepository;
import com.xr.treehole.util.Encrypt;
import com.xr.treehole.util.GenerateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class NodeService {

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    NodeTreePathRepository nodeTreePathRepository;

    // means it is comment
    public Node saveNode(String parentId, Node node) {

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


    // means it is an article
    public Node saveNode(Node node){

        String nodeId = GenerateId.GenerateIdWithTime(node.getNodeContent(), node.getPublisherHash());
        node.setNodeId(nodeId);

        // the depth of article is 0
        node.setNodeDepth(0);

        nodeRepository.save(node);
        String ntpId = GenerateId.GenerateIdByItems(nodeId, nodeId);
        NodeTreePath nodeTreePath = new NodeTreePath(ntpId, nodeId, nodeId);

        nodeTreePathRepository.save(nodeTreePath);

        return node;
    }

    public void deleteNode(String nodeId) {
        nodeTreePathRepository.deleteNodeTreePathsByAncestorId(nodeId);
        nodeRepository.deleteById(nodeId);
    }

    public List<Node> GetAllSubNodes(String nodeId) {

        List<NodeTreePath> treePaths = nodeTreePathRepository.findAllByAncestorId(nodeId);
        ArrayList<String> nodeIds = new ArrayList<>();

        for (NodeTreePath nodeTreePath : treePaths){
            nodeIds.add(nodeTreePath.getDescendantId());
        }

        List<Node> nodes = nodeRepository.findAllById(nodeIds);

        nodes.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Long.compare(o1.getPublishTime(), o2.getPublishTime());
            }
        });

        return nodes;
    }

    public Optional<Node> getNodeById(String id) {
        return nodeRepository.findById(id);
    }

    public List<Node> getPostsWithinRange(int start, int end) {
        List<Node> nodes = nodeRepository.findByNodeDepthOrderByPublishTimeDesc(0);
        if (start >= nodes.size()) return null;
        return nodes.subList(start, Math.min(nodes.size(), end));
    }
}
