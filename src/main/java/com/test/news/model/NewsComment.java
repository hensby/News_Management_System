package com.test.news.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hengchao
 * @date 2019/10/30 17:42
 */
@Data
@Entity
public class NewsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;//id
    private Integer newsId; //id
    private String content;//contents
    private Boolean isShow;
    private Integer userId;
    private Integer toUserId;
    private Date date;

    @Transient
    private String userName;
    @Transient
    private String tile;

}
