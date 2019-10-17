package com.test.news.controller;

import com.test.news.LoginRequired;
import com.test.news.model.NewsType;
import com.test.news.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/3/23.
 */
@Controller
@RequestMapping("newsType")
public class NewsTypeController {

    @Autowired
    private NewsTypeService newsTypeService;

    @LoginRequired
    @RequestMapping(value = "/list.htm")
    public String list(Model model){
        model.addAttribute("list",newsTypeService.findAll());
        return "newsType/list";
    }

    @LoginRequired
    @RequestMapping("del")
    @ResponseBody
    public boolean del(Model model,Integer id){
        newsTypeService.del(id);
        return true;
    }

    @LoginRequired
    @RequestMapping(value = "save")
    @ResponseBody
    public boolean save(Model model, NewsType newsType){
        newsTypeService.save(newsType);
        return true;
    }
}
