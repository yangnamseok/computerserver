package org.nsyang.net.socket.tcp.server;

import java.util.HashMap;
import java.util.Vector;

import org.nsyang.net.socket.tcp.server.logic.ServerLogic;

public class ServerDispatcher extends Thread
{
    private Vector mMessageQueue = new Vector();
    private HashMap clients = new HashMap();
    ServerLogic logic;
    
    public ServerDispatcher(ServerLogic logic){
    	this.logic = logic;
    }
    /**
     * Adds given client to the server's client list.
     */
    public synchronized void addClient(ServerClientInfo clientInfo) {
    	clients.put(clientInfo.key,clientInfo);
    }
 
    /**
     * Deletes given client from the server's client list
     * if the client is in the list.
     */
    public synchronized void deleteClient(ServerClientInfo clientInfo)
    {
    	clients.remove(clientInfo.key);
    }
 
    /**
     * Adds given message to the dispatcher's message queue and notifies this
     * thread to wake up the message queue reader (getNextMessageFromQueue method).
     * dispatchMessage method is called by other threads (ClientListener) when
     * a message is arrived.
     */
    public synchronized void dispatchMessage(ServerClientInfo aYfClientInfo, String aMessage){
    	String senderInfo[] = new String[4];
    	senderInfo[0] = aMessage;
    	senderInfo[1] = aYfClientInfo.key;
    	senderInfo[2] = aYfClientInfo.getIp();
    	senderInfo[3] = "" + aYfClientInfo.getPort();        
        mMessageQueue.add(senderInfo);
        notify();
    }
 
    /**
     * @return and deletes the next message from the message queue. If there is no
     * messages in the queue, falls in sleep until notified by dispatchMessage method.
     */
    private synchronized String[] getNextMessageFromQueue()
    throws InterruptedException
    {
        while (mMessageQueue.size()==0)wait();
        String[] message = (String[]) mMessageQueue.get(0);
        mMessageQueue.removeElementAt(0);
        return message;
    }
 
    /**
     * Sends given message to all clients in the client list. Actually the
     * message is added to the client sender thread's message queue and this
     * client sender thread is notified.
     */
    private synchronized void sendMessageToAllClients(String[] aMessage)
    {
    	if(clients!=null && clients.size()>0){
        	logic.process(aMessage[0],aMessage[1],aMessage[2],aMessage[3],clients);    		
    	}
    }
 
    /**
     * Infinitely reads messages from the queue and dispatch them
     * to all clients connected to the server.
     */
    public void run()
    {
        try {
           while (true) {
               String message[] = getNextMessageFromQueue();
               sendMessageToAllClients(message);
           }
        } catch (InterruptedException ie) {
           // Thread interrupted. Stop its execution
        }
    }
 
}