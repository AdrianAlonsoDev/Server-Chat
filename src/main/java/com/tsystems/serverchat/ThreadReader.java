/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat;

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
 * Reads a socket which is a client with a message and
 *
 * @author dpadilla
 */
public class ThreadReader implements Runnable {

    private ArrayList<Socket> clientSock;
    private ArrayList<String> unProcessText;
    private ReentrantLock lock;

    public ThreadReader(ArrayList<Socket> clientSock, ReentrantLock lock)
    {
        this.clientSock = clientSock;
        this.lock = lock;
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
            for (Socket socket : clientSock) {
                try {
                    if (socket.isConnected()) {
                        read(socket);
                    } else {
                        clearSocket(socket);
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
    private void read(Socket client) throws IOException
    {
        InputStream input;
        String text = "";
        try {
            input = client.getInputStream();
        } catch (IOException ex) {
            throw new IOException("Imput read socket IO Exception");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        while (reader.ready()) {
            text += reader.readLine();
        }

        if (!text.equals("")) {
            unProcessText.add(text);
        }

    }

    /**
     * Disconnects the socket and removes it from the socket list.
     *
     * @param client Socket client that will be removed and disconnected.
     * @throws IOException if the I/O operation is interrupted.
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

}
