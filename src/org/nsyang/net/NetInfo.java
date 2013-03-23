package org.nsyang.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetInfo {
	public static String ip() throws UnknownHostException{
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress();
	}

	public static String host() throws UnknownHostException{
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostName();
	}
	public static String mac() throws UnknownHostException, SocketException{
		InetAddress addr = InetAddress.getLocalHost();
		NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
		byte[] mac = ni.getHardwareAddress();
		String macAddr="";
		for(int i=0;i<mac.length;i++){
			macAddr+=String.format("%02X%s",mac[i],(i<mac.length-1)?"-":"");
		}
		return macAddr;
	}
	
	public static void main(String args[]) throws UnknownHostException, SocketException{
		System.out.println("ip: "+ip());
		System.out.println("host: "+host());
		System.out.println("mac: "+mac());
	}
}
