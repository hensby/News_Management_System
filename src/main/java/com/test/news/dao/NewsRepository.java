package com.test.news.dao;

import com.test.news.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hengchao wang
 * @date 2019/9/22 17:02
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {          //extend jpa to operate db

    List<News> findByTypeIdOrderByDate(Integer typeId);

    List<News> findByTypeIdOrderByDateDesc(Integer type);

    List<News> findByTypeIdInOrderByDateDesc(List<Integer> typeId);

    List<News> findByTitle(String title);

    List<News> findByIsTop(Boolean isTop);

}
