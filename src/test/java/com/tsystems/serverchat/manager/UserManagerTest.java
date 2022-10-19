/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.tsystems.serverchat.manager;

import com.tsystems.serverchat.models.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dpadilla
 */
public class UserManagerTest {
    
  

    /**
     * Test of login method, of class UserManager.
     */
    @Test
    public void testLogin() {
        
        System.out.println("loginTEst");
        String nickname = "test";
        String password = "test";
        UserManager instance = new UserManager();
        User user=null;
        boolean expResult = false;
        try {
            instance.register(nickname, password);
            instance.login(nickname, password);
            user=instance.getUser(nickname);
        } catch (IOException ex) {
            Logger.getLogger(UserManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(nickname, user.getNickname());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

//    /**
//     * Test of register method, of class UserManager.
//     */
//    @Test
//    public void testRegister() throws Exception {
//        System.out.println("register");
//        String nickname = "";
//        String password = "";
//        UserManager instance = new UserManager();
//        boolean expResult = false;
//        boolean result = instance.register(nickname, password);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUser method, of class UserManager.
//     */
//    @Test
//    public void testGetUser() throws Exception {
//        System.out.println("getUser");
//        String nickname = "";
//        UserManager instance = new UserManager();
//        User expResult = null;
//        User result = instance.getUser(nickname);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
