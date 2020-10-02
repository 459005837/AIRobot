package com.lzq.testtest.mapper;

import com.lzq.testtest.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：李泽强
 * @date ： 2020/9/25 20:22
 * @description：
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> getUser();
}
