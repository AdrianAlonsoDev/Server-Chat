/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.tsystems.serverchat;

import com.tsystems.serverchat.models.Chat;
import com.tsystems.serverchat.models.Message;
import java.net.Socket;
import java.util.ArrayList;
import javax.annotation.processing.Messager;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

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
        Socket socketMockOK = Mockito.mock(Socket.class);
        ArrayList<Socket> socketTestList= new ArrayList<>();
        socketTestList.add(socketMockOK);
        String textString = "Text for example";
        Message message = new Message(textString,socketMockOK);
        Chat instance = new Chat("test",socketTestList);
        instance.addText(message);
        // TODO review the generated test code and remove the default call to fail.

    }

}
