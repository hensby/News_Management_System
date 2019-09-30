package com.test.news.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author zengliming
 * @date 2018/3/20 17:46
 */
@Data
@Entity
public class User {                         //using lombok to create get(),set() auto
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String pwd;
    private Date createTime;
    private Date updateTime;
}
