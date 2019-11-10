package com.test.news.dao;

import com.test.news.model.NewsComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hengchao on 2019/10/24.
 */
@Repository
public interface NewsCommentRepository extends JpaRepository<NewsComment, Integer> {       //extend jpa to operate db

    List<NewsComment> findByNewsId(Integer newsId);

}
