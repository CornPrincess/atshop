package com.atsho.atservice.utils;

import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

public class ExcelOutputUtilTest {

    @Test
    public void test(){

    }

    @Test
    public void test7() throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream( new File("D://ceshi.xlsx")));
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        XSSFRow row =sheet.getRow(0);
//        XSSFCell cell= row.getCell(0);
//        System.out.println(cell.toString());

//        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Data Validation");
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)
                dvHelper.createExplicitListConstraint(new String[]{"11", "21", "31"});
        CellRangeAddressList addressList = new CellRangeAddressList(0, 0, 0, 0);
        XSSFDataValidation validation = (XSSFDataValidation)dvHelper.createValidation(
                dvConstraint, addressList);
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);

        FileOutputStream out =new FileOutputStream("D://ceshi.xlsx");
    }
}