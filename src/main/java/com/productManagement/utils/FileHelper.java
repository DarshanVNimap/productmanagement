package com.productManagement.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.productManagement.model.Product;

public class FileHelper {
	
	private static String[] COL_HEADING = { 
			"Sr no" , "Name" , "Description" ,
			"Price" , "Quantity in Stock" , "Image Page"
			};
	
	public static ByteArrayInputStream exportToExcel(List<Product> products) {
		
		
		try (XSSFWorkbook book = new XSSFWorkbook()) {
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XSSFSheet sheet= book.createSheet("Product list");
			
			Row row_heading = sheet.createRow(0);
			
			for(int i=0;i < COL_HEADING.length; i++) {
				row_heading.createCell(i).setCellValue(COL_HEADING[i]);
			}
			
			Iterator<Product> it = products.iterator();
			int rowNo = 1;
			while(it.hasNext()) {
				
				Row row = sheet.createRow(rowNo);
				Product p = it.next();
				
				row.createCell(0).setCellValue(rowNo);
				row.createCell(1).setCellValue(p.getName());
				row.createCell(2).setCellValue(p.getDescription());
				row.createCell(3).setCellValue(p.getPrice());
				row.createCell(4).setCellValue(p.getQuantityInStock());
				row.createCell(5).setCellValue(p.getProductImage());
				
				rowNo++;
			}
			
			book.write(out);
			book.close();
			out.close();
			
			return new ByteArrayInputStream(out.toByteArray());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
