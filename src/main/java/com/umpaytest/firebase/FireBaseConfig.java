//package com.umpaytest.firebase;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//
///**
// * @author Hu.ChangLiang
// * @date 2024/9/9 16:20
// */
//@Slf4j
//public class FireBaseConfig {
//
//    static {
//        try {
//            init();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
////    @PostConstruct
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
//        log.info("Firebase initialized successfully");
//    }
//}
