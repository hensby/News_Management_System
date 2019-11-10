package com.test.news.service;

import com.test.news.dao.UserRepository;
import com.test.news.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @date 2019/9/20 17:52
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void del(Integer id) {
        userRepository.deleteById(id);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(new User());         //find by id. orelse() is to ignor Null pointer exception
    }
}
