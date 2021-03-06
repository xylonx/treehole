package com.xr.treehole.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Data
public class Node {

    @Id
    private String nodeId;

    private String nodeTitle;

    private String nodeContent;

    private int thumbUpNumber;

    private long publishTime;

    private String publisherHash;

    private String publisherNickname;

    private int nodeDepth;

    private String parentNodeId;

    private String rootNodeId;

    @Transient
    private boolean hasThumbedUp;

    @Transient
    private int repliesNumber;
}
