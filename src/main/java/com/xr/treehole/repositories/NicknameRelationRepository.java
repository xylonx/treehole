package com.xr.treehole.repositories;

import com.xr.treehole.entity.NicknameRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NicknameRelationRepository extends JpaRepository<NicknameRelation, Integer> {
  Optional<NicknameRelation> findByEmailHashAndRootNodeId(String emailHash, String rootNodeId);
}
