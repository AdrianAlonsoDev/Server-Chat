/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
//Exampled https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
package com.tsystems.serverchat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aalonsoa
 */
public class App {

    public static void main(String[] args)
    {
        try {
            ServerChat sc = new ServerChat();
            sc.run();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
