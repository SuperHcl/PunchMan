package com.umpaytest.util;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Hu.ChangLiang
 * @date 2025/4/11 15:37
 */
@Slf4j
public class SignV2Demo {
    private static final String KEY_TIME_STAMP = "t";
    private static final String KEY_APP_ID = "appId";
    private static final String KEY_SIGN = "sign";
    private static final String KEY_REQUEST_BODY = "requestBody";
    private static final String KEY_ALGORITHM = "RSA";
    public static final String ALGO_RSA256 = "SHA256withRSA";

    public static Gson gson = new Gson();
    // 示例参数
    static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCV35kEnHASgO+lQvz3X+ucxZ+PMnw0FNzZ5AX1z/kMmWc/2KA+qRKopo8QDNxmiaOYEDI1r+lVRroszAGx9uyCcMhbptc5BIoaxkh9wgaQEXFciYi+PoNE4bdkqeshthld19ZqaB7W6S+56BWztf91h9uUU30GpqvFj/H6zqhfbg0Mq4FIf8aLZBI4IhWeRt2T65R6qKoVsSGaoyiwyiBojq4IVAUODkyJ3rhb4lrBtjpcjC3sAo5CAmsU/VC28G9/DXEt89ad/iIeSHF8t3bGt6r5yn7olvNMttLwWH76znrgIgCh940lDTG+feMj2Pv8PUg8P0zTG9/Wy7ITMOcpAgMBAAECggEARGW/+qe+SeOH+r7gnbCr4WcbU4mxvz8KL3Jt+enI+iQER9IAB7i3aDpjLesAptcHzv0NPQ0kCqP1kT4lQ+extjHsbk9tB3t335JAQQxMXHFpb9bgjhoPZF8yiAE36rGlT/uRwgv0jxXqjwhI5zQW+rde0YlslJ155Pa1c0Xe93WVfhmg5Z/4OlDIyZf8GQBM/UWr364vMTntCBqeP7BAi7FpUe7yFG1hqMqWx5Lbu1ZAbsnBn7sHHqbqxcVBxpiKJJulR9V42Rkfeubd6cbqyDClfSOIVXpLsUIitJNPFXVvmwmpwma1OeYdpWaZTa0qRONtFmBeUzSmKqPBqueHgQKBgQD2eeV/JIAZZLnOHCSKarh0xy+Gq0erJ3FH6r5Sxq44O7yAOmJjGgXzR5u8pECJEgm0NdSNvbvopZPtSqHRKWXPKSIcN9biYMkK4pGBKZcRRpt4E98OykFGniV5k9woaIMfXT/sKdnmGGXXUKReoXbzxfDMsf2qXF3UQQ/SnS2TmQKBgQCbqh9IJ0uSqoeOpiXgt6pl+pTByIxOlHhOJQW1dqyOmj5RJJH7fSYKRay4TYrv/jbTva0NK7Gsc+LKnOGx3Z6dUBv730MLr8p60gnfEnz6RMYAOprs2C+TXgjqCHEcfXTi+aZNtpYNAKjCx5VPpQ15qkVycXb6jRuntDkGOGsqEQKBgG6NAB3F7lr1bc1/m/glo/Rk/vElU/D1sP6z0IfqK2x3hrXw+f4/Rtn2jWjQN04DtCyLVHn61xSjZ7UwG2Jocgy4A1+qwAwroyDpvJ+1WtfZ1pPGPe8xUpPtDRv58O6C6gUgWzJ/Fwf4e31W41K6k1MFvlrhnshRw5gXUpSCAVZJAoGALo5+FqVyC+am7now3/nuo9uDPEYP+LydErot7+0ThP8K3lfytWXjx7S5CNJgWJEOstuK82J7HFn4naK4OetPsjswc2997PPhukQxoZ808+E32v+sMGQkLo40WlK2CdBX8j66h4mxbSgiUUdCF/UEh7vuU2EuNZeYitrzJzIekxECgYEAnMTrKR0XrwVp8lNFr+++J+YDhUd6759kz/NBHSPcyLffmbL0cdbWsDSZ1ZVxrT55ZVEg+jQvE7owMDqJN3FvkKgWrsVMGNzb/2TCxwbhsIYVySXn8s4ewGLXUOavKMyUpD6pLq8VTuz/H0YC5QHDmAC3IRJ9lxiFKA2jIr73pQg=";
    // 示例参数
    static String appId = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6";


    public static void main(String[] args) throws Exception {
        // 示例
        // 1. 组装请求参数
        RequestForm form = new RequestForm();
        form.setT(System.currentTimeMillis());
        form.setAppId(appId);

        RequestBodyForm requestBodyForm = new RequestBodyForm();
        requestBodyForm.setRecordNo("TXTH2025041500002");
        requestBodyForm.setActionType(1);
        requestBodyForm.setRequestId("TXTH2025041500002");
//        requestBodyForm.setRejectReason(null);

        form.setRequestId(requestBodyForm.getRequestId());

        String requestBody = gson.toJson(requestBodyForm);

        form.setRequestBody(requestBody);

        // 2. 签名
        TreeMap<String, Object> paramsMap = getParamsMap(form);
        String sign = sign(paramsMap, privateKey);

        System.out.println(sign);

        // 3. 设置签名
        form.setSign(sign);


        System.out.println(gson.toJson(form));
        // 5. 发送请求...
    }

    public static String sign(TreeMap<String, Object> params, String privateKey) throws Exception {
        String content = signContent(params);
        log.debug("签名内容：{}", content);
        return getSign(content, privateKey);
    }


    /**
     * 对返回参数进行加签
     *
     * @throws Exception
     */
    public static String getSign(String content, String privateKey) throws Exception {
        return Base64.getUrlEncoder().encodeToString(sign(content, privateKey));
    }

    public static byte[] sign(String content, String privateKey) throws IOException {
        return sign(content, Base64.getDecoder().decode(privateKey));
    }

    public static byte[] sign(String content, byte[] privateKey) {
        return sign(content, privateKey, "UTF-8");
    }


    /**
     * RSA签名
     *
     * @param content          待签名数据
     * @param privateKeyBase64 密钥
     * @param encode           字符集编码
     * @return 签名值
     */
    public static byte[] sign(String content, byte[] privateKeyBase64, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(privateKeyBase64);
            KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(ALGO_RSA256);
            signature.initSign(priKey);
            signature.update(content.getBytes(encode));
            return signature.sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按照规定顺序进行参数拼接
     *
     * @param form
     * @return
     */
    public static TreeMap<String, Object> getParamsMap(RequestForm form) {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put(KEY_TIME_STAMP, form.getT());
        params.put(KEY_APP_ID, form.getAppId());
        params.put(KEY_SIGN, form.getSign());
        params.put(KEY_REQUEST_BODY, form.getRequestBody());
        return params;
    }


    private static String signContent(TreeMap<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> entrys = params.entrySet();
        boolean first = true;
        for (Map.Entry<String, Object> entry : entrys) {
            Object value = entry.getValue();
            if (value != null) {
                String valueString = String.valueOf(value);
                if (StringUtils.isNotEmpty(valueString)) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append("&");
                    }
                    sb.append(entry.getKey()).append("=").append(value);
                }
            }
        }
        return sb.toString();
    }


    @Data
    static class RequestBodyForm {
        /**
         * 提现流水号，海外D的提现流水号
         */
        @NotBlank(message = "提现流水号不能为空")
        @Size(max = 64, message = "提现流水号不能超过64个字符")
        private String recordNo;

        /**
         * 审核动作类型：1：审核通过，已到账；2：驳回
         */
        @NotNull(message = "审核动作类型不能为空")
        private Integer actionType;

        /**
         * 请求id，幂等id，传recordNo即可
         */
        @NotBlank(message = "请求id不能为空")
        private String requestId;

        /**
         * 驳回原因, 当actionType为驳回操作时必填
         */
        @Size(max = 200, message = "驳回原因不能超过200个字符")
        private String rejectReason;
    }

    @Data
    static class RequestForm {
        @ApiModelProperty(value = "时间戳", required = true)
        @NotNull(message = "时间戳不能为空")
        private Long t;

        @ApiModelProperty(value = "签名", required = true)
        @NotBlank(message = "签名不能为空")
        private String sign;

        @ApiModelProperty(value = "appId", required = true)
        @NotBlank(message = "appId不能为空")
        private String appId;

        @ApiModelProperty(value = "请求体(业务参数json串)", required = true)
        @NotBlank(message = "requestBody不能为空")
        private String requestBody;

        private String requestId;
    }
}
