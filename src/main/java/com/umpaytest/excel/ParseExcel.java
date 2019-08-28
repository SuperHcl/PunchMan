package com.umpaytest.excel;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: Hucl
 * @date: 2019/8/14 16:43
 * @description:
 */
public class ParseExcel {
    private static final float TEN_THOUSAND = 10000f;
    private static final float TWO_HUNDRED_THOUSAND = 200000f;

    public static void main(String[] args) {
        String path = "C:\\Users\\Hucl\\Desktop\\2019-08-12芯渠返回结果.xlsx";
        Map<String, String> result = new HashMap<>();

        try {
            result = readFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> transformMap = new HashMap<>();
        if (result != null) {
            result.forEach((key, value) -> {
                JSONObject object = JSONObject.parseObject(value);
                String transformResult = transform(object.getString("data"));
                transformMap.put(key, transformResult);
            });
        }

        System.out.println(JSONObject.toJSONString(transformMap));

    }

    private static Map<String, String> readFile(String path) throws IOException {
        Map<String, String> content = new HashMap<>();
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        // 从第2行开始
//        HSSFRow rows = sheet.getRow(1);


        XSSFRow row;
        XSSFCell cell1, cell2;
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rows; i++) {
            row = sheet.getRow(i);
            cell1 = row.getCell(0);
            cell2 = row.getCell(1);
            content.put(cell1.getStringCellValue().trim(), cell2.getStringCellValue().trim());
        }
        return content;
    }

    public static String transform(String data) {
        // 是否查得 未查得返回 Unknown
        if (StringUtils.isEmpty(data)) {
            return "unknown";
        }
        JSONObject dataJson = JSONObject.parseObject(data);
        String BP1002_label = dataJson.getString("BP1002");
//        String BP1005_label = dataJson.getString("BP1005");
//        String BP1011_label = dataJson.getString("BP1011");
        String CP6005_label = dataJson.getString("CP6005");
        String CP5349_label = dataJson.getString("CP5349");
        String CP5351_label = dataJson.getString("CP5351");
        String CP5001_label = dataJson.getString("CP5001");
        String CP5016_label = dataJson.getString("CP5016");
        String CP5028_label = dataJson.getString("CP5028");
        String CP5033_label = dataJson.getString("CP5033");
        String CP5058_label = dataJson.getString("CP5058");
        String CP5080_label = dataJson.getString("CP5080");

        if (!StringUtils.isEmpty(BP1002_label) && BP1002_label.equals("贷记卡")) {
            return "1";
        } else {
            if (!StringUtils.isEmpty(CP6005_label) && compareDate(CP6005_label, "2016-12-01")) {
                return "1";
            } else {
                if (judge49And51(CP5349_label, CP5351_label)) {
                    return "1";
                } else {
                    if (judge01And80(CP5001_label, CP5016_label, CP5028_label, CP5033_label, CP5058_label, CP5080_label)) {
                        return "1";
                    } else {
                        return "2";
                    }
                }
            }
        }
    }

    private static boolean judge49And51(String CP5349, String CP5351) {
        return compareToNumber(CP5349, TEN_THOUSAND) || compareToNumber(CP5351, TEN_THOUSAND);
    }

    private static boolean judge01And80(String CP5001, String CP5016, String CP5028, String CP5033,
                                 String CP5058, String CP5080) {
        return compareToNumber(CP5001, TWO_HUNDRED_THOUSAND) || compareToNumber(CP5016, TWO_HUNDRED_THOUSAND) || compareToNumber(CP5028, TWO_HUNDRED_THOUSAND) ||
                compareToNumber(CP5033, TWO_HUNDRED_THOUSAND) || compareToNumber(CP5058, TWO_HUNDRED_THOUSAND) || compareToNumber(CP5080, TWO_HUNDRED_THOUSAND);
    }

    private static boolean compareToNumber(String source, Float target) {
        if (StringUtils.isEmpty(source)) {
            return false;
        }
        Float f = new Float(source);
        return f > target;
    }

    private static boolean compareDate(Date sourceDate, Date targetDate) {
        return sourceDate.getTime() < targetDate.getTime();
    }

    private static boolean compareDate(String sourceDateSrt, String targetDateStr) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sourceDate = sdf1.parse(sourceDateSrt);
            Date targetDate = sdf2.parse(targetDateStr);
            return compareDate(sourceDate, targetDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
