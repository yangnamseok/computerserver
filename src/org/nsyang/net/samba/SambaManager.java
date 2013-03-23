package org.nsyang.net.samba;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import jcifs.netbios.NbtAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbRandomAccessFile;

public class SambaManager {
	 public static void main(String[] args) {
		    try{
		    	/*
	    	String url = "smb://{ip};{user}:{pw}@{ip}/{drive}/{path}/{filename}"
		      // jcifs.Config.setProperty( "jcifs.netbios.wins", "192.168.1.220" );
		      //smb://[[[domain;]username[:password]@]server[:port]/[[share/[dir/]file]][?[param=value[param2=value2[...]]]
		      String url="smb://domain;user_name:user_password@server_name/directory/test_file.txt";
		        */
		    new SambaManager().list();
		    }catch(Exception e){
		  System.out.println(e);
		  }
		 }
	 public void list(){
		try {
			InetAddress addr = NbtAddress.getByName( "192.168.0.101" ).getInetAddress();
			System.out.println(addr.getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,"namseok","yns123"); 
//		String smburl = String.format("smb://%s/%s/%s/","192.168.0.101", "resource center", ""); 
		String smburl = String.format("smb://%s/%s/%s/","192.168.0.101", "", ""); 
		try {
			SmbFile file = new SmbFile(smburl, auth);
			checkHosts("192.168.0");
			SmbFile[] files = file.listFiles(); 
			for(SmbFile f:files){
		//		System.out.println(f.getPath());
		//		System.out.println(f.getName());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	 }
	 
	 public boolean copyFile(String url){
		 SmbFile file;
		 try {
			file = new SmbFile(url);
			 SmbRandomAccessFile rf = new SmbRandomAccessFile(file, "r");
			 byte[] b = new byte[10000];
			 int n;
			 while(( n = rf.read( b )) > 0 ) {
				 System.out.write(b, 0, n);	
			 }
		 } catch (MalformedURLException | SmbException | UnknownHostException e) {
			e.printStackTrace();
		 }
		 return false;
	 }
	 
	 public void checkHosts(String subnet){
		   int timeout=1500;
		   for (int i=1;i<254;i++){
		       String host=subnet + "." + i;
		       try {
				if (InetAddress.getByName(host).isReachable(timeout)){
				       System.out.println(host + " is reachable");
				   }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		}
}
