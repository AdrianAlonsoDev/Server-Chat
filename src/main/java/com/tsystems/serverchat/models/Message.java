/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.models;

import java.net.Socket;

/**
 * Class holder of a conecction message
 * @author dpadilla
 */
public class Message {
    private String text;
    private Socket conecction;

    /**
     * Constructor with param
     * @param text text i wanna send
     * @param conecction the socket that send the text
     */
    public Message(String text, Socket conecction) {
        this.text = text;
        this.conecction = conecction;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Socket getConecction() {
        return conecction;
    }

    public void setConecction(Socket conecction) {
        this.conecction = conecction;
    }

    @Override
    public String toString() {
        return text;
    }
    
    
    
}
