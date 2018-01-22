package tk.mybatis.springboot.utils;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import tk.mybatis.springboot.model.City;
import tk.mybatis.springboot.model.DTO.CityDTO;
import tk.mybatis.springboot.service.CityService;

import javax.annotation.Resource;

import static org.apache.poi.hssf.util.HSSFCellUtil.getCell;

public class CreateSimpleExcelToDisk {
    @Resource
    private CityService cityService;
    @Test
    public void getExcel1(String FileName,String File) throws Exception {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(FileName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("ID");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("城市名称");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("省份");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("国家ID");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        List<City> cityList = cityService.getAllCity();

        for (int i = 0; i < cityList.size(); i++) {
            row = sheet.createRow((int) i + 1);
            City city = (City) cityList.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell((short) 0).setCellValue(city.getId());
            row.createCell((short) 1).setCellValue(city.getName());
            row.createCell((short) 2).setCellValue(city.getState());
            row.createCell((short) 3).setCellValue(city.getCountryId());
            cell = row.createCell((short) 4);
        }
        // 第六步，将文件存到指定位置
        try {
            FileOutputStream fout = new FileOutputStream(File+".xls");
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
