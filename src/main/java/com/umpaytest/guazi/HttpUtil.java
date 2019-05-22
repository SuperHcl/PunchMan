package com.umpaytest.guazi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	private final static Logger _log = LoggerFactory.getLogger("HttpUtil");
	private final static String REQUEST_METHOD = "POST";
	private final static String REQUEST_CONTENT_TYPE = "Content-type";
	private final static String REQUEST_CONTENT_TYPE_VALUE = "text/plain";
	private final static String REQUEST_CHARSET = "Accept-Charset";
	private final static String REQUEST_CHARSET_VALUE = "UTF-8";
	private final static String REQUEST_CONTENT_LENGTH = "Content-Length";

	private final static int READ_TIME_OUT = 15 * 1000;
	private final static int CONNECT_TIME_OUT = 2 * 1000;

	
	public static String post(String u, String str) throws Exception{
		return HttpUtil.post(u, str, READ_TIME_OUT, CONNECT_TIME_OUT);
	}

	public static String post(String u, String str,int readTimeOut , int connectTimeOut) throws Exception{

		URL url;
		url = new URL(u);

		HttpURLConnection huc;
		DataOutputStream printout = null;
		huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod(REQUEST_METHOD);
		huc.setRequestProperty(REQUEST_CONTENT_TYPE,REQUEST_CONTENT_TYPE_VALUE);
		huc.setRequestProperty(REQUEST_CHARSET, REQUEST_CHARSET_VALUE);
		huc.setRequestProperty(REQUEST_CONTENT_LENGTH,String.valueOf(str.getBytes().length));
		huc.setDoOutput(true);
		huc.setReadTimeout(readTimeOut);
		huc.setConnectTimeout(connectTimeOut);
		printout = new DataOutputStream(huc.getOutputStream());
		printout.write(str.getBytes());
		printout.flush();
		
		if (printout != null) {
			try {
				printout.close();
			} catch (IOException e) {
				_log.error("http close printout error:", e);
			}
		}

		StringBuffer sb = new StringBuffer();
		String line;
		InputStream is = null;
		BufferedReader br = null;
		
		
		try {
			is = huc.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (Exception e) {
			_log.error("http input stream error:", e);
			return null;
		} finally {
			try {
				if(br!=null){
					br.close();
				}
				if(is!=null){
					is.close();
				}
				if(huc!=null){
					huc.disconnect();
				}
			} catch (Exception e) {
				_log.error("http close input stream error:", e);
				return null;
			}
		}		
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		post("http://10.10.111.11", "asefsafsafe");
	}	

}