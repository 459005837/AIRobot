package com.lzq.testtest.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：李泽强
 * @date ： 2020/9/26 12:30
 * @description：
 */
public interface PdfToDwgService {
    public String getData(String excelName, HttpServletResponse response) throws IOException;
}
