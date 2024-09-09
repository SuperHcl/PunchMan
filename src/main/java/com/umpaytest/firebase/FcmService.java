//package com.umpaytest.firebase;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//
///**
// * @author Hu.ChangLiang
// * @date 2024/9/9 16:26
// */
//public class FcmService {
//
//    static {
//        try {
//            init();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void init() throws IOException {
//        ClassPathResource classPathResource = new ClassPathResource("firebase-adminsdk.json");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(classPathResource.getInputStream()))
//                .build();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(options);
//        }
//        System.out.println("Firebase initialized successfully");
//    }
//
//    public static void main(String[] args) {
//
////        String token = "eCjLidZwT-uQCXZdI-P2C1:APA91bE-l1DDpBtMvi9ZdzJzGLfW3Owp2h3xcO9Q9tqAZ6Q2mIdobtATzqQFWwIbukyk7fuuOGlWI7SBb73q23VLDDKkR9QqTJeYesKrb_UE2HGKv1gmJVznJWsPiijsA2y8ipb1FhZD";
//        String title = "Firebase local test";
//        String body = "测试测试时十四hi是";
//
//        try {
//            // 创建消息
////            Notification build = Notification.builder()
////                    .setBody(body)
////                    .setTitle(title)
////                    .build();
//
//            Message message = Message.builder()
//                    .setToken(token) // 设置设备的 registration token
//                    .setNotification(new Notification(title, body)) // 消息内容
//                    .build();
//
//            // 发送消息
//            String response = FirebaseMessaging.getInstance().send(message);
//            System.out.println("Successfully sent message: " + response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
