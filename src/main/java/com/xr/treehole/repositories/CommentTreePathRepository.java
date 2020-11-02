package com.xr.treehole.repositories;


import com.xr.treehole.entity.CommentTreePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentTreePathRepository extends JpaRepository<CommentTreePath, Integer> {
}
