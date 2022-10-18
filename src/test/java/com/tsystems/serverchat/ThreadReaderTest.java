/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.tsystems.serverchat;


import com.tsystems.serverchat.manager.ThreadReader;
import com.tsystems.serverchat.models.Message;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;


/**
 *
 * @author ramaldon
 */
public class ThreadReaderTest {

    public ThreadReaderTest() {
    }


    /**
     * Test of clearSocket method, of class ThreadReader.
     */
    @Test
    public void testClearSocket() throws Exception {
        
        Socket socketMockNotOK = Mockito.mock(Socket.class);
        Socket socketMockOK = Mockito.mock(Socket.class);
        ArrayList<Socket> socketList= new ArrayList<>();
        ArrayList<Message> msgList= new ArrayList<>();
        ReentrantLock lock= new ReentrantLock();
        
        ThreadReader tr= new ThreadReader(socketList, msgList, lock);
        
        
            
        
        when(socketMockOK.isConnected()).thenReturn(true);
    }

}
