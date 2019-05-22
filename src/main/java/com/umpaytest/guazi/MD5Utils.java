package com.umpaytest.guazi;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	public static  String getMD5Str(String str) {  
		
        MessageDigest messageDigest = null;  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        byte[] byteArray = messageDigest.digest();  
        StringBuffer md5StrBuff = new StringBuffer();  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
        return md5StrBuff.toString();  
    }  
	

	public static void main(String[] args){
	
		//String str="funcodeGck00011datetime20150920141800merid10001001transidtestcrq123";
		//String str="funcode0003datetime20150129095000meridbairongtransidtest1234count1querymonth2015-11";
		//String str="funcode0011datetime20150324113200meridbaidutransidtestcrq";
		//String str="funcode0001datetime20150511135000meridzhongzhichengtransidtestcount10";
		//String str="funcodeGcs00001datetime20151027113044meridbaitiaotransid20151027113044100";
		//String str="13601322830";
		//String str="13439310867";  35fdb0c394b15d6978d5e3b6347e6437
		//String str="13718350792";
	    String str="13641372350";
		//String str = "funcodeGup10005datetime20150920141800meriddatatangtransidtestcrq123";
		System.out.println(getMD5Str(str));
		
	
	}
}
