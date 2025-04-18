package com.umpaytest.firebase;

import com.alibaba.fastjson.JSON;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hu.ChangLiang
 * @date 2024/9/9 16:26
 */
@Slf4j
public class FcmService {
    private static final String DATA_TITLE = "title";
    private static final String DATA_CONTENT = "content";
    private static final String DATA_TYPE = "type";
    private static final String DATA_ACTION = "action";
    private static final String DATA_TARGET_PAGE = "target_page";
    private static final String DATA_EXTRA_PARAMS = "extra_params";

    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("firebase-adminsdk.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(classPathResource.getInputStream()))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        System.out.println("Firebase initialized successfully");
    }

    public static void main1(String[] args) {

        String token = "dsz7ety7R3KUx3sTJvH9Yq:APA91bFXjdaM59kN4stMqqQqFOLIWbu-Kv3ZmxxZHPwlq8dG0OY3Ost1ZTDrAtmyY3AIFXvEu6PB_JfH1DCdXTVdXRDeOESX95aCt82aaRvhRBvq61BuPlGJsSDvOVY_PyeoQXAZ15PZ";
        String title = "Apple发布会";
        String body = "1块钱一个的iPhone16";

        try {
            // 创建消息
            Notification build = Notification.builder()
                    .setBody(body)
                    .setTitle(title)
                    .build();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("announcementId", "1838115366069501954");
            jsonObject.put("employeeId", "1824701376039485441");

            Message message = Message.builder()
                    .setToken(token) // 设置设备的 registration token
//                    .setNotification(new Notification(title, body)) // 消息内容
                    .setNotification(build) // 消息内容
                    .putData("type", "1")
                    .putData("action", "open_page")
                    .putData("target_page", "mxos://company/notice/index")
                    .putData("extra_params", jsonObject.toString())
                    .build();

            // 发送消息
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SingleMsg msg = new SingleMsg();
        String token = "dsz7ety7R3KUx3sTJvH9Yq:APA91bFXjdaM59kN4stMqqQqFOLIWbu-Kv3ZmxxZHPwlq8dG0OY3Ost1ZTDrAtmyY3AIFXvEu6PB_JfH1DCdXTVdXRDeOESX95aCt82aaRvhRBvq61BuPlGJsSDvOVY_PyeoQXAZ15PZ";

        msg.setClientId(token);
        msg.setContent("{\"data\":{\"action\":\"open_page\",\"type\":1,\"body\":{\"announcementId\":\"1852663098016686082\",\"employeeId\":\"1846031374348201986\"},\"key\":\"haiwaiMyMixue\",\"targetPage\":\"mxos://company/notice/detail\"},\"desc\":\"Pemberitahuan Peningkatan Standar Toko （提高门店标准通知）\",\"msgType\":6,\"pushTime\":\"2024-11-04 14:45:38\",\"title\":\"Arsip Pengumuman\"}");

        send(msg);
    }

    public static void send(SingleMsg msg) {
        ContentVo contentVo = com.alibaba.fastjson.JSONObject.parseObject(msg.getContent(), ContentVo.class);

        // 默认MyMixue
        String key = FirebaseContext.MY_MI_XUE;
        String targetPage = null;
        String extraParams = null;
        Map<String, String> dataMap = new HashMap<>();
        if (contentVo.getData() != null) {
            com.alibaba.fastjson.JSONObject dataObject = JSON.parseObject(JSON.toJSONString(contentVo.getData()));
            if (dataObject != null) {
                // 遍历 JSONObject 并将键值对放入 Map 中
                for (String dataKey : dataObject.keySet()) {
                    String value = dataObject.getString(dataKey);
                    dataMap.put(dataKey, value);
                }
                key = dataObject.getString("key");
                targetPage = dataObject.getString("targetPage");
                extraParams = dataObject.getString("body");
            }
        }

        // 创建通知消息
        Notification notification = Notification.builder()
                .setTitle(contentVo.getTitle())
                .setBody(contentVo.getDesc())
                .build();
        Message message = Message.builder()
                // 设备token
                .setToken(msg.getClientId())
                .setNotification(notification)
                // 设置自定义消息内容（客户端使用）
                .putData(DATA_TITLE, contentVo.getTitle())
                .putData(DATA_CONTENT, contentVo.getDesc())
                .putData(DATA_TARGET_PAGE, targetPage)
                .putData(DATA_EXTRA_PARAMS, extraParams)
                .putAllData(dataMap)
                .build();

        // 发送消息推送
        try {
            FirebaseMessaging instance = FirebaseContext.getInstance(key);
            String messageId = StringUtils.EMPTY;
            if (instance != null) {
                messageId = instance.send(message);
                log.info("FCM消息推送: 推送成功, key={}, messageId={}", key, messageId);
            } else {
                log.warn("FCM消息推送: 推送取消, 没找到对应的Messaging实例。key={}", key);
            }
        } catch (FirebaseMessagingException e) {
            log.error("FCM消息推送: 推送失败, key={}, errMsg={}", key, e);

        }
    }

    @Data
    public static class SingleMsg {
        // 应用名称，用于确定push server
        private String appName;
        // 消息流水号，用于消息追踪
        private String msgId;
        // 消息类型
        private int msgType;

        // 设备标识
        private String clientId;
        // 客户端类型，1 Android、2 iOS、3 原生iOS
        private String clientType;
        // 厂商设备id
        private String regId;
        // 厂商类型，1华为、2VIVO、3OPPO、4小米
        private Integer regType;
        // 离线失效时间，单位毫秒
        private Long offlineExpireTime;
        // 发送内容
        private String content;
        //设备品牌
        private String brand;
    }

    @Data
    public static class ContentVo<T> {
        // 1 订单状态
        private int msgType;
        private String title;
        private String desc;
        private Date pushTime;
        private String url;
        private String img;
        private T data;
    }
}


