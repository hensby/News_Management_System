package com.test.news.dao;

import com.test.news.model.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2019/9/28.
 */
@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser,Integer>{

    List<SystemUser> findByName(String name);
}
