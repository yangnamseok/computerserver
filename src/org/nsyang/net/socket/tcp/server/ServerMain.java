package org.nsyang.net.socket.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.nsyang.net.socket.tcp.server.logic.ServerLogic;
import org.nsyang.net.socket.tcp.server.logic.ServerLogicDefault;

public class ServerMain extends Thread{
	ServerLogic logic;
	int port;
		
    public ServerMain(){
    	this(9999,new ServerLogicDefault());
    }
    
    public ServerMain(int port,ServerLogic logic){
    	this.logic = logic;
    	this.port = port;
    }
    public void run(){
        // Open server socket for listening
        ServerSocket serverSocket = null;
        try {
           serverSocket = new ServerSocket(port);
           System.out.println("NakovChatServer started on port " + port);
        } catch (IOException se) {
           System.err.println("Can not start listening on port " + port);
           se.printStackTrace();
           System.exit(-1);
        }
 
        // Start ServerDispatcher thread
        ServerDispatcher serverDispatcher = new ServerDispatcher(logic);
        serverDispatcher.start();
 
        // Accept and handle client connections
        while (true) {
           try {
               Socket socket = serverSocket.accept();
               serverDispatcher.addClient(new ServerClientInfo(socket,serverDispatcher));
           } catch (IOException ioe) {
               ioe.printStackTrace();
           }
        }
    }
}