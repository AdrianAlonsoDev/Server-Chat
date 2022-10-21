package com.tsystems.serverchat.manager;

import com.tsystems.serverchat.models.User;
import java.io.IOException;
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

    public UserManager(UserDB newUserDB)
    {
        this.userdb = newUserDB;
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
        return userdb.login(nickname, password);
    }

    /**
     * Registers an user if it does not exists
     *
     * @param nickname User choosen unique nickname
     * @param password User selected password
     * @return if user was correctly registered
     * @throws java.io.IOException
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
            
            return user=userdb.getUser(user);
        }

        throw new LoginException("User is null");

    }
    
    
    public void writeBan() throws IOException {
        userdb.writeDB();

    }

}
