package org.nsyang.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class NSUtil {
	
	/**
	 * 문자열을 숫자인지 아닌지 확인하기
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		try {
	        Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	/**
	 * 주소에서 원하는 파라메터 값 가져오기
	 * @param url
	 * @param paramName
	 * @return
	 */
	public static String getUrlToParamValue(String url,String paramName){
		if(url!=null && paramName!=null){
			try {
				URL u = new URL(url);
				String[] params = u.getQuery().split("&");
				for(String param:params){
					String[] nv = param.split("=");
					if(nv.length>1 && nv[0].equals(paramName))return nv[1];
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		return null;
	}
	
	
	/**
	 * 글자를 유니코드 값으로 변경하기
	 * 예) 인터넷 주소 변경  %2f %3d 등등의...
	 * 
	 * @param str
	 * @return
	 */
	public static String getUnicode(String str){
		return getUnicode(str,"euc-kr");
	}
	
	public static String getUnicode(String str,String charset){
		try {
			str = URLEncoder.encode(str,charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return str;
	}
	
	public static boolean inputStreamToFile(InputStream is,String filePath){
		try {
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			int inByte;
			while((inByte = is.read()) != -1) fos.write(inByte);
			is.close();
			fos.close();
			return true;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	public static boolean download(HttpResponse res,String filePath){
		if(res!=null){
			HttpEntity entity = res.getEntity();
			try {
				return NSUtil.inputStreamToFile(entity.getContent(),filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		return false;
	}
	
}
