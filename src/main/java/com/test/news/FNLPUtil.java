package com.test.news;

import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.util.exception.LoadModelException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Hengchao on 2019/9/24.
 */
@Configuration
public class FNLPUtil {

    @Value(value = "${fnlp.path}")
    String path;

    @Bean
    public CNFactory cnFactory(){

        try {
            return CNFactory.getInstance(path);
        } catch (LoadModelException e) {
            e.printStackTrace();
        }

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
