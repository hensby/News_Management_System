package com.test.news.service;

import com.test.news.FNLPUtil;
import com.test.news.dao.*;
import com.test.news.model.*;
import org.fnlp.nlp.cn.CNFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import retrofit2.http.Url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zengliming
 * @date 2018/3/22 17:06
 */
@Service
public class NewsService {

    @Autowired
    private CNFactory cnFactory;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsTipRepository newsTipRepository;

    @Autowired
    private NewsTypeRepository newsTypeRepository;

    @Autowired
    private NewsCommentRepository newsCommentRepository;

    @Autowired
    private UserRepository userRepository;

    public News findOne(Integer id) {
        News news = newsRepository.findById(id).orElse(new News());
        List<NewsComment> comments = newsCommentRepository.findByNewsId(id);
        if (comments != null) {
            for (NewsComment comment : comments) {
                try {
                    comment.setUserName(userRepository.findById(comment.getUserId()).orElse(new User()).getName());
                } catch (Exception e) {
                }
            }
        } else {
            comments = new ArrayList<>();
        }
        news.setComments(comments);
        return news;
    }


    public List<News> listByType(Integer typeId) {
        List<News> list = newsRepository.findByTypeIdOrderByDateDesc(typeId);
        for (News news : list) {
            news.setTips(newsTipRepository.findByNewsId(news.getId()));
        }
        return list;
    }

    public List<News> findByTypeIdIn(List<Integer> typeId) {
        List<News> list = newsRepository.findByTypeIdInOrderByDateDesc(typeId);
        for (News news : list) {
            news.setTips(newsTipRepository.findByNewsId(news.getId()));
        }
        return list;
    }

    public List<News> listByTip(String tip) {
        List<News> list = new ArrayList<>();
        List<NewsTip> newsTips = newsTipRepository.findByTip(tip);
        for (NewsTip newsTip : newsTips) {
            News news = newsRepository.findById(newsTip.getNewsId()).orElse(null);
            if (news != null) {
                news.setTips(newsTipRepository.findByNewsId(news.getId()));
            }
            list.add(news);
        }
        return list;
    }


    public List<News> findAll() {
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        List<News> list = newsRepository.findAll(sort);
        for (News news : list) {
            try {
                newsTypeRepository.findById(news.getTypeId()).ifPresent(newsType -> news.setTypeName(newsType.getName()));
            } catch (Exception e) {
            }
        }
        return list;
    }


    public void delete(Integer id) {
        newsRepository.deleteById(id);
    }


    public void save(News news) {
        if (news.getId() == null) {
            news.setDate(new Date());
            news.setNum(0);
            //check the news from.
        } else {
            News n1 = newsRepository.findById(news.getId()).orElse(new News());
            news.setDate(n1.getDate());
            news.setNum(n1.getNum());
            news.setIsTop(n1.getIsTop());
        }
        news = newsRepository.save(news);
//        String[] tips = cnFactory.seg(news.getTitle());
//        if (tips != null) {
//            for (String tip : tips) {
//                if (tip.length() >= 2) {
//                    NewsTip newsTip = newsTipRepository.findByNewsIdAndTip(news.getId(), tip);
//                    if (newsTip == null) {
//                        newsTip = new NewsTip();
//                        newsTip.setNewsId(news.getId());
//                        newsTip.setTip(tip);
//                        newsTipRepository.save(newsTip);
//                    }
//                }
//            }
//        }
    }

    public News getOne(Integer id) {
        return newsRepository.findById(id).orElse(new News());
    }

    public List<News> findByName(String name) {
        return newsRepository.findByTitle(name);
    }

    public String getUrl(String url1) {
        try {
            URL url = new URL(url1);
            URLConnection URLconnection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.err.println("成功");
                InputStream in = httpConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader bufr = new BufferedReader(isr);
                String str;
                while ((str = bufr.readLine()) != null) {
                    System.out.println(str);
                    return str;
                }
                bufr.close();

            } else {
                System.err.println("失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url1;
    }

    public ModelAndView check(String url){
        return  new ModelAndView(new RedirectView(url));
    }

}
