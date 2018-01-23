package tk.mybatis.springboot.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.stereotype.Service;
import tk.mybatis.springboot.model.City;
import tk.mybatis.springboot.service.CityService;

import javax.annotation.Resource;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Service
public class CreateSimpleExcelToDisk {
    @Resource
    private CityService cityService;

    /**
     * 导出信息为Excel
     *
     * @param fileName
     * @param file
     * @throws Exception
     */
    public void getExcel(String fileName, String file) throws Exception {
        //取出要导出的信息到集合
        List<City> cityList = cityService.getAllCity();

        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(fileName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();

        // 创建一个居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置背景色
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("城市名称");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("省份");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("国家ID");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，

        for (int i = 0; i < cityList.size(); i++) {
            row = sheet.createRow((int) i + 1);
            City city = (City) cityList.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue(city.getId());
            row.createCell(1).setCellValue(city.getName());
            row.createCell(2).setCellValue(city.getState());
            row.createCell(3).setCellValue(city.getCountryId());
            cell = row.createCell(4);
        }
        // 第六步，将文件存到指定位置
        try {
            FileOutputStream fout = new FileOutputStream(file);
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public void readExcel() {
        File file = new File("D:/city.xls");
        System.out.println(file.getName());
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            HSSFWorkbook wb = new HSSFWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                String a = "" + index;
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(a);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getLastRowNum(); i++) {
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getRow(0).getPhysicalNumberOfCells(); j++) {
                        String cellinfo = sheet.getRow(i).getCell(j).getStringCellValue();
                        System.out.println(cellinfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------

    /**
     * 读取Office 2007 excel
     */
    public List<List<Object>> read2007Excel(File file)
            throws IOException {

        List<List<Object>> list = new LinkedList<List<Object>>();
        InputStream input = new FileInputStream("D:/city.xls");
        POIFSFileSystem fs = new POIFSFileSystem(input);
        HSSFWorkbook xwb = new HSSFWorkbook(fs);
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        // 读取第一章表格内容
        HSSFSheet sheet = xwb.getSheetAt(0);
        Object value = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        int counter = 0;
        for (int i = sheet.getFirstRowNum(); counter < sheet
                .getPhysicalNumberOfRows(); i++) {
            if (i == 0) {
                //跳过第一行
                continue;
            }
            row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            int a= row.getLastCellNum();
            List<Object> linked = new LinkedList<Object>();
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (cell == null) {
                   /* value = "无";//导入不能为空
                    linked.add(value);*/
                    //System.out.println(value);
                    continue;
                }
                //System.out.println(value);
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String
                // 字符
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd");// 格式化日期字符串
                DecimalFormat nf = new DecimalFormat("0");// 格式化数字
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        //System.out.println(i + "行" + j + " 列 is String type");
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        //  System.out.println(i + "行" + j
                        //  + " 列 is Number type ; DateFormt:"
                        //  + cell.getCellStyle().getDataFormatString());
                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                            value = df.format(cell.getNumericCellValue());
                        } else if ("General".equals(cell.getCellStyle()
                                .getDataFormatString())) {
                            value = nf.format(cell.getNumericCellValue());
                        } else {
                            value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                    .getNumericCellValue()));
                        }
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        value = cell.getBooleanCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK://空格，空白
                        value = "";
                        break;
                    default:
                        value = cell.toString();
                }
               /* if (value == null || "".equals(value)) {
                    value = "无";//导入不能为空
                }*/
                //System.out.println(value);
                linked.add(value);
            }
            list.add(linked);
        }
        return list;
    }
}
