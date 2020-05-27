package com.example.main;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

import java.io.File;
import java.util.Date;

public class JxlTest {
    @Test
    public void md5() throws Exception {
        Md5Hash hash = new Md5Hash("666", "jini", 2);
        System.out.println(hash);
    }

    @Test
    public void write() throws Exception {
        //xls
        WritableWorkbook workbook = Workbook.createWorkbook(new File("F:/jxl/bb.xls"));
        //工作簿
        WritableSheet sheet = workbook.createSheet("day01", 0);
        //单元格
        Label label = new Label(0, 0, "wolf");
        sheet.addCell(label);
        //日期单元格
        DateTime dateTime = new DateTime(0, 1, new Date());
        sheet.addCell(dateTime);

        //写入数据
        workbook.write();
        //关闭资源
        workbook.close();
    }

    @Test
    public void read() throws Exception {
        Workbook workbook = Workbook.getWorkbook(new File("F:/jxl/bb.xls"));
        Sheet sheet = workbook.getSheet(0);
        //总列数
        int columns = sheet.getColumns();
        //总行数
        int rows = sheet.getRows();
        //读取内容
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = sheet.getCell(j, i);
                System.out.println(cell.getContents());
            }
        }
        //关闭资源
        workbook.close();
    }
}
