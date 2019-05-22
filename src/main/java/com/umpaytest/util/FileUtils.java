package com.umpaytest.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Hucl
 * @date: 2019/4/25 10:57
 * @description:
 */
public class FileUtils {

    /**
     * 22      * 中国移动号码格式验证
     * 23      * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184
     * 24      * ,187,188,147,178,1705
     * 25      *
     **/
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";


    private static ArrayList<String> fileLists = new ArrayList<>(5120);
    private static String PATH = "C:\\Users\\Hucl\\Desktop\\0000_20190519.log";

    private FileUtils() {
    }

    private static void fileRead(String path) {

        try {
            FileReader reader = new FileReader(path);
            BufferedReader bf = new BufferedReader(reader);
            String str;
            //按行读取
            while ((str = bf.readLine()) != null) {
                fileLists.add(str);
            }
            bf.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getPhoneList() {
        fileRead(PATH);
        ArrayList<String> stringArrayList = new ArrayList<>();
        String[] strArr;
        for (String s : fileLists) {
            strArr = s.split(",");
            stringArrayList.add(getFilterStr(strArr[11]));
        }
        return stringArrayList;
    }

    public static void main(String[] args) {
//        String str = "52_9003,20190520060918206,20190520060918,bjgt83kirnn9k90hv07g,15583037582064314,Gcs6000051,03705055,unknow,P0000660,b06439b3ad7854282fe6e9d21a6ec3dc,1,unknow&211223197605044012&0&15942347124&杨金华&1&10000&1,4,0,unknow,1,0501,0,0,53,Pro00447,Pro00003&Pro00008&Pro00114&Pro00054&Pro00510&Pro00512&Pro00514&Pro00418&Pro00419&Pro00428&Pro00437&Pro00450&Pro00401&Pro00194&Pro00605&Pro00606&Pro00608&Pro01758,V3_1,1";
//        String[] Aa = str.split(",");
//        String aa = str.substring(str.lastIndexOf("&")-11, str.lastIndexOf("&"));
//        System.out.println(aa);
//
//        String subStr = Aa[11];
//        System.out.println(subStr);
////        subStr = subStr.substring(subStr.lastIndexOf("&")-23, subStr.lastIndexOf("&")-12);
//        subStr = getFilterStr(Aa[11]);
//        System.out.println(subStr);
        ArrayList<String> result = getPhoneList();
        ArrayList<String> chinaMobileList = new ArrayList<>();
        Map<String, String> allMap = new HashMap<>();
        Map<String, String> chinaMobileMap = new HashMap<>();



        System.out.println(result.toString());
        System.out.println("原始size()----" + result.size());
        for (String s : result) {
            allMap.put(s, s);
            if (isChinaMobilePhoneNum(s)) {
                chinaMobileList.add(s);
            }
        }

        for(String s : chinaMobileList) {
            chinaMobileMap.put(s,s);
        }

        System.out.println("过滤后size()----" + allMap.size());
        System.out.println(allMap.toString());

        System.out.println("----------------------------");
        System.out.println("移动手机号20190519未过滤的个数 "+chinaMobileList.size());
        System.out.println("移动手机号20190519过滤后的个数---"+chinaMobileMap.size());

    }


    private static String getFilterStr(String string) {
        String[] arr = string.split("&");
        return arr[3];
    }

    /**
     * 70      * 验证【移动】手机号码的格式
     * 71      *
     * 73      * @param str
     * 74      *            校验手机字符串
     * 75      * @return 返回true,否则为false
     * 76
     */
    public static boolean isChinaMobilePhoneNum(String str) {

        return str == null || str.trim().equals("") ? false : match(
                CHINA_MOBILE_PATTERN, str);
    }

    /**
     * 135      * 执行正则表达式
     * 136      *
     * 137      * @param pat
     * 138      *            表达式
     * 139      * @param str
     * 140      *            待验证字符串
     * 141      * @return 返回true,否则为false
     * 142
     */
    private static boolean match(String pat, String str) {
        Pattern pattern = Pattern.compile(pat);
        Matcher match = pattern.matcher(str);
        return match.find();
    }
}
