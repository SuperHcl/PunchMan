package com.umpaytest.baidu;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author: Hucl
 * @date: 2019/4/17 15:37
 * @description: 人脸对比
 */
public class FaceContrast {

    public static void main(String[] args) {
        AipFace client = new AipFace(BaiduConstants.APP_ID,BaiduConstants.API_KEY,BaiduConstants.SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        contrast(client);
    }

    private static void contrast(AipFace client) {
        // image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
        MatchRequest req1 = new MatchRequest(BaiduConstants.HUGE_BASE64, "BASE64");
        MatchRequest req2 = new MatchRequest(BaiduConstants.HUGE1_BASE64, "BASE64");
        ArrayList<MatchRequest> requests = new ArrayList<>();
        requests.add(req1);
        requests.add(req2);

        JSONObject res = client.match(requests);
        System.out.println(res.toString(2));
    }
}
