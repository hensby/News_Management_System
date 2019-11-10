package com.test.news.controller.system;

import com.test.news.LoginRequired;
import com.test.news.model.User;
import com.test.news.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/23.
 */
@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @LoginRequired
    @RequestMapping(value = "/userList.htm")
    public String userList(Model model) {
        model.addAttribute("list", userService.findAll());
        return "user/list";
    }

    @LoginRequired
    @RequestMapping("del")
    @ResponseBody
    public boolean del(Model model, Integer id) {
        userService.del(id);
        return true;
    }

    @LoginRequired
    @RequestMapping(value = "save")
    @ResponseBody
    public boolean save(User user) {
        log.info("ksjzdhfkjsadhfj" + user.getPwd());
        if (user.getPwd().isEmpty() || user.getName().isEmpty()) {
            return false;
        }
        if (user.getId() == null) {
//            log.info("aaaaaaaaaa"+user.getCreateTime().toString());
            user.setCreateTime(new Date());
        } else {
//            User u2 = userService.findById(user.getId());
            User u2 = userService.findByName(user.getName());
//            if(u2==null){
            user.setCreateTime(userService.findById(user.getId()).getCreateTime());
//            }
        }
//        log.info("cccccccccccc"+user.getCreateTime().toString());
        user.setUpdateTime(new Date());
        userService.save(user);
        return true;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public boolean login(String name, String pwd, HttpServletRequest request) {
        User user = userService.findByName(name);
        if (user == null) {
            return false;
        }
        if (!user.getPwd().equals(pwd)) {
            return false;
        }
        request.getSession().setAttribute("user", user);
        return true;
    }
}