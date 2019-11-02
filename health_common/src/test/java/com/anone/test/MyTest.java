package com.anone.test;


import com.anone.utils.DateUtils;
import com.anone.utils.SMSUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyTest {

    public static void main(String[] args) throws Exception {
      /*  List<String> months = DateUtils.getMonthBetween("2018-10-30","2019-09-08", "yyyy-MM");
        System.out.println(months);*/
       /* Calendar calendar = Calendar.getInstance();
        String d = DateUtils.parseDate2String(calendar.getTime(), "yyyy-MM");
        System.out.println(d);*/
       /* Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        List<String> list = new ArrayList<>();
        for(int i=0;i<12;i++){
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        System.out.println(list);*/
        /* SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,"18739937085","666666");*/
        /*//创建excel对象---读取excel
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook("C:\\Users\\Administrator\\Desktop\\0.xlsx");
        //获取sheet对象
        XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
        //获取row对象
        for (Row row : sheetAt) {
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }*/
       //创建一个Excel
    /*    XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet();
        XSSFRow row = sheet.createRow(0);
       row.createCell(0).setCellValue("名字");
       row.createCell(1).setCellValue("性别");
        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("小李");
        row1.createCell(1).setCellValue("男");
        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue(111);
        row2.createCell(1).setCellValue(222);*/
        /*   try {*/

          /*  XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            //记住行的索引从1开始 列是从0开
            //获取第一种sheet表
        XSSFRow row = null;
        XSSFSheet sheetAt = xssfWorkbook.createSheet();
        //第3行
            row = sheetAt.createRow(3);
            row.createCell(5).setCellValue(1);
            //第五行
            row = sheetAt.createRow(5);
            row.createCell(5).setCellValue(2);
            row.createCell(7).setCellValue(3);
            //第6行
            row = sheetAt.createRow(6);
            row.createCell(5).setCellValue(4);
            row.createCell(7).setCellValue(5);
            //第8行
            row = sheetAt.createRow(8);
            row.createCell(5).setCellValue(6);
            row.createCell(7).setCellValue(7);
            //第9行
            row = sheetAt.createRow(9);
            row.createCell(5).setCellValue(8);
            row.createCell(7).setCellValue(9);
            //第10行
            row = sheetAt.createRow(10);
            row.createCell(5).setCellValue(10);
            row.createCell(7).setCellValue(11);*/
            //第12行之后
            //定义行数

             /*   //输出
                ServletOutputStream outputStream = response.getOutputStream();
                //下载文件设置响应头信息
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
                xssfWorkbook.write(outputStream);
                //刷新流
                outputStream.flush();
                //先关闭流
                outputStream.close();
                //再关闭xssfworkbook对象 excel对象
                xssfWorkbook.close();*/
        //创建
/*        FileOutputStream fileOutputStream=new FileOutputStream("C:\\Users\\Administrator\\Desktop\\3.xlsx");
        xssfWorkbook.write(fileOutputStream);
        //关闭流
        fileOutputStream.flush();
        fileOutputStream.close();
        xssfWorkbook.close();*/
    }
}
