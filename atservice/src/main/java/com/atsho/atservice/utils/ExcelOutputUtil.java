package com.atsho.atservice.utils;


import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelOutputUtil {

    public static void main(String[] args) {
        FileOutputStream out = null;
        try {
            // excel对象
            HSSFWorkbook wb = new HSSFWorkbook();
            // sheet对象
            HSSFSheet sheet = wb.createSheet("sheet1");
            // 输出excel对象
            out = new FileOutputStream("D://ceshi.xlsx");
            // 取得规则
            HSSFDataValidation validateData =
                    ExcelOutputUtil.setValidate((short) 1, (short) 1, (short) 4,
                            (short) 4);

            HSSFDataValidation validate = ExcelOutputUtil.setBoxs();

            HSSFDataValidation dateVa = ExcelOutputUtil.setDate();

            // 设定规则
            // sheet.addValidationData(validate);
            // sheet.addValidationData(validateData);
            sheet.addValidationData(dateVa);
            // 输出excel
            wb.write(out);
            out.close();

            System.out.println("在D盘成功生成了excel，请去查看");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    // 数字大小控制：设置单元格只能在1-20之间
    public static HSSFDataValidation setValidate(short firstRow, short lastRow, short firstCol, short lastCol) {
        // 创建一个规则：1-100的数字
        DVConstraint constraint = DVConstraint.createNumericConstraint(DVConstraint.ValidationType.INTEGER,
                DVConstraint.OperatorType.BETWEEN, "1", "20");
        // 设定在哪个单元格生效
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 创建规则对象
        HSSFDataValidation ret = new HSSFDataValidation(regions, constraint);
        return ret;
    }

    // 下拉框限制
    public static HSSFDataValidation setBoxs() {
        CellRangeAddressList addressList = new CellRangeAddressList(0, 0, 0, 0);
        final String[] DATA_LIST = new String[]{"男", "女",};
        DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(DATA_LIST);

        HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
        dataValidation.setSuppressDropDownArrow(true);
        dataValidation.createPromptBox("输入提示", "请从下拉列表中选择男女");
        dataValidation.setShowPromptBox(true);

        return dataValidation;
    }

    // 日期格式限制
    public static HSSFDataValidation setDate() {
        CellRangeAddressList addressList = new CellRangeAddressList(0, 1, 0, 2);
        DVConstraint dvConstraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01",
                "5000-01-01", "yyyy-mm-dd");

        HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
        dataValidation.setSuppressDropDownArrow(false);
        dataValidation.createPromptBox("输入提示", "请填写日期格式");
        // 设置输入错误提示信息
        dataValidation.createErrorBox("日期格式错误提示", "你输入的日期格式不符合'yyyy-mm-dd'格式规范，请重新输入！");
        dataValidation.setShowPromptBox(true);

        return dataValidation;
    }

    public static void xssValidate() {
//        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
//        Sheet sh = wb.createSheet();
//        for(int rownum = 0; rownum < 1000; rownum++){
//            Row row = sh.createRow(rownum);
//            for(int cellnum = 0; cellnum < 10; cellnum++){
//                Cell cell = row.createCell(cellnum);
//                String address = new CellReference(cell).formatAsString();
//                cell.setCellValue(address);
//            }
//
//        }
//
//        // Rows with rownum < 900 are flushed and not accessible
//        for(int rownum = 0; rownum < 900; rownum++){
//            Assert.assertNull(sh.getRow(rownum));
//        }
//
//        // ther last 100 rows are still in memory
//        for(int rownum = 900; rownum < 1000; rownum++){
//            Assert.assertNotNull(sh.getRow(rownum));
//        }
//
//        FileOutputStream out = new FileOutputStream("/temp/sxssf.xlsx");
//        wb.write(out);
//        out.close();
//
//        // dispose of temporary files backing this workbook on disk
//        wb.dispose();
//        Workbook workbook=
//
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Data Validation");
//        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
//        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)
//                dvHelper.createExplicitListConstraint(new String[]{"11", "21", "31"});
//        CellRangeAddressList addressList = new CellRangeAddressList(0, 0, 0, 0);
//        XSSFDataValidation validation = (XSSFDataValidation)dvHelper.createValidation(
//                dvConstraint, addressList);
//        validation.setShowErrorBox(true);
//        sheet.addValidationData(validation);
    }
}