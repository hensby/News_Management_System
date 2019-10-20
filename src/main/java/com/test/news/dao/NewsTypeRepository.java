package com.test.news.dao;

import com.test.news.model.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2019/10/13.
 */
@Repository
public interface NewsTypeRepository extends JpaRepository<NewsType, Integer>{       //extend jpa to operate db
    
}
