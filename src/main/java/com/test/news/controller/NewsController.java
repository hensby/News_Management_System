package com.test.news.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchAPI;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchManager;
import com.test.news.service.BingNewsSearchService;
import com.test.news.LoginRequired;
import com.test.news.dao.NewsUserRepository;
import com.test.news.model.News;
import com.test.news.model.NewsUser;
import com.test.news.model.User;
import com.test.news.service.NewsService;
import com.test.news.service.NewsTypeService;
import com.test.news.service.BingNewsSearchService;
import lombok.extern.slf4j.Slf4j;
import org.fnlp.nlp.cn.CNFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hengchao wang
 * @date 2019/10/13 17:12
 */

@Slf4j
@Controller
@RequestMapping("news")
public class NewsController {

//    @Autowired
//    CNFactory cnFactory;

    @Autowired
    NewsService newsService;

    @Autowired
    NewsTypeService newsTypeService;

    @Autowired
    NewsUserRepository newsUserRepository;

    @Autowired
    BingNewsSearchService bingNewsSearchService;

    @LoginRequired
    @RequestMapping("list.htm")
    public String newsList(Model model) throws Exception {

        List<News> news = newsService.findAll();

        log.info("1111111");
        model.addAttribute("newsList", news);
        return "news/list";
    }


    @RequestMapping("info")
    public String info(Model model, Integer id, HttpSession session) throws Exception {
        News news = newsService.findOne(id);

        //read
        User user = (User) session.getAttribute("user");
        if (user != null) {
            NewsUser newsUser = newsUserRepository.findByUserId(user.getId());
            if (newsUser == null) {
                newsUser = new NewsUser();
                newsUser.setNum(1);
            } else {
                newsUser.setNum(newsUser.getNum() + 1);
            }
            newsUser.setUserId(user.getId());
            newsUser.setTypeId(news.getTypeId());
            newsUserRepository.save(newsUser);
        }
        news.setNum(news.getNum() + 1);
//        newsService.save(news);
        model.addAttribute("news", news);
        log.info("2222222");
        if (news.getIsTop() == false) {
//            String str = newsService.getUrl(news.getContent());
//            log.info("content: {}", str);
//            news.setContent(str);
//            newsService.save(news);

            log.info("引起注意"+news.getContent());
            return "news/showNews1";

        }else{
        return "news/showNews";}
    }


    @RequestMapping("listByType")
    public String listByType(Model model, Integer typeId) {
        try {
            //=============================================================
            // Authenticate
            // Set the BING_SEARCH_V7_SUBSCRIPTION_KEY environment variable with your subscription key,
            // then reopen your command prompt or IDE. If not, you may get an API key not found exception.
//            final String subscriptionKey = "048f8f4715ce4aa8bef4eded60bd44e2";
//            BingNewsSearchAPI bingNewsSearchAPIClient = BingNewsSearchManager.authenticate(subscriptionKey);
//            bingNewsSearchService.runSample(bingNewsSearchAPIClient);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        List<News> news = newsService.listByType(typeId);
        model.addAttribute("newsList", news);

        log.info("333333");
        return "news/showList";
    }

    @RequestMapping("newsUser")
    public String newsUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        List<NewsUser> list = newsUserRepository.findByUserIdOrderByNumDesc(user.getId());
        List<Integer> typeIds = new ArrayList<>(3);
        for (NewsUser newsUser : list) {
            if (typeIds.size() < 3) {
                typeIds.add(newsUser.getTypeId());
            }
        }
        List<News> news3 = newsService.findByIstopIn(true);
        List<News> news = newsService.findByTypeIdIn(typeIds);

        List<News> news2 = new ArrayList<>();
        for (News news1 : news3) {
            if (typeIds.size() < 10) {
                news2.add(news1);
            }
        }
        for (News news1 : news) {
            if (typeIds.size() < 10) {
                news2.add(news1);
            }
        }
        model.addAttribute("newsList", news2);
        return "news/showList";
    }

    @RequestMapping("listByTip")
    public String listByTip(Model model, String tip) {
        List<News> news = newsService.listByTip(tip);
        model.addAttribute("newsList", news);
        return "news/showList";
    }

    @LoginRequired
    @RequestMapping("savePage")
    public String savePage(Model model, Integer id) throws Exception {
        News news = new News();
        if (id != null) {
            news = newsService.getOne(id);
            model.addAttribute("id", id);
        }
        model.addAttribute("newsTypes", newsTypeService.findAll());
        model.addAttribute("news", news);
        return "news/info";
    }

    @LoginRequired
    @RequestMapping("save")
    @ResponseBody
    public boolean save(Model model, News news) throws Exception {
        if ("<p><br></p>".equals(news.getContent())) {
            news.setContent(newsService.getOne(news.getId()).getContent());
        }
        news.setIsTop(true);
        newsService.save(news);
        return true;
    }

    @LoginRequired
    @RequestMapping("del")
    @ResponseBody
    public boolean save(Model model, Integer id) throws Exception {
        newsService.delete(id);
        return true;
    }

//    @RequestMapping("tips")
//    @ResponseBody
//    public String[] tips(String title) throws Exception {
////        CNFactory factory =  CNFactory.getInstance("F:\\fnlp\\models");
//        String[] words = cnFactory.seg(title);
//        return words;
//    }

    @RequestMapping("check")
    @ResponseBody
    public String[] check(int newsId){
        News news = newsService.findOne(newsId);
        log.info("url"+news.getContent());
        String[] url = new String[2];
        url[0] = news.getContent();
        url[1] = "1111";
//        return news.getContent();
        return url;
    }

}