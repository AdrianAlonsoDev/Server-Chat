/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.manager;

import com.tsystems.serverchat.models.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *  Class that cointains a HashSet of bad words and censure them to the users. 
 * 
 * @author ramaldon && rruizfer
 */
public class BanManager {
    
    private static final String DB_FILEPATH = "./badword.txt";
    private HashSet<String> badword;
    private UserManager userdb;


    public BanManager(UserManager userdb) {
        this.userdb= userdb;
        badword = new HashSet<>();
        try {
            loadBadWord();
        } catch (IOException ex) {
            Logger.getLogger(BanManager.class.getName()).info(ex.getMessage());
        }
    }
    
    /**
     * Method that checks that the file exists and add the words to a HashSet
     * 
     * @return if the file exists or not
     * @throws FileNotFoundException if the file doesn't exists
     * @throws IOException if the words can't be added
     */
    private boolean loadBadWord() throws FileNotFoundException, IOException {
        File textFile = new File(BanManager.DB_FILEPATH);

        if (!textFile.exists()) {
            return false;
        }
        
        try ( BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String taskString = line;
                this.badword.add(taskString);
            }
        }
        return true;

    }

    public void addWarning(User user) throws IOException {
        user.setWarning(user.getWarning()+1);  
        userdb.writeBan();
    }

    /**
     * Method that cheks if the user is banned
     * @param user
     * @return if is banned or not
     */
    public boolean youBanForever(User user) {
        if (user.getWarning() > 2) {
            return true;
        }
        return false;
    }

    /**
     * Replaces ( , . ; : ) with a empty space
     * 
     * @param cad The send message 
     * @return returns the message without puntuation signs
     */
    private String replaceChars(String cad) {
        String msg = cad.replace('.', ' ');
        msg = msg.replace(',', ' ');
        msg = msg.replace(';', ' ');
        msg = msg.replace(':', ' ');
        
        return msg;
    }
    
    /**
     * Method that check messages, and censure bad words
     * 
     * @param msg
     * @return if the message contains bad words or not
     */
    public String checkMessage(String msg) {
        
        String remplaced = replaceChars(msg);
        remplaced.toLowerCase();
        String[] myArray = remplaced.split(" ");
        
        for (int i = 0; i < myArray.length; i++) {
            for (String bw : badword) {
                if (myArray[i].contains(bw)) {
                    //msg=msg.replaceAll("\\Q"+myArray[i], "****");
                    int ini=remplaced.indexOf(myArray[i]);
                    String toChange=msg.substring(ini, ini+myArray[i].length());
                    msg=msg.replaceAll("\\Q"+toChange, "****");
                }
            }
    
        }
        return msg;
    }
    
    public void adminWarning() {
        
    }
}
