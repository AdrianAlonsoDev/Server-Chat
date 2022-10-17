/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat;

import static com.tsystems.serverchat.Constant.*;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author aalonsoa
 */
public class ServerChat {

    private ServerSocket serverSocket;

    public ServerChat() throws IOException
    {
        serverSocket = new ServerSocket(PORT);
    }

    /*
    * Starts the server
     */
    public void run()
    {

    }

}
