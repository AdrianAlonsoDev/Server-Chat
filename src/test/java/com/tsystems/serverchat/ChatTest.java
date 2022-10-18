/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.tsystems.serverchat;

import java.net.Socket;
import javax.annotation.processing.Messager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ramaldon
 */
public class ChatTest {
    
    public ChatTest() {
    }

    /**
     * Test of addText method, of class Chat.
     */
    @Test
    public void testAddText_String() throws Exception {
        // Create mock
        System.out.println("Test add Text example");
        Socket socket = new Socket();
        String text = "Text for example";
        Chat instance = new Chat();
        instance.addText(text);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
