package com.lzq.testtest.controller;

import com.lzq.testtest.service.PdfToDwgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author ：李泽强
 * @date ： 2020/9/26 12:23
 * @description：
 */
@Controller
public class PdfController {

    @Autowired
    PdfToDwgService service;

    //文件上传接口，返回数据展示界面
    @PostMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file, HttpServletResponse response) {
        //判断文件是否有传递过来
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        //获取文件的名称
        String fileName = file.getOriginalFilename();
        //获取最后一个.的位置
        int lastIndexOf = fileName.lastIndexOf(".");
        //获取文件的后缀名 .pdf
        String suffix1 = fileName.substring(lastIndexOf+1);
        //判断是否为pdf
        if(!"pdf".equals(suffix1)){
            return "文件格式错误，请选择pdf格式的文件类型";
        }
        //设置存储路径
        String path = "F:/A_springbootDemo/testtest/src/main/resources/PDF";
        //保证文件存储路径存在
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //为文件重命名为一个唯一值
        String uuid = UUID.randomUUID().toString().replace("-", "");
        fileName = uuid + "."+suffix1;
        File dest = new File(path , fileName);
        try {
            file.transferTo(dest);
            //调用获得数据的接口，然后打开dwg
            String str = service.getData("用户表", response);
            System.out.println(str);
            return "main.html";
        } catch (IOException e) {
            //TODO 抛出异常
        }
        return "上传失败！";
    }


    @GetMapping("/uploads")
    public HttpServletResponse download( HttpServletResponse response) {
        try {
            String path = "F:\\A_springbootDemo\\testtest\\src\\main\\resources\\PDF\\1a2106fa41d3472ba70b8814af6f365f.pdf";
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
}