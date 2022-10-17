/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.tsystems.serverchat;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rruizfer
 */
public class ChatTest {
    
    public ChatTest() {
    }

    /**
     * Test of getNameChat method, of class Chat.
     */
    @org.junit.Test
    public void testGetNameChat() {
        System.out.println("getNameChat");
        Chat instance = new Chat();
        String expResult = "";
        String result = instance.getNameChat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of notificationChat method, of class Chat.
     */
    @org.junit.Test
    public void testNotificationChat() {
        System.out.println("notificationChat");
        Chat instance = new Chat();
        instance.notificationChat();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addText method, of class Chat.
     */
    @org.junit.Test
    public void testAddText() throws Exception {
        System.out.println("addText");
        String text = "";
        Chat instance = new Chat();
        instance.addText(text);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
