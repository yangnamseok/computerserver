package org.nsyang.net.socket.tcp.server.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.nsyang.net.socket.tcp.server.ServerClient;

public abstract class ServerLogic {
	
    public abstract void process(String msg,String key,String ip,String port);
    
	private HashMap<String,ServerClient> clients; 
	
	
    public void process(String msg,String key,String ip,String port,HashMap<String,ServerClient> clients){
    	this.clients = clients;
    	process(msg,key,ip,port);
    }
    
	public void sendAll(String message){
		Set<String> keySet = clients.keySet();
		Iterator iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();			
        	ServerClient client = (ServerClient)clients.get(key);
			if(client!=null){
	        	System.out.println(key+":"+message);
				client.sendMessage(message);			
			}
         }		
	}
	
	public void send(String message,String... key){		
		int size = key.length;
        for (int i=0; i<size; i++) {
			ServerClient client = clients.get(key[i]);
			if(client!=null){
				client.sendMessage(message);			
			}
        }
	}
	
	public String[] keys(){		
		return (String[])clients.keySet().toArray();
	}

}
