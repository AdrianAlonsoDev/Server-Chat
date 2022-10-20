/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.manager;

import static com.tsystems.serverchat.ConnectionDetails.*;
import com.tsystems.serverchat.models.Chat;
import com.tsystems.serverchat.models.Message;
import com.tsystems.serverchat.models.User;
import com.tsystems.serverchat.models.UserSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads a socket which is a client with a message and sends it to ThreadWriter
 * so it can be sent to a chat.
 *
 * @author dpadilla
 */
public class ThreadReader implements Runnable {

    private ArrayList<UserSocket> clientSock;
    private ArrayList<Message> unProcessText;
    private ReentrantLock lock;
    private ArrayList<Chat> activeChats;

    public ThreadReader(ArrayList<UserSocket> clientSock, ArrayList<Message> _unProcessText, ReentrantLock lock, ArrayList<Chat> activeChats)
    {
        this.unProcessText = _unProcessText;
        this.clientSock = clientSock;
        this.lock = lock;
        this.activeChats=activeChats;
    }

    @Override
    public void run()
    {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadReader.class.getName()).log(Level.SEVERE, null, ex);
            }

            lock.lock();
            for (UserSocket socket : clientSock) {
                try {
                    if (socket.getSocket().isConnected()) {
                        read(socket.getSocket());
                    } else {
                        clearSocket(socket.getSocket());
                        //true
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ThreadReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            lock.unlock();
        }
    }

    /**
     * Read from the socket the mensaje
     *
     * @param client socket to be readed
     * @return text that has been readed from the socket
     * @throws IOException the socket can not be readed
     */
    public void read(Socket client) throws IOException
    {
        InputStream input;
        String text = "";
        String command="";
        try {
            input = client.getInputStream();
        } catch (IOException ex) {
            throw new IOException("Imput read socket IO Exception");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        while (reader.ready()) {
            text += reader.readLine();
        }
        
        if(text.contains(COMMANDCHANGECHAT)){
            command=text.split(" ")[1];
            
            Chat toLeave=null;
            UserSocket currentUser=null;

            for (UserSocket userSocket : clientSock) {
                if (userSocket.getSocket().equals(client)) {
                    toLeave = userSocket.getChat();
                    currentUser=userSocket;
                }
            }
            
            Chat toChange= searchChat(command, currentUser);
            toLeave.removeUser(currentUser);
            toChange.addUser(currentUser);
            currentUser.setChat(toChange);
        }        
        else if (!text.equals("")) {
            UserSocket currentUser=null;

            for (UserSocket userSocket : clientSock) {
                if (userSocket.getSocket().equals(client)) {
                    currentUser = userSocket;
                }
            }

            unProcessText.add(new Message(text, client, currentUser.getUser(),currentUser.getChat()));

        }

    }

    /**
     * This method safely deletes a socket.
     *
     * @param client socket to be deleted
     * @throws IOException if the socket is already close or disconnected
     */
    public void clearSocket(Socket client) throws IOException
    {
        if (client.isConnected()) {
            client.close();
            if (client.isClosed()) {
                clientSock.remove(client);
                if (clientSock.contains(client)) {
                    throw new IOException("ThreadReader clearSocket IO Exception Remove");
                }
            } else {
                throw new IOException("ThreadReader clearSocket IO Exception Close");
            }

        } else {
            throw new IOException("ThreadReader clearSocket IO Exception Connection");
        }
    }

    private Chat searchChat(String command, UserSocket client) {
        Chat toAdd=null;
        for (Chat activeChat : activeChats) {
            if(activeChat.getNameChat().equals(command))
                toAdd=activeChat;
        }
        
        if(toAdd==null){
            toAdd=new Chat(command, new ArrayList<>());
        }
        
        return toAdd;
    }

}
