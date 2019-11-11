package com.test.news.service;
/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */


import com.fasterxml.jackson.databind.JsonSerializer;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchAPI;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchManager;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.Freshness;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsArticle;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsModel;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsTopic;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.SafeSearch;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.TrendingTopics;
import com.test.news.model.News;
import com.test.news.model.NewsType;
import com.test.news.service.NewsService;
import com.test.news.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@Service
public class BingNewsSearchService {

    @Autowired
    private NewsTypeService newsTypeService;

    @Autowired
    private NewsService newsService;

    /**
     * Main function which runs the actual sample.
     *
     * @param client instance of the Bing News Search API client
     * @return true if sample runs successfully
     */
    public NewsModel runSample(BingNewsSearchAPI client) {
        try {

            //=============================================================
            // This will search most recent news for (Artificial Intelligence) with freshness and sort-by parameters then
            //  verify number of results and print out totalEstimatedMatches, name, url, description, published time and
            //  name of provider of the first news result

            System.out.println("Search most recent news for query \"UT Arlington\" with freshness and sortBy");
            NewsModel newsResults = client.bingNews().search()
                    .withQuery("UT Arlington")
                    .withMarket("en-us")
                    .withFreshness(Freshness.WEEK)
                    .withCount(50)
                    .withSortBy("Date")
                    .execute();

            SaveNewsResult(newsResults);


            //=============================================================
            // This will search category news for movie and TV entertainment with safe search then verify number of results
            //  and print out category, name, url, description, published time and name of provider of the first news result

//            System.out.println("Search category news for movie and TV entertainment with safe search");
//            newsResults = client.bingNews().category()
//                    .withMarket("en-us")
//                    .withCategory("Entertainment_MovieAndTV")
//                    .withSafeSearch(SafeSearch.STRICT)
//                    .execute();
//
//          SaveNewsResult(newsResults);


            //=============================================================
            // This will search news trending topics in Bing then verify number of results and print out name, text of query,
            //  webSearchUrl, newsSearchUrl and image Url of the first news result

            System.out.println("Search news trending topics in Bing");
            TrendingTopics trendingTopics = client.bingNews().trending()
                    .withMarket("en-us")
                    .execute();

            if (trendingTopics != null) {
                if (trendingTopics.value().size() > 0) {
                    NewsTopic firstTopic = trendingTopics.value().get(0);

                    System.out.println(String.format("Trending topics count: %s", trendingTopics.value().size()));
                    System.out.println(String.format("First topic name: %s", firstTopic.name()));
                    System.out.println(String.format("First topic query: %s", firstTopic.query().text()));
                    System.out.println(String.format("First topic image url: %s", firstTopic.image().url()));
                    System.out.println(String.format("First topic webSearchUrl: %s", firstTopic.webSearchUrl()));
                    System.out.println(String.format("First topic newsSearchUrl: %s", firstTopic.newsSearchUrl()));
                } else {
                    System.out.println("Couldn't find news trending topics!");
                }
            } else {
                System.out.println("Didn't see any news trending topics..");
            }
            return newsResults;
//            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
//        return false;
        return null;
    }

    /**
     * Prints the first item in the list of news result list.
     *
     * @param newsResults the news result
     */
    public NewsModel SaveNewsResult(NewsModel newsResults) {

        if (newsResults != null) {
            if (newsResults.value().size() > 0) {
                System.out.println(String.format("News result count: %d", newsResults.value().size()));
                for (int x = 0; x < newsResults.value().size(); x++) {
                    NewsArticle firstNewsResult = newsResults.value().get(x);
                    List list = newsService.findByName(firstNewsResult.name());
                    if (list != null && !list.isEmpty()) {
                        log.info("have a news");

                    } else {
//
                        News news1 = new News();                        //set news from api

                        news1.setTitle(firstNewsResult.name());
                        news1.setAuthor(firstNewsResult.provider().get(0).name());
                        news1.setIsTop(false);
                        news1.setContent(firstNewsResult.url());
                        log.info("category: {}", firstNewsResult.category());
                        String i = firstNewsResult.category();
                        if (i == null || i.equals("null")) {
                            news1.setTypeId(2);
                        } else {
                            List res = newsTypeService.findByName(i);
                            if (res != null && !res.isEmpty()) {
                                news1.setTypeId(newsTypeService.findByName(firstNewsResult.category()).get(0).getId());
                            } else {
                                NewsType newsType = new NewsType();
                                newsType.setName(firstNewsResult.category());
                                newsTypeService.save(newsType);
                                news1.setTypeId(newsTypeService.findByName(newsType.getName()).get(0).getId());
                            }
                        }
                        newsService.save(news1);
                    }
                }
            } else {
                System.out.println("Couldn't find news results!");
            }
        } else {
            System.out.println("Didn't see any news result data..");

        }
        return newsResults;
    }

    /**
     * Main entry point.
     *
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate
            // Set the BING_SEARCH_V7_SUBSCRIPTION_KEY environment variable with your subscription key,
            // then reopen your command prompt or IDE. If not, you may get an API key not found exception.
            final String subscriptionKey = "048f8f4715ce4aa8bef4eded60bd44e2";

            BingNewsSearchAPI bingNewsSearchAPIClient = BingNewsSearchManager.authenticate(subscriptionKey);

            BingNewsSearchService newsSearchService = new BingNewsSearchService();
            newsSearchService.runSample(bingNewsSearchAPIClient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

