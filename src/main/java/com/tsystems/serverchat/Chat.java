/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author ramaldon
 */
public class Chat {
    private ArrayList<Socket> clientSock;
    private String text;
    
    private String nameChat;

    /**
     * Constructor defauld
     */
    public Chat() {
    }

    /**
     * Constructor method
     * @param nameChat 
     */
    public Chat(String nameChat,ArrayList<Socket> clientSock ) {
        this.nameChat = nameChat;
        this.clientSock=clientSock;
    }

    public String getNameChat() {
        return nameChat;
    }
    
    /**
     * Method notificationChat for notification the events
     */
    public void notificationChat(){
        
    }
    private void write(Socket client, String text) throws IOException
    {
        OutputStream output;
        try {
            output = client.getOutputStream();
        } catch (IOException ex) {
            throw new IOException("Output write socket IO Exception");
        }
        PrintWriter writer = new PrintWriter(output, true);

        writer.println(text);
        //writer.close();
    }

    /**
     * Send a specific mensage to all my sockets
     *
     * @param mensage to be sended
     * @throws IOException write error
     * @see write
     */
    private void broadcastAll(String text) throws IOException
    {
        for (Socket socket : clientSock) {
            write(socket, text);
        }
    }
    public void addText(String text) throws IOException {
        broadcastAll(text);
    
}

    private void process(String read)
    {
        //SEND MESAJE TO THE CHAT
    }
    
}
