package com.xr.treehole.service;

import com.xr.treehole.entity.NodeTreePath;
import com.xr.treehole.entity.ThumbUp;
import com.xr.treehole.entity.User;
import com.xr.treehole.entity.NicknameRelation;
import com.xr.treehole.entity.Node;
import com.xr.treehole.repositories.NicknameRelationRepository;
import com.xr.treehole.repositories.NodeRepository;
import com.xr.treehole.repositories.NodeTreePathRepository;
import com.xr.treehole.repositories.ThumbUpRepository;
import com.xr.treehole.util.Encrypt;
import com.xr.treehole.util.GenerateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class NodeService {
    @Autowired
    NicknameRelationRepository nicknameRelationRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    NodeTreePathRepository nodeTreePathRepository;

    @Autowired
    ThumbUpRepository thumbUpRepository;

    public boolean hasThumbedUpNode(User user, Node node) {
        List<ThumbUp> tb = thumbUpRepository.findByEmailHashAndNodeId(user.getEmailHash(), node.getNodeId());
        return !tb.isEmpty();
    }

    public boolean thumbUpNode(User user, Node node) {
        if (hasThumbedUpNode(user, node)) return false;
        Optional<Node> optionalNodeInDb = nodeRepository.findById(node.getNodeId());
        if (optionalNodeInDb.isEmpty()) return false;
        // else the node is real

        Node nodeInDb = optionalNodeInDb.get();
        nodeInDb.setThumbUpNumber(nodeInDb.getThumbUpNumber() + 1);
        nodeRepository.save(nodeInDb);

        ThumbUp tb = new ThumbUp();
        tb.setEmailHash(user.getEmailHash());
        tb.setNodeId(node.getNodeId());
        thumbUpRepository.save(tb);

        return true;
    }

    // means it is comment
    public Node saveNode(String parentId, Node node) {
        String nodeTitle = node.getNodeTitle();
        if (nodeTitle == null || nodeTitle.isEmpty()) {
            node.setNodeTitle("未命题");
        }

        String nodeId = GenerateId.GenerateIdWithTime(node.getNodeContent(), node.getPublisherHash());
        node.setNodeId(nodeId);
        node.setParentNodeId(parentId);
        node.setRootNodeId(nodeId);

        int nodeDepth = 0;
        long now = Instant.now().getEpochSecond();
        node.setPublishTime(now);

        List<NodeTreePath> savedNodeTP = new ArrayList<>();

        // self circle
        String selfCircleId = GenerateId.GenerateIdByItems(nodeId, nodeId);
        NodeTreePath selfCircle = new NodeTreePath(selfCircleId, nodeId, nodeId);
        savedNodeTP.add(selfCircle);

        if (parentId != null) {
            Optional<Node> optionalNode = nodeRepository.findById(parentId);
            if (optionalNode.isEmpty()) {
                return null;
            }
            Node parent = optionalNode.get();
            nodeDepth = parent.getNodeDepth() + 1;
            node.setRootNodeId(parent.getRootNodeId());

            List<NodeTreePath> paths = nodeTreePathRepository.findAllByDescendantId(parentId);
            for (NodeTreePath path : paths) {

                String ntpId = GenerateId.GenerateIdByItems(path.getAncestorId(), nodeId);

                NodeTreePath nodeTreePath = new NodeTreePath(ntpId, path.getAncestorId(), nodeId);
                savedNodeTP.add(nodeTreePath);
            }
        }

        node.setNodeDepth(nodeDepth);

        // set nickname

        Optional<NicknameRelation> nicknameRelation = nicknameRelationRepository.findByEmailHashAndRootNodeId(node.getPublisherHash(), node.getRootNodeId());
        if (nicknameRelation.isPresent()) {
            node.setPublisherNickname(nicknameRelation.get().getNickname());
        } else {
            NicknameRelation nr = new NicknameRelation();
            nr.setEmailHash(node.getPublisherHash());
            nr.setRootNodeId(node.getRootNodeId());
            nr.setNickname(GenerateId.GenerateNickname());
            node.setPublisherNickname(nr.getNickname());
            nicknameRelationRepository.save(nr);
        }

        nodeRepository.save(node);

        nodeTreePathRepository.saveAll(savedNodeTP);

        return node;
    }

    // means it is an article
    @Deprecated
    public Node saveNode(Node node) {

        String nodeId = GenerateId.GenerateIdWithTime(node.getNodeContent(), node.getPublisherHash());
        node.setNodeId(nodeId);

        long now = Instant.now().getEpochSecond();
        node.setPublishTime(now);

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

        for (NodeTreePath nodeTreePath : treePaths) {
            nodeIds.add(nodeTreePath.getDescendantId());
        }

        List<Node> nodes = nodeRepository.findAllById(nodeIds);

        for (Node node : nodes) {
            node.setRepliesNumber(nodeTreePathRepository.findAllByAncestorId(node.getNodeId()).size());
        }

        nodes.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Long.compare(o1.getPublishTime(), o2.getPublishTime());
            }
        });

        return nodes;
    }

    public Optional<Node> getNodeById(String id) {
        Optional<Node> node = nodeRepository.findById(id);
        if (node.isPresent()) {
            node.get().setRepliesNumber(nodeTreePathRepository.findAllByAncestorId(node.get().getNodeId()).size());
        }
        return node;
    }

    public List<Node> getPostsWithinRange(int start, int end) {
        List<Node> nodes = nodeRepository.findByNodeDepthOrderByPublishTimeDesc(0);
        if (start >= nodes.size()) return null;
        nodes = nodes.subList(start, Math.min(nodes.size(), end));
        for (Node node : nodes) {
            node.setRepliesNumber(nodeTreePathRepository.findAllByAncestorId(node.getNodeId()).size());
        }
        return nodes;
    }

    public List<Node> getHotestPostsWithinRange(int start, int end) {
        List<Node> nodes = nodeRepository.findByNodeDepthOrderByThumbUpNumberDesc(0);
        if (start >= nodes.size()) return null;
        nodes = nodes.subList(start, Math.min(nodes.size(), end));
        for (Node node : nodes) {
            node.setRepliesNumber(nodeTreePathRepository.findAllByAncestorId(node.getNodeId()).size());
        }
        return nodes;
    }

    public List<Node> getNodeByUser(User user) {
        List<Node> nodes = nodeRepository.findByPublisherHash(user.getEmailHash());
        for (Node node : nodes) {
            node.setRepliesNumber(nodeTreePathRepository.findAllByAncestorId(node.getNodeId()).size());
        }
        return nodes;
    }

    public List<Node> getArticleNodeList(String emailHashed) {
        return nodeRepository.getAllByNodeDepthAndPublisherHash(0, emailHashed);
    }
}
