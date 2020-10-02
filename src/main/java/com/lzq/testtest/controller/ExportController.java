package com.lzq.testtest.controller;

import com.lzq.testtest.pojo.User;
import com.lzq.testtest.service.ExportService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author ：李泽强
 * @date ： 2020/9/25 16:18
 * @description：导表测试
 */
@Controller
public class ExportController {

    @Autowired
    private ExportService exportService;

    //导出excel表格
    @RequestMapping(value = "/export", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String exportExcel(String ExcelName, HttpServletResponse response) throws IOException {
        //文件名用
        String fileName = "";
        String fileNameURL = "";

        // 创建excel工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        if (ExcelName.equals("用户表")) {
            // 创建一个文件(有中文必须URL编码)
            fileName = ExcelName + ".xls";
            fileNameURL = URLEncoder.encode(fileName, "UTF-8");
            //获取数据（查出想要导出的数据）
            List<User> lists = exportService.getHorseBreedConstant();
            String[] title = {"用户名", "邮箱名"};
            // 插入第一行，标题头
            for (int i = 0; i < title.length; i++) {
                // 创建一行的一格
                cell = row.createCell(i);
                // 赋值
                cell.setCellValue(title[i]);
            }
            if (lists.size() != 0) {
                // 追加数据行数
                int j = 1;
                User list = null;
                for (int i = 0; i < lists.size(); i++) {
                    // 从集合中得到一个对象
                    list = lists.get(i);
                    // 创建第2行（数据）
                    HSSFRow nextrow = sheet.createRow(i + 1);
                    // 创建第2行第1列并赋值
                    HSSFCell cessk = nextrow.createCell(0);
                    cessk.setCellValue(list.getUsername());
                    // 创建第2行第2列并赋值
                    cessk = nextrow.createCell(1);
                    cessk.setCellValue(list.getEmail());
                    j++;
                }
                System.out.println(j + "条数据");
            }
        }

        if (fileName.equals("")) {
            response.getWriter().write("失败，失败原因：参数为空！");
            response.reset();
            workbook.close();
            return "失败，失败原因：参数错误！";
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);
        OutputStream ouputStream;
        try {
            ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
            return "ok";
        } catch (IOException e) {
            return "catch";
        } finally {
            workbook.close();
        }
    }



}