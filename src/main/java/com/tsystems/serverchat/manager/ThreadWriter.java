/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.manager;

import com.tsystems.serverchat.models.Chat;
import com.tsystems.serverchat.models.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Writes a socket which is a client with a message and sends it to a chat.
 *
 * @author aalonsoa
 */
public class ThreadWriter implements Runnable {

    private ArrayList<Message> messagesArray;
    private Chat chat;
    private ReentrantLock lock;

    public ThreadWriter(ArrayList<Message> messagesArray, Chat chat, ReentrantLock lock)
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
            ArrayList<Message> readArray = new ArrayList<>(messagesArray);
            for (Message msg : readArray) {
                try {
                    msg.beWrite();
                    messagesArray.remove(msg);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            lock.unlock();
        }
    }

}
