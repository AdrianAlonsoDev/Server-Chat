/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author dpadilla
 */
public class ThreadReader {

    public void clearSocket(Socket client, ArrayList<Socket> clientSock ) {
        try {
            clientSock.remove(client);
            
        } catch (IOException ex) {
            throw new IOException("Imput read socket IO Exception");
        }
        
    }

}
