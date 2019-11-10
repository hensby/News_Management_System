package com.test.news.controller;

import com.paralleldots.paralleldots.App;
import com.test.news.LoginRequired;
import com.test.news.model.NewsComment;
import com.test.news.model.User;
import com.test.news.service.NewsCommentService;
import com.test.news.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hengchao on 2019/10/24.
 */
@Slf4j
@Controller
@RequestMapping("comment")
public class NewsCommentController {

    @Autowired
    NewsCommentService newsCommentService;

    @Autowired
    UserService userService;

    @LoginRequired
    @RequestMapping("list")
    public String list(Model model) {
        List<NewsComment> list = newsCommentService.findList();
        model.addAttribute("list", list);
        return "comment/list";
    }

    @LoginRequired
    @RequestMapping("del")
    @ResponseBody
    public void del(Integer id) {
        newsCommentService.del(id);
    }


    @RequestMapping("add")
    @ResponseBody
    public int add(String content, Integer newsId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
//            user = userService.findById(77);
            return 0;
        }

        if (newsCommentService.checkComment(content)) {
            NewsComment comment = new NewsComment();
            comment.setUserId(user.getId());
            comment.setContent(content);
            comment.setNewsId(newsId);
            comment.setIsShow(true);
            comment.setDate(new Date());
            newsCommentService.save(comment);
            return 1;
        } else {
            return 2;
        }
    }


    @RequestMapping("check")
    @ResponseBody
    public void check(String comment) {
        try {
            App pd = new App("HEhkJRQ68O4zUw9KeTxpBHZwTJjZYYw6hWBAUh4NnbI");
            // for single sentences
            String abuse = pd.abuse("fuck");
            System.out.println(abuse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
