package com.umpaytest.service.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hu.ChangLiang
 * @date 2025/2/5 15:07
 */
public class CsvTest {

    public static void main(String[] args) {
        // 模拟数据
        List<DataRow> data = new ArrayList<>();
        data.add(new DataRow("John, Doe", "Value1"));
        data.add(new DataRow("Jane \"Smith\"", "Value2"));

        // 定义 CSV 文件路径
        String csvFilePath = "C:\\Users\\li185\\Downloads\\output.csv";

        try (FileWriter writer = new FileWriter(csvFilePath)) {
            // 写入表头
            writer.append("name,otherColumn\n");

            // 写入数据行
            for (DataRow row : data) {
                // 处理 name 列，添加引号并转义引号
                String escapedName = escapeCsvValue(row.name);
                writer.append(escapedName);
                writer.append(",");
                // 处理 otherColumn 列，添加引号并转义引号
                String escapedOtherColumn = escapeCsvValue(row.otherColumn);
                writer.append(escapedOtherColumn);
                writer.append("\n");
            }

            System.out.println("CSV 文件导出成功！");
        } catch (IOException e) {
            System.err.println("导出 CSV 文件时出现错误：" + e.getMessage());
        }
    }

    // 处理包含特殊字符的数据，添加引号并转义引号
    private static String escapeCsvValue(String value) {
        if (value == null) {
            return "";
        }
        // 如果数据包含逗号或双引号，需要用双引号括起来
        if (value.contains(",") || value.contains("\"")) {
            // 转义双引号
            value = value.replace("\"", "\"\"");
            // 用双引号括起来
            return "\"" + value + "\"";
        }
        return value;
    }
}

class DataRow {
    String name;
    String otherColumn;

    public DataRow(String name, String otherColumn) {
        this.name = name;
        this.otherColumn = otherColumn;
    }
}