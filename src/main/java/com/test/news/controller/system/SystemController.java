package com.test.news.controller.system;

import com.test.news.LoginRequired;
import com.test.news.dao.SystemUserRepository;
import com.test.news.model.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Hengchao Wang
 * @date 2019/9/20 17:52
 */
@Controller
@RequestMapping(value = "system")
public class SystemController {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @LoginRequired
    @GetMapping(value = "/")
    public String index() {
        return "system";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "systemLogin";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public boolean login2(String name, String pwd, HttpSession session) {
        List<SystemUser> systemUsers = systemUserRepository.findByName(name);
        if (systemUsers != null && !systemUsers.isEmpty()) {
            SystemUser systemUser = systemUsers.get(0);
            if (systemUser.getPwd().equals(pwd)) {
                session.setAttribute("systemUser", systemUser);
                return true;
            }
        }
        return false;
    }


}
