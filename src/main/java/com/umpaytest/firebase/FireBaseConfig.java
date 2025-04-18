package com.umpaytest.firebase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hu.ChangLiang
 * @date 2024/9/9 16:20
 */
@Slf4j
public class FireBaseConfig {

    private static String type = "service_account";
    private static String projectId = "my-mixue";
    private static String privateKeyId = "41e77ee1814ac293682f2648c07163bf5599f692";
    private static String privateKey = "-----BEGIN PRIVATE KEY-----\nMIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQDHjAbC54+cpNJV\nOKt48GWhn9DvoKmjjvsDvOPrxAGyDXQpNpUWXidsvyft7nljYgkY58XFNvjKgx/v\n7PCJfefBhD0Yc/V58P9zm7h1bShUspaBGsq84YqzOSQUVbAKePD7lRsWkt0cgmXf\nc2NW6mMGKz4eyt7r9htlQDk6RwMUYoNvhgAs58O5I1NtbQodF+hpjBW0P4WyyPTP\nXevKvl4I0Olrml2k4x3jqrkZAArQ8/WNNe4b5p4/RkqSRbeQaQPF/QymdTRZKZp1\ndDW/N4Kog+4wN86YbyrrtfzdUXkbKsf4sRDIErt3/7lA6XywQeU3UNpNsFuZB2Ex\nkhIF4DfJAgMBAAECgf8bSAmknAQGZoJbJGRNJJDAiBkIqe6HbgTjfe+rTCDvNueq\nusunQzggpgHPhcsSy1RzEzz2uY1KIqM7Kim4on4M9wdjUHc/NLO4GpIde88HxWLO\nQ9tz92H/HtDP9ucah25q9ve9Hlccjrde1We/ekwPi9nyW8oeeIdGUIGCdjL1lkRv\niPZClLVUCsF+WWC2wnApJ1LspZkBBBgqBJBtjLB9QJWx947AruWvrGVSwVxWjYbn\nruh4q/5XMPoyOZ3Mc5r4gt7YxgX34pcuv2DR4Q7viuFephw4YnN1Jl0Z8An14m9e\nrZPi1VF1XFOjrlUBaMqDFk52Seiym1+0y3/Wy80CgYEA8g7URpvrFHBI1dm1CfSO\nBRXlHCyRJRY19qSXhRt6dVkdFoTfthaOHiCWfCa1AfHK7eyo12EXuXfbVDILdZyM\ncsdYo0GjImANNEpMRRR/nXLQAwZD6r8EjfRdqgkQ2fyBGWgb1VthX/j+TqE44Z9/\nm3LJGwXl+qkghOdhwth9FTsCgYEA0wpeW/q7FllO51/AQYaeuAYuZqx3W9nqC3pp\nTXs7vIQ+QP/zUdW2pUngcCuzvwutrIHBp+JzuM1hUqCTZlnDqyms4x5IDIwQ/0Xw\no1+xeNmJYNWf7B7wrL/AB9lc2mENMFyOTT3NHtkgPrzkA8IxfYroKPpLUmh4n3wn\nfb2DBssCgYBydcyvLt90pLvsU6wl7vyRLw9NyU1BKkWOQbpceOfpQaBdm6tloTjG\nykYeovcI2ltJMpWMcx8S1rhiEeAVV1ealuU1Hb6UbLMRvOhf6heGTlpKc9X91SHE\nNn9r/il3ys0JRI+Uzkc3aLhoWhhZqGBhE2BBZQKhCwSTlA5IYrKTLQKBgQCP/xol\nLm5rMGLLMqUu0JkJvYX5nqIOdSDh1hN1v6kh1yXXGg0/AFCQdVkDkH4j0+cbtqmZ\nUMVfChvm0hPhGUyORHBnw6irjwKl+2XXQ5sHd37qKLjm1rGnXWq8sE5P3zqtjem2\nwH1uF/6NcDswaVd1bss78pRkVU5Am+kiPaXigwKBgHScH+0FhMkYbEE3LAIfpcPS\nxlmET2MvrBubq3alsx3rtDuYWl5VwD7WiYoo6bysHqhcgh13qpGRz0RlIJ7JxPgP\nxazR6ygV1OPxcAvEPQ9iMfvj2pp23NVAlLwkXuEIpvqBYh8KIuexOItLY/JSDAFV\ndKoruQypA3uvZedabjcv\n-----END PRIVATE KEY-----\n";
    private static String clientEmail = "firebase-adminsdk-wjdmx@my-mixue.iam.gserviceaccount.com";
    private static String clientId = "115098593776963865976";
    private static String authUri = "https://accounts.google.com/o/oauth2/auth";
    private static String tokenUri = "https://oauth2.googleapis.com/token";
    private static String authProviderCertUrl = "https://www.googleapis.com/oauth2/v1/certs";
    private static String clientCertUrl = "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-wjdmx%40my-mixue.iam.gserviceaccount.com";
    private static String universe_domain = "googleapis.com";


    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void init() throws IOException {
        // 将从 Nacos 获取的配置项组合为一个 JSON 字符串
        Map<String, Object> firebaseConfigMap = new HashMap<>();
        firebaseConfigMap.put("type", type);
        firebaseConfigMap.put("project_id", projectId);
        firebaseConfigMap.put("private_key_id", privateKeyId);
        firebaseConfigMap.put("private_key", privateKey);
        firebaseConfigMap.put("client_email", clientEmail);
        firebaseConfigMap.put("client_id", clientId);
        firebaseConfigMap.put("auth_uri", authUri);
        firebaseConfigMap.put("token_uri", tokenUri);
        firebaseConfigMap.put("auth_provider_x509_cert_url", authProviderCertUrl);
        firebaseConfigMap.put("client_x509_cert_url", clientCertUrl);
        firebaseConfigMap.put("universe_domain", universe_domain);

        String firebaseConfigJson = new ObjectMapper().writeValueAsString(firebaseConfigMap);

        // 将 JSON 字符串转换为 InputStream
        InputStream serviceAccountStream = new ByteArrayInputStream(firebaseConfigJson.getBytes());


        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        log.info("Firebase initialized successfully");
    }

    public static void main(String[] args) {

        String token = "eCjLidZwT-uQCXZdI-P2C1:APA91bE-l1DDpBtMvi9ZdzJzGLfW3Owp2h3xcO9Q9tqAZ6Q2mIdobtATzqQFWwIbukyk7fuuOGlWI7SBb73q23VLDDKkR9QqTJeYesKrb_UE2HGKv1gmJVznJWsPiijsA2y8ipb1FhZD";
        String title = "20240911";
        String body = "国足vs沙特 1:2";

        try {
            // 创建消息
//            Notification build = Notification.builder()
//                    .setBody(body)
//                    .setTitle(title)
//                    .build();

            Message message = Message.builder()
                    .setToken(token) // 设置设备的 registration token
//                    .setNotification(new Notification(title, body)) // 消息内容
                    .putData("redirect", "www.baidu.com")
                    .build();

            // 发送消息
            FirebaseMessaging instance = FirebaseMessaging.getInstance();
            String response = instance.send(message);
//            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
