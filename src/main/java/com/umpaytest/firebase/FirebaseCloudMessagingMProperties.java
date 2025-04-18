package com.umpaytest.firebase;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;

/**
 * 使用firebase cloud messaging的配置
 *
 * @author Hu.ChangLiang
 * @date 2024/9/11 15:47
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "fcm")
public class FirebaseCloudMessagingMProperties {

    // 海外 MyMiXue app 的配置
    private Configuration haiwaiMymixue;

//    @PostConstruct
    public void init() throws IOException {
        // 允许多实例配置
        if (Objects.nonNull(haiwaiMymixue)) {
            log.info("构建海外app的FCM...");
            FirebaseContext.build(FirebaseContext.MY_MI_XUE, haiwaiMymixue);
        }
    }

    @Data
    public static class Configuration {
        private String type;
        private String projectId;
        private String privateKeyId;
        private String privateKey;
        private String clientEmail;
        private String clientId;
        private String authUri;
        private String tokenUri;
        private String authProviderCertUrl;
        private String clientCertUrl;
        private String universeDomain;
    }
}
