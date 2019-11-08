package com.test.news.service;

import com.test.news.dao.NewsCommentRepository;
import com.test.news.dao.NewsRepository;
import com.test.news.dao.UserRepository;
import com.test.news.model.News;
import com.test.news.model.NewsComment;
import com.test.news.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hengchoa on 2019/11/4.
 */
@Service
public class NewsCommentService {

    @Autowired
    private NewsCommentRepository newsCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    public  List<NewsComment> findList(){
        List<NewsComment> list = newsCommentRepository.findAll();
        for (NewsComment newsComment : list) {
            User user = userRepository.findById(newsComment.getUserId()).orElse(new User());
            News news = newsRepository.findById(newsComment.getNewsId()).orElse(new News());
            newsComment.setTile(news.getTitle());
            newsComment.setUserName(user.getName());
        }
        return list;
    }

    public  void del(Integer id){
        newsCommentRepository.deleteById(id);
    }

    public void save(NewsComment newsComment){
        News news = newsRepository.findById(newsComment.getNewsId()).orElse(new News());
        news.setNum(news.getNum()+1);
        newsRepository.save(news);
        newsCommentRepository.save(newsComment);
    }
}
