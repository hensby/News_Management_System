package com.test.news.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Wuxuan Yang
 * @date 2019/9/20 17:47
 */
@Data
@Entity
public class SystemUser {           //using lombok to create get(),set() auto
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String pwd;


}
