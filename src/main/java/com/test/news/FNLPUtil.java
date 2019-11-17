package com.test.news;

import lombok.extern.slf4j.Slf4j;
import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.util.exception.LoadModelException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Hengchao on 2019/9/24.
 */
@Slf4j
@Configuration
public class FNLPUtil {

    @Value(value = "${fnlp.path}")
    String path;

    @Bean
    public CNFactory cnFactory() {

//        try {
//            return CNFactory.getInstance(path);
//        } catch (LoadModelException e) {
//            log.error("FNLPUtil: CNFactory.getInstance", e);
//        }

        return null;
    }

//    public static CNFactory factory = null;
//    static {
//        try {
//            factory =  CNFactory.getInstance("../../models");
//        } catch (LoadModelException e) {
//            e.printStackTrace();
//        }
//    }

}
