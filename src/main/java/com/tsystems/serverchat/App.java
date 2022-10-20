package com.tsystems.serverchat;

import com.tsystems.serverchat.manager.ServerChat;
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
