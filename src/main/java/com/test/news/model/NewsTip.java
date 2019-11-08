package com.test.news.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author zengliming
 * @date 2018/3/20 17:36
 */
@Data
@Entity
public class NewsTip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;//id
    private Integer newsId;
    private String tip;//by nlp model
}
