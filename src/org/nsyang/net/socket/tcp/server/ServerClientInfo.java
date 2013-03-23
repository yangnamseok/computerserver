package org.nsyang.net.socket.tcp.server;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;

public class ServerClientInfo implements ServerClient{
	
	public String key = "";	
    public Socket socket = null;
    public ServerClientListener mClientListener = null;
    public ServerClientSender mClientSender = null;
    
    public ServerClientInfo(Socket socket,ServerDispatcher serverDispatcher) throws IOException{
    	//this.key = ""+System.currentTimeMillis(); 
    	this.key = getMac();
    	System.out.println("?ëÏÜç: "+key+" read");
    	this.socket = socket;
        mClientListener = new ServerClientListener(this, serverDispatcher);
        mClientSender = new ServerClientSender(this, serverDispatcher);
    }
    
    private String getMac() throws SocketException{
		NetworkInterface ni = NetworkInterface.getByInetAddress(socket.getLocalAddress());
		byte[] mac = ni.getHardwareAddress();
		String macAddr="";
		for(int i=0;i<mac.length;i++){
			macAddr+=String.format("%02X%s",mac[i],(i<mac.length-1)?"-":"");
		}
		return macAddr;
    }
    
    public String getIp(){
    	return socket.getInetAddress().getHostAddress();
    }
    public int getPort(){
    	return socket.getPort();
    }    
    
    public void sendMessage(String message){
        mClientSender.sendMessage(message);	
    }    
}