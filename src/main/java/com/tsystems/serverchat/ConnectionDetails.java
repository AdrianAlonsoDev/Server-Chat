package com.tsystems.serverchat;

import com.tsystems.serverchat.models.User;

/**
 * Contains all the details which the server will run in.
 *
 * @author dpadilla
 */
public class ConnectionDetails {

    public static int PORT = 2525;
    public static String IP = "192.168.3.215";
    /**
     * Character that will define the login option.
     */
    public static String LOGINOPTION = "L";
    /**
     * "" "" will define the register option
     */
    public static String REGISTEROPTION = "R";
    /**
     * Character that seprates user |(from) password
     */
    public static String SEPARATOR = "|";
    /**
     * Command that will be used to change chat rooms.
     */
    public static String COMMANDCHANGECHAT = "/join ";
    /**
     * Empty user so the All chat is never removed.
     */
    public static User SYSTEMUSER = new User("");

}
