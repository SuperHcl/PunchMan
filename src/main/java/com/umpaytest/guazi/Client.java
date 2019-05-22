package com.umpaytest.guazi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    private static Logger log = LoggerFactory.getLogger("ClientBcm");
    private static Logger logReqRes = LoggerFactory.getLogger("req_and_res_xml");
//    private String URL = "http://10.102.5.53:9006/umpaydc/dataQuery/";
      private String URL = "http://10.102.5.51:9005/umpaydc/dataQuery/";
    private static String LICENSE = "32xa0m72p617f91uu7fw";

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public String execute(String mobileid, String license, String merid,
                          String funcode) {
        String result = "";
        String reqXml = genReqXml(mobileid, license, merid, funcode);
        logReqRes.info("request:{}", reqXml);
        try {
            result = HttpUtil.post(URL, reqXml);
            logReqRes.info("response:{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成请求串 流水号,时间戳_四位随机数
     *
     * @param mobileid
     * @param license
     * @param merid
     * @param funcode
     * @return
     * @author huaxiaoqiang
     * @date 2016年5月18日 上午11:19:56 修改
     */
    public String genReqXml(String mobileid, String license, String merid,
                            String funcode) {
        int SeqID = new Random().nextInt(8999) + 1000;
        String datetime = sdf.format(new Date());
        String transid = datetime + "_" + SeqID++;
        String sign = MD5Utils.getMD5Str("funcode" + funcode + "datetime"
                + datetime + "merid" + merid + "transid" + transid);
        Map<String, String> map = new HashMap<String, String>();
        map.put("funcode", funcode);
        map.put("datetime", datetime);
        map.put("merid", merid);
        map.put("transid", transid);
        map.put("mobileid", mobileid);
        map.put("license", license);
        map.put("sign", sign);
        map.put("querymonth","201803");
		map.put("identityNo","411403199302091518");
        map.put("name","胡长亮");

        String reqXML = XmlUtils.mapToXml(map, "request");

        return reqXML;
    }

    public static void main(String[] args) throws Exception {

        Client client = new Client();
        //tmziq1h7y1e7um02zvgj 18292801010
        String result = client.execute("18208622008", "32xa0m72p617f91uu7fw",
                "10001001", "Gcs6000051");
        System.out.println("------------------------------------------");
        System.out.println(result);
    }

}
