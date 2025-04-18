package com.umpaytest.service.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hu.ChangLiang
 * @date 2025/2/17 17:34
 */
public class ExcelMergeByEmployeeId {
    public static void main(String[] args) {
        try {
            // 1. 读取excel2.xlsx并构建员工Id映射
            Map<String, String[]> employeeMap = readExcel2Data("C:\\Users\\li185\\Desktop\\excel2.xlsx");

            // 2. 读取并更新excel1.xlsx
            updateExcel1WithEmployeeData("C:\\Users\\li185\\Desktop\\excel1.xlsx", "C:\\Users\\li185\\Desktop\\output.xlsx", employeeMap);

            System.out.println("数据处理完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String[]> readExcel2Data(String filePath) throws Exception {
        Map<String, String[]> map = new HashMap<>();
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // 跳过标题行

            Cell idCell = row.getCell(3); // 第四列（D列）员工Id
            if (idCell == null) continue;

            String employeeId = getCellValueAsString(idCell);
            String[] data = new String[3];

            // 获取前三列数据（A,B,C列）
            data[0] = getCellValueAsString(row.getCell(0)); // 员工账号
            data[1] = getCellValueAsString(row.getCell(1)); // 员工名
            data[2] = getCellValueAsString(row.getCell(2)); // 组织岗位

            map.put(employeeId.trim(), data);
        }

        workbook.close();
        fis.close();
        return map;
    }

    private static void updateExcel1WithEmployeeData(String inputPath, String outputPath, Map<String, String[]> employeeMap) throws Exception {
        FileInputStream fis = new FileInputStream(inputPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // 添加新列标题（如果原文件没有）
        Row headerRow = sheet.getRow(0);
        headerRow.createCell(3).setCellValue("员工账号");
        headerRow.createCell(4).setCellValue("员工名");
        headerRow.createCell(5).setCellValue("组织岗位");

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // 跳过标题行

            Cell idCell = row.getCell(1); // 第二列（B列）员工Id
            if (idCell == null) continue;

            String employeeId = getCellValueAsString(idCell);
            String[] employeeData = employeeMap.get(employeeId.trim());

            if (employeeData != null) {
                // 写入第4、5、6列（D,E,F列）
                row.createCell(3).setCellValue(employeeData[0]);
                row.createCell(4).setCellValue(employeeData[1]);
                row.createCell(5).setCellValue(employeeData[2]);
            }
        }

        // 保存到新文件
        FileOutputStream fos = new FileOutputStream(outputPath);
        workbook.write(fos);
        workbook.close();
        fos.close();
        fis.close();
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }
}
