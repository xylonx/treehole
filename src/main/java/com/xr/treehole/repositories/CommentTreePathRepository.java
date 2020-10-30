package com.xr.treehole.repositories;

import com.xylon.springstudy.entity.CommentTreePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentTreePathRepository extends JpaRepository<CommentTreePath, Integer> {
}
