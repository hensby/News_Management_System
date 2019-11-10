package com.test.news.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.paralleldots.paralleldots.App;
import com.test.news.dao.NewsCommentRepository;
import com.test.news.dao.NewsRepository;
import com.test.news.dao.UserRepository;
import com.test.news.model.News;
import com.test.news.model.NewsComment;
import com.test.news.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hengchoa on 2019/11/4.
 */
@Slf4j
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

    public boolean checkComment(String comment){
        try {
            App pd = new App("HEhkJRQ68O4zUw9KeTxpBHZwTJjZYYw6hWBAUh4NnbI");
            // for single sentences
            String abuse = pd.abuse(comment);
            System.out.println(abuse);

            String pattern = "(?<=:).*?(?=,)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(abuse);
            m.find();
            double abusive = Double.parseDouble(m.group(0));
            if (abusive>=0.8){
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
