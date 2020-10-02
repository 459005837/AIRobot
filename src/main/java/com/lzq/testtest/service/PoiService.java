package com.lzq.testtest.service;

import com.lzq.testtest.pojo.Product;
import com.lzq.testtest.utils.DateUtil;
import com.lzq.testtest.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：李泽强
 * @date ： 2020/9/27 22:05
 * @description：
 */
@Service
public class PoiService {
    private static final Logger log= LoggerFactory.getLogger(PoiService.class);

    /**
     * 读取excel数据
     * @param wb
     * @return
     * @throws Exception
     */
    public List<Product> readExcelData(Workbook wb) throws Exception{
        Product product=null;
        List<Product> products=new ArrayList<Product>();
        Row row=null;
        int numSheet=wb.getNumberOfSheets();
        if (numSheet>0) {
            for(int i=0;i<numSheet;i++){
                Sheet sheet=wb.getSheetAt(i);
                int numRow=sheet.getLastRowNum();
                if (numRow>0) {
                    for(int j=1;i<numRow;i++){
                        //TODO：跳过excel sheet表格头部
                        row=sheet.getRow(j);
                        product=new Product();

                        String name=ExcelUtil.manageCell(row.getCell(1), null);
                        String unit= ExcelUtil.manageCell(row.getCell(2), null);
                        Double price=Double.valueOf(ExcelUtil.manageCell(row.getCell(3), null));
                        String stock=ExcelUtil.manageCell(row.getCell(4), null);
                        String remark=ExcelUtil.manageCell(row.getCell(6), null);

                        product.setName(name);
                        product.setUnit(unit);
                        product.setPrice(price);
                        product.setStock(Double.valueOf(stock));
                        String value=ExcelUtil.manageCell(row.getCell(5), "yyyy-MM-dd");
                        product.setPurchaseDate(DateUtil.strToDate(value, "yyyy-MM-dd"));
                        product.setRemark(remark);
                        products.add(product);
                    }
                }
            }
        }

        log.info("获取数据列表: {} ",products);
        return products;
    }

}
