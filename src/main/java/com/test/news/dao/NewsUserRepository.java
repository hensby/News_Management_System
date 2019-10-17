package com.test.news.dao;

import com.test.news.model.NewsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2019/9/27.
 */
@Repository
public interface NewsUserRepository extends JpaRepository<NewsUser, Integer> {

    NewsUser findByUserId(Integer userId);

    List<NewsUser> findByUserIdOrderByNumDesc(Integer userId);

}
