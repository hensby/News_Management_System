package com.test.news.controller;

import com.test.news.model.NewsType;
import com.test.news.model.User;
import com.test.news.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Hengchao Wang
 * @date 2019/9/20 16:49
 */
@Controller
public class IndexController {

    @Autowired
    private NewsTypeService newsTypeService;

    @RequestMapping(value = "/")
    public String index(Model model, HttpSession httpSession){
       List<NewsType> newsTypeList =  newsTypeService.findAll();
       model.addAttribute("newsTypeList",newsTypeList);
       User user = (User) httpSession.getAttribute("user");
        model.addAttribute("user",user);
       if(user!=null){
           model.addAttribute("userName",user.getName());
       }else{
           model.addAttribute("userName","Login");
       }

       return "index";
    }

}
