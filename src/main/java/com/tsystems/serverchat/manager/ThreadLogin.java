/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.manager;

import com.tsystems.serverchat.models.UserSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author dpadilla
 */
public class ThreadLogin implements Runnable{
    private Socket client;
    private ArrayList<UserSocket> serverSockets;

    public ThreadLogin(Socket client, ArrayList<UserSocket> serverSockets) {
        this.client = client;
        this.serverSockets = serverSockets;
    }

    @Override
    public void run() {
        
        //COMPROBAR LEER REGISTRO O LOGIN
        
        
        //SI ES REGISTRO CREAR NUEVO USER
        
        
        
        //SI ES LOGIN 
    }
    
    
    
}
