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
 *
 * @author ramaldon
 */
public class UserDB {

    private static final String DB_FILEPATH = "./db/user.csv";
    private HashSet<User> users = new HashSet<>();

    public UserDB() {
        try {
            loadDB();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashSet<User> getUsers() {
        return users;
    }

    public boolean loadDB() throws FileNotFoundException, IOException {
        File csvFile = new File(UserDB.DB_FILEPATH);
        System.out.println(csvFile.getAbsolutePath());
        if (!csvFile.exists()) {
            return false;
        }

        try ( BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] taskString = line.split(";");

                User user = new User(
                        taskString[0],//nickname
                        taskString[1]//password
                );

                this.users.add(user);
            }
        }

        return true;
    }

    public boolean writeDB() throws IOException {
        String output = this.users.stream()
                .map(user -> new String(
                user.getNickname() + "|" + user.getPassword()
        )).collect(Collectors.joining("\n"));

        File csvFile = new File(UserDB.DB_FILEPATH);

        if (!csvFile.exists()) {
            csvFile.getParentFile().mkdirs();
            csvFile.createNewFile();
        }

        try ( PrintWriter pw = new PrintWriter(csvFile)) {
            pw.println(output);
        }

        return csvFile.exists();
    }

    public boolean addUser(User user) {
        if (exists(user.getNickname())) {
            return false;
        }

        return this.users.add(user);
    }

    public boolean exists(String nickname) {
        return this.users.stream()
                .filter(u -> u.getNickname().equals(nickname))
                .findFirst()
                .isPresent();
    }

    public User login(String nickname, String password) throws LoginException {
        Optional<User> user = this.users.stream()
                .filter(u -> u.getNickname().equals(nickname) && u.getPassword().equals(password))
                .findFirst();

        if (!user.isPresent()) {
            throw new LoginException("Credenciales inválidos");
        }

        return user.get();
    }

}
