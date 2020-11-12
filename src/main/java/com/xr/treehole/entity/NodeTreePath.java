package com.xr.treehole.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTreePath {

    @Id
    private String id;

    private String ancestorId;

    private String descendantId;
}
