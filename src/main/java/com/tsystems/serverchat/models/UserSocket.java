/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.models;

import java.net.Socket;

/**
 *
 * @author aalonsoa
 */
public class UserSocket {

    private User user;
    private Socket socket;
    private Chat chat;

    public UserSocket(User user, Socket socket, Chat chat)
    {
        this.user = user;
        this.socket = socket;
        this.chat = chat;
    }

    public Chat getChat()
    {
        return chat;
    }

    public void setChat(Chat chat)
    {
        this.chat = chat;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }

}
