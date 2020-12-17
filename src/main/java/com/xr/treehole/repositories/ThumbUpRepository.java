package com.xr.treehole.repositories;

import java.util.Optional;

import com.xr.treehole.entity.ThumbUp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ThumbUpRepository extends JpaRepository<ThumbUp, Integer> {
  Optional<ThumbUp> findOneByEmailHashAndNodeId(String emailHash, String NodeId);
}
