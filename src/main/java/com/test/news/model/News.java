package com.test.news.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author hengchao wang
 * @date 2019/10/3 17:36
 */
@Data
@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;//id
    private String title;//title
    @Column(columnDefinition = "text")
    private String content;//contents
    private Date date;//time
    private Integer num;//comment number
    private Integer typeId;//type ID
    private String author;
    private Boolean isTop;

    @Transient
    private String typeName;

    @Transient
    private List<NewsTip> tips;

    @Transient
    private List<NewsComment> comments;

}
