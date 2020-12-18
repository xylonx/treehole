package com.xr.treehole.entity;

import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class NicknameRelation {
  @Id  
  @GeneratedValue(strategy = GenerationType.AUTO)  
  private int id; 

  private String emailHash;

  private String rootNodeId;

  private String nickname;
}
