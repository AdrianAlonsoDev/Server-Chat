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
 *
 * @author ramaldon
 */
public class BanManager {
    
    private static final String DB_FILEPATH = "./badword.txt";
    private HashSet<String> badword = new HashSet<>();
    
    public BanManager() {
        try {
            loadBadWord();
        } catch (IOException ex) {
            Logger.getLogger(BanManager.class.getName()).info(ex.getMessage());
        }
    }
    
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
    
    private String replaceChars(String cad) {
        String msg = cad.replace('.', ' ');
        msg = msg.replace(',', ' ');
        msg = msg.replace(';', ' ');
        msg = msg.replace(':', ' ');
        
        return msg;
    }
    
    public boolean checkMessage(String msg) {
        String remplaced = replaceChars(msg);
        boolean found = false;
        String[] myArray = remplaced.split(" ");
        for (int i = 0; i < myArray.length; i++) {
            for (String bw : badword) {
                if (myArray[i].contains(bw)) {
                    msg.replace(myArray[i], "****");
                    found = true;
                }
            }
            
        }
        return found;
    }
    
    public void adminWarning() {
        
    }
    
}
