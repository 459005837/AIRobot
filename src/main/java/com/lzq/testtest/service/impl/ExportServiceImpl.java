package com.lzq.testtest.service.impl;

import com.lzq.testtest.mapper.UserMapper;
import com.lzq.testtest.pojo.User;
import com.lzq.testtest.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：李泽强
 * @date ： 2020/9/25 20:22
 * @description：
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getHorseBreedConstant() {
        return userMapper.getUser();
    }
}
