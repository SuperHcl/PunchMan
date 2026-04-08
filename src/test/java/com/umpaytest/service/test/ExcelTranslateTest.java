package com.umpaytest.service.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hu.ChangLiang
 * @date 2025/4/18 10:34
 */
public class ExcelTranslateTest {
    public static Set<String> haveSceneSet = new HashSet<>();
    public static Set<String> haveMsgSet = new HashSet<>();
    public static List<String> haveSceneList = new ArrayList<>();
    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\li185\\Desktop\\MY MIXUE 海外翻译对照表 - 副本.xlsx";
        String jsonFilePath = "classpath:transdb.json";

        try {
            // 读取JSON文件
            List<Message> messages = readJsonFile();
            // 读取Excel文件
            String sqlStatement = processExcel(excelFilePath, messages);
            System.out.println(sqlStatement);

            Set<String> allMsg = messages.stream().map(Message::getMsg_temp).collect(Collectors.toSet());
            allMsg.removeAll(haveMsgSet);

            System.out.println("allMsg: " + allMsg);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Message> readJsonFile() throws IOException {
        ClassPathResource resource = new ClassPathResource("transdb.json");
        try (InputStream inputStream = resource.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Message> messages = objectMapper.readValue(reader, new TypeReference<List<Message>>() {
            });
            return messages;
        }
    }

    public static String processExcel(String filePath, List<Message> messages) throws IOException {
        List<String> valuePairs = new ArrayList<>();
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // 跳过表头
            }
            Cell chineseCell = row.getCell(0);
            if (chineseCell == null) {
                continue;
            }
            String chineseValue = getCellValueAsString(chineseCell);

            for (Message message : messages) {
                if (haveMsgSet.contains(message.getMsg_temp())) {
                    continue;
                }
                if (chineseValue.equals(message.getMsg_temp())) {
                    haveSceneSet.add(message.getScene());
                    haveMsgSet.add(message.getMsg_temp());

                    Cell russianCell = row.getCell(1);
                    Cell kazakhCell = row.getCell(2);
                    Cell traditionalChineseCell = row.getCell(3);

                    String russianValue = getCellValueAsString(russianCell);
                    String kazakhValue = getCellValueAsString(kazakhCell);
                    String traditionalChineseValue = getCellValueAsString(traditionalChineseCell);

                    valuePairs.add(generateValuePair(message.getScene(), message.getKey(), "ru", russianValue));
                    valuePairs.add(generateValuePair(message.getScene(), message.getKey(), "kk", kazakhValue));
                    valuePairs.add(generateValuePair(message.getScene(), message.getKey(), "zh-MO", traditionalChineseValue));
                    break;
                }
            }
        }
        workbook.close();
        file.close();
        return generateSQL(valuePairs);
    }

    private static String generateValuePair(String scene, String key, String language, String msgTemp) {
        return "(FLOOR(RAND() * 100000000), '" + scene + "', '" + key + "', '" + language + "', 1, '" + msgTemp.replace("'", "\\'") + "', now(), now(), 1, 1, 1)";
    }

    private static String generateSQL(List<String> valuePairs) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `locale_message` (`id`, `scene`, `key`, `language`, `is_default`, `msg_temp`, `created_time`, `modified_time`, `creater`, `modifier`, `row_state`) VALUES ");

        for (int i = 0; i < valuePairs.size(); i++) {
            if (i > 0) {
                sql.append(", \n");
            }
            sql.append(valuePairs.get(i));
        }
        sql.append(";");
        return sql.toString();
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    static class Message {
        private String msg_temp;
        private String key;
        private String scene;

        public String getMsg_temp() {
            return msg_temp;
        }

        public String getKey() {
            return key;
        }

        public String getScene() {
            return scene;
        }
    }
}
