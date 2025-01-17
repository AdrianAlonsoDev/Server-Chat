package com.tsystems.serverchat.models;

import com.tsystems.serverchat.ConnectionDetails;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class writes messages on a specific chat.
 *
 * @author ramaldon
 */
public class Chat {

    private ArrayList<UserSocket> clientSock;
    private String text;
    private String nameChat;

    /**
     * Constructor defauld
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
    public Chat(String nameChat, ArrayList<UserSocket> clientSock)
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
        for (UserSocket socket : clientSock) {
            write(socket.getSocket(), text);
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
        for (UserSocket socket : clientSock) {
            if (!msg.getConecction().equals(socket.getSocket())) {
                Logger.getLogger(Chat.class.getName()).info(
                        msg.getUser().getNickname() + ".." + socket.getUser().getNickname());
                write(socket.getSocket(), msg.toString());
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
    public void addText(Message msg) throws IOException {
        if(msg.getUser().getNickname() != null)
            Logger.getLogger(Chat.class.getName()).info(msg.getUser().getNickname()+".."+msg.getChat().nameChat);
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

    public void addUser(UserSocket user) throws IOException
    {
        clientSock.add(user);
        Message systemMessage = new Message("Login to the user: " + user.getUser().getNickname(), user.getSocket(), ConnectionDetails.SYSTEMUSER, user.getChat());
        addText(systemMessage);
    }

    public void removeUser(UserSocket user) throws IOException
    {
        clientSock.remove(user);
        Message systemMessage = new Message("The user has left: " + user.getUser().getNickname(), user.getSocket(), ConnectionDetails.SYSTEMUSER, user.getChat());
        addText(systemMessage);
    }

}
