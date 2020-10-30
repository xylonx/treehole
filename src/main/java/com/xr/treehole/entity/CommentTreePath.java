package com.xr.treehole.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class CommentTreePath {

    @Id
    @GeneratedValue
    private String id;

    private int ancestorId;

    private int descendantId;

    private int depth;
}
