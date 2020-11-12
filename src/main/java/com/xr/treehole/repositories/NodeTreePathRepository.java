package com.xr.treehole.repositories;

import com.xr.treehole.entity.NodeTreePath;
import org.hibernate.annotations.OnDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeTreePathRepository extends JpaRepository<NodeTreePath, String> {

     List<NodeTreePath> findAllByAncestorId(String ancestorId);

    List<NodeTreePath> findAllByDescendantId(String descendantId);

    void deleteNodeTreePathsByAncestorId(String ancestorId);

}
