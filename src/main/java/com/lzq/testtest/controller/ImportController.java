package com.lzq.testtest.controller;

import com.lzq.testtest.mapper.ProductMapper;
import com.lzq.testtest.pojo.Product;
import com.lzq.testtest.service.PoiService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ：李泽强
 * @date ： 2020/9/27 15:36
 * @description：
 */
@Controller
public class ImportController {

    static Logger logger = LoggerFactory.getLogger(ImportController.class);

    @Autowired
    ProductMapper productMapper;

    @Autowired
    PoiService poiService;

    @RequestMapping(value="/imports",method= RequestMethod.POST)
    @ResponseBody
    public String uploadExcel(MultipartFile file,String version){
        System.out.println("11111111111111111111111111111");
        System.out.println(version);
        System.out.println(file);
        try {
            if (StringUtils.isEmpty(version) || file==null) {
                return "参数为空";
            }
            logger.debug("版本号：{} file：{} ",version,file);

            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            List<Product> products = poiService.readExcelData(wb);
            productMapper.insertBatch(products);
        } catch (Exception e) {
            logger.error("上传excel导入数据 发生异常：",e.fillInStackTrace());
            return "上传错误";
        }
        return "上传成功";
    }
}
