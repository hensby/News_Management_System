package com.test.news;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchAPI;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchManager;
import com.test.news.dao.NewsRepository;
import com.test.news.dao.NewsTypeRepository;
import com.test.news.service.BingNewsSearchService;
import com.test.news.service.NewsService;
import com.test.news.service.NewsTypeService;
import lombok.extern.slf4j.Slf4j;
import org.fnlp.nlp.cn.CNFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * Created by Administrator on 2019/9/20.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATests {

    @Autowired
    NewsService newsService;

    @Autowired
    NewsTypeService newsTypeService;

    @Autowired
    NewsTypeRepository newsTypeRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    BingNewsSearchService bingNewsSearchService;

    @Test
    public void test1() throws Exception {
//        // 创建中文处理工厂对象，并使用“models”目录下的模型文件初始化
////        CNFactory factory = CNFactory.getInstance("F:\\fnlp\\models");
////
////        // 使用分词器对中文句子进行分词，得到分词结果
////        String[] words = factory.seg("关注自然语言处理、语音识别、深度学习等方向的前沿技术和业界动态。");
////
////        // 打印分词结果
////        for (String word : words) {
////            System.out.print(word + " ");
////        }
////        System.out.println();
        log.info("newsRepository: {}", newsTypeService.findByName("Sport"));
        log.info("newsRepository: {}", newsRepository.findByTitle("TRAINING SOCIAL WORKERS IN FIGHT AGAINST OPIOIDS"));
        log.info("newsRepository: {}", newsTypeRepository.findByName("Sport"));

    }
}


//        // 创建中文处理工厂对象，并使用“models”目录下的模型文件初始化
//        CNFactory factory = CNFactory.getInstance("F:\\fnlp\\models");
//
//        // 使用分词器对中文句子进行分词，得到分词结果
//        String[] words = factory.seg("关注自然语言处理、语音识别、深度学习等方向的前沿技术和业界动态。");
//
//        // 打印分词结果
//        for (String word : words) {
//            System.out.print(word + " ");
//        }
//        System.out.println();
//    }
//}
