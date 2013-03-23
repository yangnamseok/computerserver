package org.nsyang.net.socket.tcp.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class ServerClientSender extends Thread
{
    private Vector<Object> mMessageQueue = new Vector<Object>();
 
    private ServerDispatcher mServerDispatcher;
    private ServerClientInfo mClientInfo;
    private PrintWriter out;
 
    public ServerClientSender(ServerClientInfo aClientInfo, ServerDispatcher aServerDispatcher)
    throws IOException
    {
        mClientInfo = aClientInfo;
        mServerDispatcher = aServerDispatcher;
        Socket socket = aClientInfo.socket;
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }
 
    /**
     * Adds given message to the message queue and notifies this thread
     * (actually getNextMessageFromQueue method) that a message is arrived.
     * sendMessage is called by other threads (ServeDispatcher).
     */
    public synchronized void sendMessage(String aMessage)
    {
        mMessageQueue.add(aMessage);
        notify();
    }
 
    /**
     * @return and deletes the next message from the message queue. If the queue
     * is empty, falls in sleep until notified for message arrival by sendMessage
     * method.
     */
    private synchronized String getNextMessageFromQueue() throws InterruptedException
    {
        while (mMessageQueue.size()==0)
           wait();
        String message = (String) mMessageQueue.get(0);
        mMessageQueue.removeElementAt(0);
        return message;
    }
 
    /**
     * Sends given message to the client's socket.
     */
    private void sendMessageToClient(String aMessage)
    {
    	System.out.println(mClientInfo.key+":"+aMessage);
    	out.println(aMessage);
    	out.flush();
    }
 
    /**
     * Until interrupted, reads messages from the message queue
     * and sends them to the client's socket.
     */
    public void run()
    {
        try {
           while (!isInterrupted()) {
               String message = getNextMessageFromQueue();
               sendMessageToClient(message);
           }
        } catch (Exception e) {
           // Commuication problem
        }
 
        // Communication is broken. Interrupt both listener and sender threads
        mClientInfo.mClientListener.interrupt();
        mServerDispatcher.deleteClient(mClientInfo);
    }
 
}