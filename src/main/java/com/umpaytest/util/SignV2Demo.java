package com.umpaytest.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
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

    public static Gson gson;
    static {
        gson =  new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapterFactory(new DateNullAdapterFactory<>())
                .create();;
    }
    // 示例参数
    static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCV35kEnHASgO+lQvz3X+ucxZ+PMnw0FNzZ5AX1z/kMmWc/2KA+qRKopo8QDNxmiaOYEDI1r+lVRroszAGx9uyCcMhbptc5BIoaxkh9wgaQEXFciYi+PoNE4bdkqeshthld19ZqaB7W6S+56BWztf91h9uUU30GpqvFj/H6zqhfbg0Mq4FIf8aLZBI4IhWeRt2T65R6qKoVsSGaoyiwyiBojq4IVAUODkyJ3rhb4lrBtjpcjC3sAo5CAmsU/VC28G9/DXEt89ad/iIeSHF8t3bGt6r5yn7olvNMttLwWH76znrgIgCh940lDTG+feMj2Pv8PUg8P0zTG9/Wy7ITMOcpAgMBAAECggEARGW/+qe+SeOH+r7gnbCr4WcbU4mxvz8KL3Jt+enI+iQER9IAB7i3aDpjLesAptcHzv0NPQ0kCqP1kT4lQ+extjHsbk9tB3t335JAQQxMXHFpb9bgjhoPZF8yiAE36rGlT/uRwgv0jxXqjwhI5zQW+rde0YlslJ155Pa1c0Xe93WVfhmg5Z/4OlDIyZf8GQBM/UWr364vMTntCBqeP7BAi7FpUe7yFG1hqMqWx5Lbu1ZAbsnBn7sHHqbqxcVBxpiKJJulR9V42Rkfeubd6cbqyDClfSOIVXpLsUIitJNPFXVvmwmpwma1OeYdpWaZTa0qRONtFmBeUzSmKqPBqueHgQKBgQD2eeV/JIAZZLnOHCSKarh0xy+Gq0erJ3FH6r5Sxq44O7yAOmJjGgXzR5u8pECJEgm0NdSNvbvopZPtSqHRKWXPKSIcN9biYMkK4pGBKZcRRpt4E98OykFGniV5k9woaIMfXT/sKdnmGGXXUKReoXbzxfDMsf2qXF3UQQ/SnS2TmQKBgQCbqh9IJ0uSqoeOpiXgt6pl+pTByIxOlHhOJQW1dqyOmj5RJJH7fSYKRay4TYrv/jbTva0NK7Gsc+LKnOGx3Z6dUBv730MLr8p60gnfEnz6RMYAOprs2C+TXgjqCHEcfXTi+aZNtpYNAKjCx5VPpQ15qkVycXb6jRuntDkGOGsqEQKBgG6NAB3F7lr1bc1/m/glo/Rk/vElU/D1sP6z0IfqK2x3hrXw+f4/Rtn2jWjQN04DtCyLVHn61xSjZ7UwG2Jocgy4A1+qwAwroyDpvJ+1WtfZ1pPGPe8xUpPtDRv58O6C6gUgWzJ/Fwf4e31W41K6k1MFvlrhnshRw5gXUpSCAVZJAoGALo5+FqVyC+am7now3/nuo9uDPEYP+LydErot7+0ThP8K3lfytWXjx7S5CNJgWJEOstuK82J7HFn4naK4OetPsjswc2997PPhukQxoZ808+E32v+sMGQkLo40WlK2CdBX8j66h4mxbSgiUUdCF/UEh7vuU2EuNZeYitrzJzIekxECgYEAnMTrKR0XrwVp8lNFr+++J+YDhUd6759kz/NBHSPcyLffmbL0cdbWsDSZ1ZVxrT55ZVEg+jQvE7owMDqJN3FvkKgWrsVMGNzb/2TCxwbhsIYVySXn8s4ewGLXUOavKMyUpD6pLq8VTuz/H0YC5QHDmAC3IRJ9lxiFKA2jIr73pQg=";
//    static String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDYTcY+L/LxKCzKRjWVM8iT+PHuY6RLgHh0+bblzGUFum64w3tIgah+YFfF+BZ49EeYK9O9nPfTHx0IdPygIk5lyfWBQevdl+Ma3uTmWvi4UKJsaUOerhAnRrLGZaElnMBnXhiLGpTBZfKDjqWMCit9nGZASecsOk2R0zQUPBXNmaOHfuoUdL/8kdEkQKY1Ksv2t3aDEmfb+cyAd3BpBC2vM+HnhBl81lWd8aWSQC7fDMUFDtUt5AnLVfqnnxpFPgWTvvYyhjl2I9xhtWAKsuw5vAJFrvW45ChNmE7QEJLsj1KoSMrmwUEiRKkP/xiwx9aeb1K1l4YMehz45Z/6DKl3AgMBAAECggEAReJq6HCpd0zZm8ivSzNd3KET2UeML3CkenarpvcwtHcVitBtpnFttNb/xNBwwkd6Uinm0V5Dm4xz3uWdMUekOLLVhRt9TyzhjS7ymZJOxY4XzBr+Y+gtA+ONoxf2xqYsa7k1CMxfv1Rm1CZkSiiHAKBlUOQPsnRr1297ZgY7rR8cbN6nSvurf6ZhBe2OplSIRoS0QPwrPjC/Vl5LwC7hWYxqymrUYBtEryMishOwe2fdEYKrJOFVxucXv10QxqIQEGQb5zA4BcsLDFZDdsIXbygBHeDdfEjLhyzwoLnU4asdMeFUtkUTa08/L7duhuP74zcI3w14K8Yp8vnEPm16sQKBgQDtLgv2dj39hRB1ssPylAzqmeIRZShiZT3Pjbjta5secO2aLXQ1k8xJYGxufjCYkVbSD7AxbBP9M8W43qG6oPNLR1VEXLOCP1s6fnO5VMgGE0Qv4TyFPPBRozNRxOhpVVWShpTWozT2YJUUXe0jtHIvk9whNKmasZQTE9DlaWJZ1QKBgQDpd6lnhSCB9hFqxSUc0PriOG4P3B0Wh+1GGu/QgdDS+uLSP1Nw9/aGRl9mEh8tRiNWG0OMq4lcgYxz5+5bE0VNh1bB+OItNxXOgifN97G1pzAr4t0JAGLgzDF8dIfG/QB0zAyFQWw1R5DXX8B9VhAG84UL8zoY/jXWhX+dz+BwGwKBgQDFTyE0j3lShngC8gyAqRl1sT6YKgdGk+Kv376QBIyu7lM7h8S9LY8tG8IYyXGz+nS3m8V/1+FpKsC54ru4flznqxgMHvJW0E4cg0XST+124GBBVKb8UI273IC87YpK1mqx0rPPO0a8D+z1XI4CvgKgAemG1kCiKEh+9mU59EmVYQKBgQDfHJHx8hFc4XFHnduXrEb2BYN4+Jt7uq6P/pILquOLnGNDFW2CWhVnwLzOaKcOtyZx1WA6gHumwc8dvRnWQ5ZJPvs0J8rN9wSJ82xis0FLHdk1N3qQbaQQvFLWYu6WxZW4CIA2oIzuZdQPXtwvax2p5vLQtsJgvB9rQOzyPYR43QKBgQDJNQHRtcKWucnK/goBD1matDNzR7EVcGLAG9zQ+lHM2dZrvgH77B79veBVNQ6q3Q/ndlfQXx7luS9FGq0IXYtVikqJI3u6oRdO7pILFPF68rl/5ydOIhrZb7xUMsUAw7KKyuctpLq0qJAYD/L+4MJEjj5pWAH0e+3ugvtVp1HolA==";
    // 示例参数
    static String appId = "b2b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6";
//    static String appId = "RC98sMrgGvo9NqUFYAMyXbcMVx4PiSDV";


    public static void main(String[] args) throws Exception {
        // 示例
        // 1. 组装请求参数
        RequestForm form = new RequestForm();
//        form.setT(System.currentTimeMillis());
        form.setT(1747387027213L);
        form.setAppId(appId);


        // 提现审核
//        AuditRequestBodyForm audit = mockAuditForm();
//        form.setRequestId(audit.getRequestId());
//        String requestBody = gson.toJson(audit);
//        form.setRequestBody(requestBody);

        // 推送门店挂账
//        FsscPushBalanceCreditAccountingForm creditForm = mockCreditForm();
        TMSConfirmReceiveForm tmsConfirmReceiveForm = mockConfirmReceiveForm();
        form.setRequestId(tmsConfirmReceiveForm.getRequestId());
        String requestBody = gson.toJson(tmsConfirmReceiveForm);
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


    private static AuditRequestBodyForm mockAuditForm() {
        AuditRequestBodyForm requestBodyForm = new AuditRequestBodyForm();
        requestBodyForm.setRecordNo("TXTH2025041500002");
        requestBodyForm.setActionType(1);
        requestBodyForm.setRequestId("TXTH2025041500002");
        //        requestBodyForm.setRejectReason(null);
        return requestBodyForm;
    }

    private static FsscPushBalanceCreditAccountingForm mockCreditForm() {
        FsscPushBalanceCreditAccountingForm form = new FsscPushBalanceCreditAccountingForm();
        form.setThirdBusinessNo("fssc002");
        form.setShopCode("800100001");
        form.setAmount(new BigDecimal("200"));
        form.setTransactionType(1);
        form.setRequestId("fssc002");
        return form;
    }

    private static TMSConfirmReceiveForm mockConfirmReceiveForm() throws ParseException {
        TMSConfirmReceiveForm form = new TMSConfirmReceiveForm();
        form.setTransportTaskId("按运段生成应付凭证");
        form.setConfirmSignDate(DateUtils.parseDate("2025-03-28 16:41:48", "yyyy-MM-dd HH:mm:ss"));
        form.setSignPerson("MXBCadmin");
        form.setRequestId("864638830077999740074949");
        return form;
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
    static class AuditRequestBodyForm {
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
    static class FsscPushBalanceCreditAccountingForm implements Serializable {
        private static final long serialVersionUID = -7296590767944184452L;

        @NotBlank(message = "三方业务单号不能为空")
        @Size(max = 64, message = "三方业务单号不能超过64个字符")
        private String thirdBusinessNo;

        /**
         * 门店编码
         */
        @NotBlank(message = "门店编码")
        @Size(max = 20, message = "驳回原因不能超过20个字符")
        private String shopCode;

        /**
         * 挂账金额，必须大于0
         */
        @NotNull(message = "挂账金额不能为空")
        @DecimalMin(value = "0.0", inclusive = false, message = "挂账金额必须大于0")
        private BigDecimal amount;

        /**
         * 商贸公司编码
         */
        private String tradingCompany;

        /**
         * 交易类型：1.共享挂账-重复支付，2.共享挂账-线下转账
         */
        @NotNull(message = "交易类型不能为空")
        private Integer transactionType;

        /**
         * 请求id，幂等id，传recordNo即可
         */
        @NotBlank(message = "请求id不能为空")
        private String requestId;

    }

    @Data
    static class TMSConfirmReceiveForm implements Serializable {
        private static final long serialVersionUID = 1L;

        private String transportTaskId;

        /**
         * 确认签收日期
         */
        private Date confirmSignDate;

        /**
         * 签收人
         */
        private String signPerson;

        /**
         * 请求id，幂等id，传recordNo即可
         */
        @NotBlank(message = "请求id不能为空")
        private String requestId;

    }


    @Data
    static class RequestForm {
        @NotNull(message = "时间戳不能为空")
        private Long t;

        @NotBlank(message = "签名不能为空")
        private String sign;

        @NotBlank(message = "appId不能为空")
        private String appId;

        @NotBlank(message = "requestBody不能为空")
        private String requestBody;

        private String requestId;
    }
}
