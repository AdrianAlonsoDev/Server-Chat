/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.tsystems.serverchat.manager;

import com.tsystems.serverchat.models.User;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rruizfer
 */
public class UserManagerTest {
    
    public UserManagerTest() {
    }

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

    /**
     * Test of register method, of class UserManager.
     */
    @Test
    public void testRegisterExists() throws Exception {
        UserDB userDB= Mockito.mock(UserDB.class);
        UserManager manager = new UserManager(userDB);
        
        when(userDB.exists("ruben")).thenReturn(true);

        boolean isRegistred = manager.register("ruben", "password");
        assertFalse(isRegistred);
    }

    @Test
    public void testRegisterNotExists() throws Exception {
        UserDB userDB= Mockito.mock(UserDB.class);
        UserManager manager = new UserManager(userDB);

        when(userDB.exists("ruben")).thenReturn(false);

        when(userDB.addUser( any())).thenReturn(true);
        
        boolean isRegistred = manager.register("ruben", "password");
        assertTrue(isRegistred);
    }

    
    
    /**
     * Test of getUser method, of class UserManager.
     */
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
