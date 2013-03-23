package org.nsyang.net.socket.tcp;

import org.nsyang.net.socket.tcp.server.ServerMain;
import org.nsyang.net.socket.tcp.server.logic.ServerLogic;

public abstract class ServerManager extends ServerLogic{

	ServerMain server;
	
	public ServerManager(int port){
		this.server = new ServerMain(9999,this);		
	}
	
	public void start(){
		this.server.start();	
	}
		
	@Override
	public abstract void process(String msg, String key, String ip, String port);

}
