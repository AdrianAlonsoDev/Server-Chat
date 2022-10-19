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
import java.util.Optional;
import java.util.stream.Collectors;
import javax.security.auth.login.LoginException;

/**
 * Saves the registered users.
 *
 * @author ramaldon
 */
public class UserDB {

    private static final String DB_FILEPATH = "./db/user.txt";
    private HashSet<User> users = new HashSet<>();

    /**
     * Constructor of the class.
     *
     * @see loadDB
     */
    public UserDB() {
        try {
            loadDB();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the users 
     */
    public HashSet<User> getUsers() {
        return users;
    }
    /**
     * This method checks if the file with all the users exists and adds all to the HashSet.  
     * 
     * @return boolean
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private boolean loadDB() throws FileNotFoundException, IOException {
        File textFile = new File(UserDB.DB_FILEPATH);

        if (!textFile.exists()) {
            return false;
        }

        try ( BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] taskString = line.split("\\Q|");

                User user = new User(
                        taskString[0],//nickname
                        taskString[1]//password
                );

                this.users.add(user);
            }
        }

        return true;
    }

    /**
     * Add a new user on the file. 
     * 
     * @return boolean
     * @throws IOException
     */
    public boolean writeDB() throws IOException {
        String output = this.users.stream()
                .map(user -> new String(
                user.getNickname() + "|" + user.getPassword()
        )).collect(Collectors.joining("\n"));

        File textFile = new File(UserDB.DB_FILEPATH);

        if (!textFile.exists()) {
            textFile.getParentFile().mkdirs();
            textFile.createNewFile();
        }

        try ( PrintWriter pw = new PrintWriter(textFile)) {
            pw.println(output);
            pw.close();
        }
        return textFile.exists();

        
    }

    /**
     * Checks if a user is already registered and if it is not adds the user to the file. 
     * 
     * @param user
     * @return boolan if the user has been aded or already exists. 
     * @throws IOException
     */
    public boolean addUser(User user) throws IOException {
        if (!exists(user.getNickname())) {
            this.users.add(user);
            if (writeDB()) {
                System.out.println("You are the machine! New user add DB.");
            }
            return true;
        }

        return false;
    }

    /**
     *
     * @param nickname
     * @return if the user exists
     */
    public boolean exists(String nickname) {
//        return this.users.stream()
//                .filter(u -> u.getNickname().equals(nickname))
//                .findFirst()
//                .isPresent();
          return users.contains(new User(nickname,""));
    }

//    /**
//     * Checks the user exists and logged it 
//     * 
//     * @param nickname
//     * @param password
//     * @return if the user has succesfully loggin
//     * @throws LoginException
//     */
//    public User login(String nickname, String password) throws LoginException {
//        Optional<User> user = this.users.stream()
//                .filter(u -> u.getNickname().equals(nickname) && u.getPassword().equals(password))
//                .findFirst();
//
//        if (!user.isPresent()) {
//            throw new LoginException("Credenciales inválidos");
//        }
//
//        return user.get();
//    }

    public boolean login(String nickname, String password) throws LoginException {

        if (!users.contains(new User(nickname,password))) {
            return false;
        }

        return true;
    }

}
