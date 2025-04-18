package com.umpaytest.firebase;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * google消息推送context
 *
 * @author Hu.ChangLiang
 * @date 2024/9/11 18:33
 */
@Slf4j
public class FirebaseContext {
    public static final String MY_MI_XUE = "haiwaiMyMixue";
    private final static Map<String, FirebaseApp> instances = new HashMap<>(8);

    private FirebaseContext() {
    }


    public static void build(String key, FirebaseCloudMessagingMProperties.Configuration configuration) {
        log.info("FirebaseContext build [{} {}]", key, configuration);
        try {
            InputStream inputStream = convert2InputStream(configuration);
            // 读取配置
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    // 设置连接超时时间10s
                    // 超时配置不会生效，问题还没被解决。
                    // https://github.com/googleapis/google-auth-library-java/issues/356
                    // https://github.com/firebase/firebase-admin-java/issues/317
//                    .setConnectTimeout(1000)
                    // 设置读取超时时间5s
//                    .setReadTimeout(5000)
                    .build();
            // 初始化
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options, key);
//            // 获取实例
//            FirebaseMessaging instance = FirebaseMessaging.getInstance(firebaseApp);
            // 保存
            instances.putIfAbsent(key, firebaseApp);
        } catch (Exception e) {
            log.warn("Firebase initialized failed. errMsg=", e);
        }
    }

    static {
        build();
    }

    public static void build() {
        ClassPathResource classPathResource = new ClassPathResource("firebase-adminsdk.json");

        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(classPathResource.getInputStream()))
                    .build();
            // 初始化
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options, MY_MI_XUE);
            instances.putIfAbsent(MY_MI_XUE, firebaseApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取FCM发送消息的实例
     *
     * @param key 项目标识，例如MyMixue对应的项目标识
     * @return {@link FirebaseMessaging}
     */
    public static FirebaseMessaging getInstance(String key) {
        FirebaseApp firebaseApp = instances.get(key);
        FirebaseMessaging instance = FirebaseMessaging.getInstance(firebaseApp);
        return instance;
    }

    private static InputStream convert2InputStream(FirebaseCloudMessagingMProperties.Configuration configuration) throws JsonProcessingException {
        Map<String, String> firebaseConfigMap = new HashMap<>();
        firebaseConfigMap.put("type", configuration.getType());
        firebaseConfigMap.put("project_id", configuration.getProjectId());
        firebaseConfigMap.put("private_key_id", configuration.getPrivateKeyId());
        firebaseConfigMap.put("private_key", configuration.getPrivateKey().replace("\\n", System.lineSeparator()));
        firebaseConfigMap.put("client_email", configuration.getClientEmail());
        firebaseConfigMap.put("client_id", configuration.getClientId());
        firebaseConfigMap.put("auth_uri", configuration.getAuthUri());
        firebaseConfigMap.put("token_uri", configuration.getTokenUri());
        firebaseConfigMap.put("auth_provider_x509_cert_url", configuration.getAuthProviderCertUrl());
        firebaseConfigMap.put("client_x509_cert_url", configuration.getClientCertUrl());
        firebaseConfigMap.put("universe_domain", configuration.getUniverseDomain());

        // 转换为JSON
//        String firebaseConfigJson = JSON.toJSONString(firebaseConfigMap);
        String firebaseConfigJson = new ObjectMapper().writeValueAsString(firebaseConfigMap);
        // 将 JSON 字符串转换为 InputStream
        return new ByteArrayInputStream(firebaseConfigJson.getBytes());
    }

}
