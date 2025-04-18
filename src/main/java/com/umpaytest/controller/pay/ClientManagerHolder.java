package com.umpaytest.controller.pay;

import com.github.joekerouac.common.tools.io.IOUtils;
import com.github.joekerouac.common.tools.resource.impl.ClassPathResource;
import com.mxbc.pay.common.enums.SignAlgorithm;
import com.mxbc.pay.sdk.ClientConfig;
import com.mxbc.pay.sdk.ClientManager;
import com.mxbc.pay.sdk.SDKServiceFactory;

import java.io.IOException;

/**
 * @author JoeKerouac
 * @date 2023-03-14 15:59
 * @since 3.0.0
 */
public class ClientManagerHolder {

    /**
     * 实际使用中将这个声明为一个bean注入即可，注意，系统关闭时需要调用{@link ClientManager#close()}
     */
    public static final ClientManager clientManager;

    public static final String scene = "scm";

    public static final String bizCode = "scm";

    static {
        // 服务端的公钥各个环境是固定的
        ClassPathResource serverPublicKeyResource = new ClassPathResource("qa.pub");
        // 将客户端的私钥替换为真实的私钥
        ClassPathResource clientPrivateKeyResource = new ClassPathResource("qa.key");
        byte[] serverPublicKey;
        byte[] clientPrivateKey;
        try {
            serverPublicKey = IOUtils.read(serverPublicKeyResource.getInputStream(), true);
            clientPrivateKey = IOUtils.read(clientPrivateKeyResource.getInputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("密钥加载失败", e);
        }

        ClientConfig clientConfig = new ClientConfig();
        // 替换为实际的服务端地址
        clientConfig.setServerUri("https://pay-qa.mxbc.net/pay");
        // 替换为分配给本系统的scene
        clientConfig.setScene(scene);
        // 设置scene对应的私钥
        clientConfig.setClientPrivateKey(clientPrivateKey);
        // 设置服务端公钥
        clientConfig.setServerPublicKey(serverPublicKey);
        clientConfig.setSignAlgorithm(SignAlgorithm.SHA256WithRSA);
        clientConfig.setConcurrency(100);

        clientManager = SDKServiceFactory.newClientManager(clientConfig);

        Runtime.getRuntime().addShutdownHook(new Thread(clientManager::close));
    }

}
