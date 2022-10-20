/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.manager;

import com.tsystems.serverchat.models.User;
import java.io.IOException;
import java.util.Optional;
import javax.security.auth.login.LoginException;

/**
 * Manages the registration or login of an user.
 *
 * @author aalonsoa
 */
public class UserManager {

    private UserDB userdb;

    public UserManager()
    {
        this.userdb = new UserDB();
    }

    /**
     * Logins an user if it exists
     *
     * @param nickname User choosen unique nickname
     * @param password User selected password
     * @return if user was logged
     */
    public boolean login(String nickname, String password) throws LoginException
    {
//        Optional<User> user = this.userdb.getUsers().stream()
//                .filter(u -> u.getNickname().equals(nickname) && u.getPassword().equals(password))
//                .findFirst();
//
//        return user.isPresent();
        return userdb.login(nickname, password);
    }

    /**
     * Registers an user if it does not exists
     *
     * @param nickname User choosen unique nickname
     * @param password User selected password
     * @return if user was correctly registered
     */
    public boolean register(String nickname, String password) throws IOException
    {
        if (!userdb.exists(nickname)) {
            User user = new User(nickname, password);
            return this.userdb.addUser(user);
        }
        return false;
    }

    /**
     *
     * @param nickname User unique nickname
     * @return Returns user object
     * @throws Exception If user does not exits
     */
    public User getUser(String nickname) throws Exception
    {
        User user = new User(nickname);
        if (this.userdb.exists(nickname)) {
            return user;
        }

        throw new LoginException("User is null");

    }


}
