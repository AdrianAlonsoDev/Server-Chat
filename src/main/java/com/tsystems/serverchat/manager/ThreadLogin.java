/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.manager;

import static com.tsystems.serverchat.ConnectionDetails.*;
import com.tsystems.serverchat.models.User;
import com.tsystems.serverchat.models.UserSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpadilla
 */
public class ThreadLogin implements Runnable {

    private Socket client;
    private ArrayList<UserSocket> serverSockets;
    private boolean correctOperation;
    private User logedUser;
    private UserManager db;

    public ThreadLogin(Socket client, ArrayList<UserSocket> serverSockets,UserManager um) {
        this.client = client;
        this.serverSockets = serverSockets;
        correctOperation = false;
        this.db=um;
    }

    @Override
    public void run() {

        while (!correctOperation) {

            String textReaded = "";

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadReader.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                //COMPROBAR LEER REGISTRO O LOGIN
                textReaded = readLoginOption(client);

                if (textReaded.equals(LOGINOPTION)) {
                    loggin();

                } else if (textReaded.equals(REGISTEROPTION)) {
                    register();
                }

                write();
                addSocket();
                
            } catch (IOException ex) {
                Logger.getLogger(ThreadLogin.class.getName()).log(Level.SEVERE, null, ex);
                
                try {
                    client.close();
                } catch (IOException ex1) {
                    Logger.getLogger(ThreadLogin.class.getName()).log(Level.SEVERE, null, ex1);
                } finally {
                    correctOperation = true;
                }

            } catch (Exception ex) {
                Logger.getLogger(ThreadLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * method that read a socket to get loggin or register option
     * @param client socket i will read
     * @return the option that has been readed
     * @throws IOException cant read the socket
     */
    public String readLoginOption(Socket client) throws IOException {
        InputStream input;
        String text = "";
        try {
            if(!client.isInputShutdown()){
                input = client.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                text = reader.readLine();
                if(text==null)text="";
            }
        } catch (IOException ex) {
            throw new IOException("Imput readLoginOption socket Thread Loging IO Exception");
        }
        
        return text;
    }

    /**
     * method that read user and pasword form the socket and try login in the db
     * @throws IOException cant read the socket
     */
    private void loggin() throws IOException, Exception {
        InputStream input;
        String text = "";

        try {
            input = client.getInputStream();
        } catch (IOException ex) {
            throw new IOException("Imput loggin socket Thread Loging IO Exception");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        text = reader.readLine();

        String[] words = text.split("\\Q" + SEPARATOR);
        Logger.getLogger(ThreadLogin.class.getName()).info(words[0]+" - "+words[1]);
        if (words.length == 2) {
            correctOperation = db.login(words[0],words[1]);
            if(correctOperation) logedUser=db.getUser(words[0]);
        }
        Logger.getLogger(ThreadLogin.class.getName()).info(logedUser.getNickname());
    }

    /**
     * method that read user and pasword form the socket and try register in the db
     * @throws IOException cant read the socket
     */
    private void register() throws IOException, Exception {
        InputStream input;
        String text = "";

        try {
            input = client.getInputStream();
        } catch (IOException ex) {
            throw new IOException("Imput loggin socket Thread Loging IO Exception");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        text = reader.readLine();

        String[] words = text.split("\\Q" + SEPARATOR);

        if (words.length == 2) {
            correctOperation = db.register(words[0],words[1]);
            if(correctOperation) logedUser=db.getUser(words[0]);
        }
    }

    /**
     * Send to the socket how was the operation of register/login
     * @throws IOException cant write in the socket
     */
    private void write() throws IOException {

        OutputStream output;
        try {
            output = client.getOutputStream();
        } catch (IOException ex) {
            throw new IOException("Output write socket Thread Loging IO Exception");
        }
        PrintWriter writer = new PrintWriter(output, true);

        writer.println(correctOperation);
        
    }

    /**
     * if the user is ok, we send it to the chat
     */
    private void addSocket() {
        if(correctOperation){
            serverSockets.add(new UserSocket(logedUser, client));
        }
    }

}
