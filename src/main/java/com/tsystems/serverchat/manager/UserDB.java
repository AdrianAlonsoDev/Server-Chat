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
    public UserDB()
    {
        try {
            loadDB();
        } catch (IOException ex) {
            Logger.getLogger(UserDB.class.getName()).info(ex.getMessage());
        }
    }

    /**
     * @return the users
     */
    public HashSet<User> getUsers()
    {
        return users;
    }

    /**
     * This method checks if the file with all the users exists and adds all to
     * the HashSet.
     *
     * @return boolean
     * @throws FileNotFoundException
     * @throws IOException
     */
    private boolean loadDB() throws FileNotFoundException, IOException
    {
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
                        taskString[1],//password
                        Integer.parseInt(taskString[2]) //warning
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
    public boolean writeDB() throws IOException
    {
        String output = this.users.stream()
                .map(user -> new String(
                user.getNickname() + "|" + user.getPassword() + "|" + user.getWarning()
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
     * Checks if a user is already registered and if it is not adds the user to
     * the file.
     *
     * @param user
     * @return boolan if the user has been aded or already exists.
     * @throws IOException
     */
    public boolean addUser(User user) throws IOException
    {
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
     * Checks if a user exists
     *
     * @param nickname Nickname string of the user
     * @return if the user exists
     */
    public boolean exists(String nickname)
    {
        return users.contains(new User(nickname, ""));
    }

    /**
     * Logins the user
     *
     * @param nickname Nickname string of the user
     * @param password Password string of the user
     * @return true or false if user loggeed in
     */
    public boolean login(String nickname, String password) throws LoginException
    {
        if (!users.contains(new User(nickname, password))) {
            return false;
        }
        return true;
    }

}
