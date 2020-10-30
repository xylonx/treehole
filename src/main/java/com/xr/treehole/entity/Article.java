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
    private long ArticleId;

    private String ArticleTitle;

    private String ArticleContent;

    private int ThumbUpNumber;

    private String ArticleSummary;

    private long ArticlePublishTime;

    private long ArticlePublisherId;
}
