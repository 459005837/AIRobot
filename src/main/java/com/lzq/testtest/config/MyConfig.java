package com.lzq.testtest.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：李泽强
 * @date ： 2020/9/26 13:00
 * @description：
 */
public class MyConfig implements WebMvcConfigurer {

    /**
     * 功能描述:设置首页自动跳转，利用视图解析器的配置
     *
     * @return void
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
    }

}

