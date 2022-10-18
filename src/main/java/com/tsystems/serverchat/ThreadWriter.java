/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gets the message from the socket and adds it to the chat. Runs in a different
 * single thread.
 *
 * @author aalonsoa
 */
public class ThreadWriter implements Runnable {

    private ArrayList<String> messagesArray;
    private Chat chat;
    private ReentrantLock lock;

    public ThreadWriter(ArrayList<String> messagesArray, Chat chat, ReentrantLock lock)
    {
        this.chat = chat;
        this.messagesArray = messagesArray;
        this.lock = lock;
    }

    @Override
    public void run()
    {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

            lock.lock();
            for (String str : messagesArray) {
                try {
                    chat.addText(str);
                    messagesArray.remove(str);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            lock.unlock();
        }
    }

}
