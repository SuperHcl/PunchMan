package com.umpaytest.service.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SqlTemplateFiller {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\li185\\Desktop\\马来培训-转码后的url.txt";
        String outputFilePath = "C:\\Users\\li185\\Desktop\\填充后的sql.txt";
        String sqlTemplate = "UPDATE `cloud_resource_detail` set `cloud_url` = \"{2}\", `modified_time` = '2025-02-21 00:00:00' where `cloud_url` = \"{1}\";";

        Set<String> oldUrlSet = new HashSet<>();
        Set<String> newUrlSet = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            int lineNumber = 1;
            String baseUrl = "";
            while ((line = reader.readLine()) != null) {
                if (lineNumber % 2 == 1) {
                    baseUrl = line;
                    oldUrlSet.add(baseUrl);
                } else {
                    newUrlSet.add(line);
                    String filledSql = sqlTemplate.replace("{1}", baseUrl).replace("{2}", line);
                    writer.write(filledSql);
                    writer.newLine();
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("数据处理完成！");
        System.out.println("已填充的旧URL条数：" + oldUrlSet.size());
        System.out.println("已填充的旧URL：\n" + oldUrlSet);

        System.out.println("已填充的新URL条数：" + newUrlSet.size());
        System.out.println("已填充的新URL：\n" + newUrlSet);
    }
}
