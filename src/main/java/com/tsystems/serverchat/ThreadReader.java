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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpadilla
 */
public class ThreadReader implements Runnable {

    private ArrayList<Socket> clientSock;
    private ArrayList<String> unProcessText;

    public ThreadReader(ArrayList<Socket> clientSock ,ArrayList<String> _unProcessText) {
        this.unProcessText = _unProcessText;
        this.clientSock = clientSock;
    }

    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadReader.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (Socket socket : clientSock) {

                if (socket.isConnected()) {
                    try {
                        read(socket);
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        clearSocket(socket);
                        //true
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /**
     * Read from the socket the mensaje
     *
     * @param client socket to be readed
     * @return text that has been readed from the socket
     * @throws IOException the socket can not be readed
     */
    private void read(Socket client) throws IOException {
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
     * This method safely deletes a socket.
     * @param client socket to be deleted
     * @throws IOException if the socket is already close or disconnected
     */
    public void clearSocket(Socket client) throws IOException {

//        try (client) {
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

//        } catch (IOException ex) {
//            throw new IOException("Imput read socket IO Exception");
//        }
    }

}
