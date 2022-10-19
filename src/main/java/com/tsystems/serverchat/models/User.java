/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tsystems.serverchat.models;

import java.io.Serializable;

/**
 *
 * @author aalonsoa
 */
public class User implements Serializable {

    private String nickname;
    private String password;

    public User(String nickname, String password)
    {
        this.nickname = nickname;
        this.password = password;
    }

    public User(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
