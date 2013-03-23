package org.nsyang.net.socket.tcp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerClientListener extends Thread
{
    private ServerDispatcher mServerDispatcher;
    private ServerClientInfo mClientInfo;
    private BufferedReader in;
 
    public ServerClientListener(ServerClientInfo aClientInfo, ServerDispatcher aServerDispatcher)
    throws IOException
    {
        mClientInfo = aClientInfo;
        mServerDispatcher = aServerDispatcher;
        Socket socket = aClientInfo.socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }
 
    /**
     * Until interrupted, reads messages from the client socket, forwards them
     * to the server dispatcher's queue and notifies the server dispatcher.
     */
    public void run()
    {
        try {
           while (!isInterrupted()) {
               String message = in.readLine();
               if (message == null)break;
               mServerDispatcher.dispatchMessage(mClientInfo, message);
           }
        } catch (IOException ioex) {
           // Problem reading from socket (communication is broken)
        }
 
        // Communication is broken. Interrupt both listener and sender threads
        mClientInfo.mClientSender.interrupt();
        mServerDispatcher.deleteClient(mClientInfo);
    }
 
}