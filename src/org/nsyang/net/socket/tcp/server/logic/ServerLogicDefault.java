package org.nsyang.net.socket.tcp.server.logic;



public class ServerLogicDefault extends ServerLogic{
	
	@Override
	public void process(String msg, String key, String ip, String port) {
		String message = ip+":"+port+" => "+msg;
		System.out.println(message);
		sendAll(message);
	}	
}
