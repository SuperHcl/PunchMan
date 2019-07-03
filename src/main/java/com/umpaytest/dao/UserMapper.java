package com.umpaytest.dao;

import com.umpaytest.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/6/27 10:54
 * @description:
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER")
    List<User> findAll();

}
