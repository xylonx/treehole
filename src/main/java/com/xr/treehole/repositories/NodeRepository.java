package com.xr.treehole.repositories;

import java.util.List;

import com.xr.treehole.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, String> {
  List<Node> findByNodeDepthOrderByPublishTimeDesc(int nodeDepth);
}
