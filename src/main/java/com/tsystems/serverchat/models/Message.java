/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.models;

import java.io.IOException;
import java.net.Socket;

/**
 * Class holder of a conecction message
 *
 * @author dpadilla
 */
public class Message {

    private String text;
    private Socket conecction;
    private User user;
    private Chat chat;

    /**
     * Constructor with param
     *
     * @param text text i wanna send
     * @param conecction the socket that send the text
     */
    public Message(String text, Socket conection, User user, Chat chat)
    {
        this.text = text;
        this.conecction = conection;
        this.user = user;
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

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void beWrite() throws IOException
    {
        chat.addText(this);
    }

    public Socket getConecction()
    {
        return conecction;
    }

    public void setConecction(Socket conecction)
    {
        this.conecction = conecction;
    }

    public User getUser()
    {
        return user;
    }

    @Override
    public String toString()
    {
        return user.getNickname() + ": " + text;
    }

}
