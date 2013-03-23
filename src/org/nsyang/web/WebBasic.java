package org.nsyang.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.nsyang.util.NSUtil;

public class WebBasic {
	final public static String CHAR_SET = "EUC-KR";
	final public static String PATH_DOWNLOAD = "c:/Download/";
	
	public static enum METHOD{
		POST,
		GET
	}
	
	public String url="";
	
	DefaultHttpClient client = new DefaultHttpClient();
	  
	HttpPost post;
    HttpGet httpget;
    List <NameValuePair> parameters = new ArrayList <NameValuePair>();
    
    private HttpResponse res;
    
    public HttpResponse exec(METHOD method,String url,String[][] params) throws ClientProtocolException, IOException{
		 // 417 에러 방지
		client.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,false);

		if(params!=null){
			for(String[] param:params){
				parameters.add(new BasicNameValuePair(param[0],param[1])); 			
			}
		}
		
		switch (method) {
		case POST:
			post = new HttpPost(url);
			if(params!=null)post.setEntity(new UrlEncodedFormEntity(parameters,CHAR_SET));
			res = client.execute(post);
			break;
		case GET:
			httpget = new HttpGet(url);
			res = client.execute(httpget);
			break;
		default:
			break;
		}
    	return res;
    }
    
	
	public String print(boolean isPrint){
		StringBuilder sb = new StringBuilder();
		try {
			HttpEntity entity=null;
			if(res!=null)entity = res.getEntity();

			 if (entity != null) {
	                InputStream instream = entity.getContent();
	                byte[] tmp = new byte[2048];
	                while (instream.read(tmp) != -1) {
	                    if(isPrint)System.out.println(new String(tmp,CHAR_SET));
	                    sb.append(new String(tmp,CHAR_SET));
	                }
	            }
		} catch (IOException e) {
			e.printStackTrace();
		}	
		end();
		return sb.toString();
	}
	
	
	public void end(){
		try {
			if(res!=null)EntityUtils.consume(res.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	public void close(){
		 client.getConnectionManager().shutdown();
	}
	
	public boolean download(String fileName){
		return NSUtil.download(res, PATH_DOWNLOAD+fileName);
	}
}
