/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.models;

import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author aalonsoa
 */
public class UserSocket implements Serializable {

    private User user;
    private Socket socket;

    public UserSocket(User user, Socket socket)
    {
        this.user = user;
        this.socket = socket;
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
