package com.umpaytest.service.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hu.ChangLiang
 * @date 2025/2/6 15:53
 */
public class JsonTest {

    @Test
    public void test_1() {

        String filePath = "C:\\Users\\li185\\Desktop\\new 5.txt";
        List<Long> shopIds = new ArrayList<>();
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    JsonObject jsonObject = JsonParser.parseString(line).getAsJsonObject();
                    if (jsonObject.has("data")) {
                        JsonObject dataObject = jsonObject.getAsJsonObject("data");
                        if (dataObject.has("list")) {
                            JsonArray listArray = dataObject.getAsJsonArray("list");
                            count += listArray.size();
                            for (JsonElement element : listArray) {
                                JsonObject listItem = element.getAsJsonObject();
                                if (listItem.has("shopId")) {
                                    long shopId = listItem.get("shopId").getAsLong();
                                    shopIds.add(shopId);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 统计shopId的数量
        int shopIdCount = shopIds.size();

        // 找出重复的shopId
        Set<Long> uniqueShopIds = new HashSet<>();
        Set<Long> duplicatedShopIds = new HashSet<>();
        for (Long shopId : shopIds) {
            if (!uniqueShopIds.add(shopId)) {
                duplicatedShopIds.add(shopId);
            }
        }

        System.out.println("总记录数： " + count);
        System.out.println("shopId的数量： " + shopIdCount);
        System.out.println("重复的shopId： " + duplicatedShopIds);
    }
}
