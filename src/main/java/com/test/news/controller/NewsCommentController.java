package com.test.news.controller;

import com.test.news.LoginRequired;
import com.test.news.model.NewsComment;
import com.test.news.model.User;
import com.test.news.service.NewsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by Hengchao on 2019/10/24.
 */
@Controller
@RequestMapping("comment")
public class NewsCommentController {

    @Autowired
    NewsCommentService newsCommentService;

    @LoginRequired
    @RequestMapping("list")
    public String list(Model model){
        List<NewsComment> list = newsCommentService.findList();
        model.addAttribute("list",list);
        return "comment/list";
    }

    @LoginRequired
    @RequestMapping("del")
    @ResponseBody
    public void del(Integer id){
        newsCommentService.del(id);
    }


    @RequestMapping("add")
    @ResponseBody
    public boolean add(String content, Integer newsId , HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        NewsComment comment = new NewsComment();
        comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setNewsId(newsId);
        comment.setIsShow(true);
        comment.setDate(new Date());
        newsCommentService.save(comment);
        return true;
    }

}
