# Tree Hole

## 基本信息描述

* 主题：树洞



## 功能分析

### 词语来源

树洞这种说法是来源于一个童话故事《皇帝长了驴耳朵》，里面讲得是有一个国王长了一对驴耳朵，每年都会有不同的理发师来给他理发，是因为每个给他理发的人看到他的驴耳朵都会忍不住告诉别人，之后就被国王恼怒杀掉。这天有一个理发匠理发后不想被杀掉，就努力的不去将这件事告诉别人，但是秘密藏在心里十分辛苦，都快要憋不住时了，就跑到山上对着一个大树洞说出了这个秘密。顿时就觉得一阵畅快，而理发匠就保住了性命。

### 意义

在网络上将一些平时不能对人说的话通过一些隐晦的方式写的日志里，发在网上或者别的地方，这些地方都可以称为树洞，树洞即为承受秘密的地方。

### 功能

* 用户相关：
  * 注册
  * 登录
* 树洞特性：
  * 分页查看所有匿名贴
  * 查看单独匿名帖
  * 发表匿名帖
  * 评论
  * 点赞



## 系统设计

### 技术栈

* 数据库：MySQL
* ORM框架：Hibernate
* Java框架：Spring Boot
* 页面渲染模板引擎：Thymeleaf



### 架构

采用经典MVC架构。

#### Entity（Model）

数据库中包括三个部分：用户、匿名树洞贴、评论

##### 用户：User

|     属性名     |   类型   |                           备注                           |
| :------------: | :------: | :------------------------------------------------------: |
|   `Password`   | `String` |                         用户密码                         |
| `EmailHash` | `String PrimaryKey` | 用户邮箱Hash，用于申请账号，同时亦可作为唯一标识符。用于登录 |

因为树洞主打匿名，故用户仅保留最基本信息。无头像、个性签名等相关内容



##### 匿名贴：Article

|        属性名        |   类型   |                       备注                       |
| :------------------: | :------: | :----------------------------------------------: |
|     `ArticleId`      |  `long`  |               数据库文章唯一标识符               |
|    `ArticleTitle`    | `String` |                     文章标题                     |
|   `ArticleContent`   | `String` |                     文章内容                     |
|   `ThumbUpNumber`    |  `int`   |                      点赞数                      |
| `ArticlePublishTime` |  `long`  |          采用UnixTimeStamp方式存储时间           |
| `ArticlePublisherHs` | `String` |                 文章发布者Hash                   |



##### 评论：Comment && CommentTreePath

评论采取闭包表的方式保存。Comment中存储具体内容，而CommentTreePath中存储结点关系。

具体可参考[xylonx的博客](http://www.xylonx.com/2020/10/14/%E9%97%AD%E5%8C%85%E8%A1%A8/)

###### Comment

|      属性名      |   类型   |                           备注                            |
| :--------------: | :------: | :-------------------------------------------------------: |
|   `CommentId`    |  `long`  |                   数据库评论唯一标识符                    |
| `CommentContent` | `String` |                         评论内容                          |
|     `Depth`      |  `int`   | 存储结点深度，用于得到直接的父子关系<br />定义文章深度为0 |

###### CommentTreePath

|       属性名        |  类型  |       备注       |
| :-----------------: | :----: | :--------------: |
| `CommenTreePathtId` | `long` | 数据库唯一标识符 |
|    `AncestorId`     | `long` |    祖先结点ID    |
|   `DescendantId`    | `long` |    后代结点ID    |



#### Controller

接口文档采用Swagger。具体参考[swaggerAPI](https://github.com/xylonx/treehole/blob/master/API-swagger.yaml)



#### View

采用Thymeleaf模板引擎

主要分为三大界面

* 用户界面：
  * 注册
  * 用户信息
* 主界面：
  * 目录
  * 详情
