package com.test.news;

import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchAPI;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchManager;
import com.test.news.dao.NewsRepository;
import com.test.news.dao.NewsTypeRepository;
import com.test.news.service.BingNewsSearchService;
import com.test.news.service.NewsService;
import com.test.news.service.NewsTypeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2019/9/20.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class APITests {

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

//        log.info("newsRepository: {}",newsTypeService.findByName("Sport"));
//        log.info("newsRepository: {}",newsRepository.findByTitle("TRAINING SOCIAL WORKERS IN FIGHT AGAINST OPIOIDS"));
//        log.info("newsRepository: {}",newsTypeRepository.findByName("Sport"));
        final String subscriptionKey = "048f8f4715ce4aa8bef4eded60bd44e2";
        BingNewsSearchAPI bingNewsSearchAPIClient = BingNewsSearchManager.authenticate(subscriptionKey);

        bingNewsSearchService.runSample(bingNewsSearchAPIClient);
    }
}

