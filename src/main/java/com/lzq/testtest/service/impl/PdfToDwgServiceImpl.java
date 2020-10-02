package com.lzq.testtest.service.impl;

import com.lzq.testtest.pojo.User;
import com.lzq.testtest.service.ExportService;
import com.lzq.testtest.service.PdfToDwgService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author ：李泽强
 * @date ： 2020/9/26 12:32
 * @description：
 */
@Service
public class PdfToDwgServiceImpl implements PdfToDwgService {

    @Autowired
    private ExportService exportService;

    @Override
    public String getData(String excelName, HttpServletResponse response) throws IOException {
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
        if (excelName.equals("用户表")) {
            // 创建一个文件(有中文必须URL编码)
            fileName = excelName + ".xls";
            fileNameURL = URLEncoder.encode(fileName, "UTF-8");
            System.out.println(fileName);

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

            //打开应用程序
            String command = "cmd  /c  start  ";
            String path = "D:\\lenovo\\demo1.dxf";
            Runtime.getRuntime().exec(command+path);
            return "ok";
        } catch (IOException e) {
            return "catch";
        } finally {
            workbook.close();
        }
    }
}
