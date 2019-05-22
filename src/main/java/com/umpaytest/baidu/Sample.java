package com.umpaytest.baidu;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author: Hucl
 * @date: 2019/4/17 15:14
 * @description: 百度人脸检测
 */
public class Sample {


    public static void main(String[] args) {
        AipFace client = new AipFace(BaiduConstants.APP_ID,BaiduConstants.API_KEY,BaiduConstants.SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        //调用接口
//        String path = "C:\\Users\\Hucl\\Pictures\\huge.jpg";
        JSONObject res = client.detect(BaiduConstants.HUGE_BASE64,"BASE64", new HashMap<>());
        System.out.println(res.toString(2));

    }


}
