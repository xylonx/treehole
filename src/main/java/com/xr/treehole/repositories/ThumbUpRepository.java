package com.xr.treehole.repositories;

import java.util.List;

import com.xr.treehole.entity.ThumbUp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbUpRepository extends JpaRepository<ThumbUp, Integer> {
  List<ThumbUp> findByEmailHashAndNodeId(String emailHash, String NodeId);
}
