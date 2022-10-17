/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aalonsoa
 */
public class ThreadWriter implements Runnable {

    private ArrayList<String> messagesArray;
    private Chat chat;

    public ThreadWriter(ArrayList<String> messagesArray, Chat chat)
    {
        this.chat = chat;
        this.messagesArray = messagesArray;
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

            for (String str : messagesArray) {
                try {
                    chat.addText(str);
                    messagesArray.remove(str);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
