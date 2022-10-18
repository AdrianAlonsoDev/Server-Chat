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
 * This class writes messages on a specific chat.
 *
 * @author ramaldon
 */
public class Chat {

    private ArrayList<Socket> clientSock;
    private String nameChat;

    /**
     * Constructor default
     */
    public Chat()
    {
    }

    /**
     * Constructor method
     *
     * @param nameChat
     * @param clientSock is Arraylist from Socket
     *
     */
    public Chat(String nameChat, ArrayList<Socket> clientSock)
    {
        this.nameChat = nameChat;
        this.clientSock = clientSock;
    }

    /**
     * getNameChat is method get from nameChat
     *
     * @return nameChat
     */
    public String getNameChat()
    {
        return nameChat;
    }

    /**
     * Method notificationChat for notification the events
     *
     */
    public void notificationChat()
    {

    }

    /**
     * Send a mensage to a expecific socket
     *
     * @param client socket where we send the mensage
     * @param text to send to the socket
     * @throws IOException for cant write in the socket
     */
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
    }

    /**
     * Send a specific mensage to all my sockets
     *
     * @param text to be sended
     * @throws IOException write error
     * @see write
     */
    private void broadcastAll(String text) throws IOException
    {
        for (Socket socket : clientSock) {
            write(socket, text);
        }
    }

    /**
     * Send a specific mensage to all my sockets except mine
     *
     * @param mensage to be sended
     * @throws IOException write error
     * @see write
     */
    private void broadcastAll(Message msg) throws IOException
    {
        for (Socket socket : clientSock) {
            if (!msg.getConecction().equals(socket)) {
                write(socket, msg.toString());
            }
        }
    }

    /**
     * Add message to the chat and broadcastAll
     *
     * @param msg message to be sended
     * @throws IOException write error
     * @see broadcastAll
     */
    public void addText(Message msg) throws IOException
    {
        broadcastAll(msg);
    }

    /**
     * This method add the buffered mesages to the broadcast.
     *
     * @param text to be added
     * @throws IOException if the messages can`t be writed.
     *
     * @see broadcastAll
     */
    public void addText(String text) throws IOException
    {
        broadcastAll(text);

    }

    /**
     * process is method
     *
     * @param read
     */
    private void process(String read)
    {
        //SEND MESAJE TO THE CHAT
    }

}
