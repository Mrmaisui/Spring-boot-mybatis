package tk.mybatis.springboot.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ReadE {
	public static void main(String[] args) {
		try {
			InputStream input = new FileInputStream("D:/city.xls");
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next();
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue()+"\t");
						break;
					case HSSFCell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue()+"\t");
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						System.out.print(cell.getBooleanCellValue()+"\t");
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						System.out.print(cell.getCellFormula()+"\t");
						break;
					}
				}
				System.out.println();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
