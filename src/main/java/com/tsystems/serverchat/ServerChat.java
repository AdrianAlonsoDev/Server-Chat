/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat;

import static com.tsystems.serverchat.Constant.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author aalonsoa
 */
public class ServerChat {

    private ServerSocket serverSocket;
    private ArrayList<Socket> clientSock = new ArrayList<>();

    public ServerChat() throws IOException
    {
        serverSocket = new ServerSocket(PORT);
        clientSock = new ArrayList<>();
    }

    /*
    * Starts the server
     */
    public void run() throws IOException
    {

        while (true) {

            InetAddress ia = InetAddress.getLocalHost();
            System.out.println("ip:" + ia);
            System.out.println("Server listening for a connection");
            Socket clientSocket = serverSocket.accept();

            System.out.println("Received connection ");
            clientSock.add(clientSocket);

            process(read(clientSocket));

        }

    }

    /**
     * Read from the socket the mensaje
     *
     * @param client socket to be readed
     * @return text that has been readed from the socket
     * @throws IOException the socket can not be readed
     */
    private String read(Socket client) throws IOException
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
        System.out.println(text);
        return text;
    }

    /**
     * Send a mensage to a expecific socket
     *
     * @param client socket where we send the mensage
     * @param text to send to the socket
     * @throws IOException cant write in the socket
     */
    private void write(Socket client, String text) throws IOException
    {
        OutputStream output;
        try {
            output = client.getOutputStream();
        } catch (IOException ex) {
            throw new IOException("Output write socket IO Exception");
        }
        PrintWriter writer = new PrintWriter(output, true);

        writer.println(text);
        //writer.close();
    }

    /**
     * Send a specific mensage to all my sockets
     *
     * @param mensage to be sended
     * @throws IOException write error
     * @see write
     */
    private void broadcastAll(String mensage) throws IOException
    {
        for (Socket socket : clientSock) {
            write(socket, mensage);
        }
    }

    private void process(String read)
    {
        //SEND MESAJE TO THE CHAT
    }

}
