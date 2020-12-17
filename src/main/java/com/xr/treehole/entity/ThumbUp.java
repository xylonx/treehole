package com.xr.treehole.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class ThumbUp {
  @Id  
  @GeneratedValue(strategy = GenerationType.AUTO)  
  private int id; 

  private String emailHash;

  private String nodeId;
}
