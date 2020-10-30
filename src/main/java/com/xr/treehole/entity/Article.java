package com.xr.treehole.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue
    private int ArticleId;

    private String Content;

    private int ThumbUpNumber;

    private int ThumbDownNumber;

}
