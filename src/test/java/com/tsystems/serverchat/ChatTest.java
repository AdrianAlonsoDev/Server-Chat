/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.tsystems.serverchat;

import com.tsystems.serverchat.models.Chat;
import com.tsystems.serverchat.models.Message;
import com.tsystems.serverchat.models.UserSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.annotation.processing.Messager;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        Socket socketMock = Mockito.mock(Socket.class);
        UserSocket usMock= Mockito.mock(UserSocket.class);
        
        
        ArrayList<UserSocket> socketTestList= new ArrayList<>();
        socketTestList.add(usMock);
        when(usMock.getSocket()).thenReturn(socketMockOK);
        
        
        OutputStream imput = Mockito.mock(OutputStream.class);
        when(socketMockOK.getOutputStream()).thenReturn(imput);
        
        String textString = "Text for example";
        Message message = new Message(textString,socketMock);
        Chat instance = new Chat("test",socketTestList);
        instance.addText(message);
        
        
        verify(socketMockOK.getOutputStream(), times(0));

    }

}
