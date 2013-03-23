package org.nsyang.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Nic {
	public static void main(String args[]) throws Exception {

	    List<InetAddress> addrList = new ArrayList<InetAddress>();
	    Enumeration<NetworkInterface> interfaces = null;
	    try {
	        interfaces = NetworkInterface.getNetworkInterfaces();
	    } catch (SocketException e) {
	        e.printStackTrace();
	    }

	    InetAddress localhost = null;

	    try {
	        localhost = InetAddress.getByName("127.0.0.1");
	    } catch (UnknownHostException e) {
	        e.printStackTrace();
	    }

	    while (interfaces.hasMoreElements()) {
	        NetworkInterface ifc = interfaces.nextElement();
	        Enumeration<InetAddress> addressesOfAnInterface = ifc.getInetAddresses();

	        while (addressesOfAnInterface.hasMoreElements()) {
	            InetAddress address = addressesOfAnInterface.nextElement();

	            if (!address.equals(localhost) && !address.toString().contains(":")) {
	                addrList.add(address);
	                System.out.println("FOUND ADDRESS ON NIC: " + address.getHostAddress());
	            }
	        }
	    }

	}
}
