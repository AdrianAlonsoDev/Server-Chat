/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.tsystems.serverchat.manager;

import com.tsystems.serverchat.manager.ThreadReader;
import com.tsystems.serverchat.models.Message;
import com.tsystems.serverchat.models.UserSocket;
import java.lang.reflect.Array;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author dpadilla
 */
public class ThreadReaderTest {

    public void testClearSocket() throws Exception {

        Socket socketMockNotOK = Mockito.mock(Socket.class);
        Socket socketMockOK = Mockito.mock(Socket.class);

        ArrayList<UserSocket> socketList = new ArrayList<>();
        ArrayList<Message> msgList = new ArrayList<>();
        ReentrantLock lock = new ReentrantLock();

        ThreadReader tr = new ThreadReader(socketList, msgList, lock);

        when(socketMockOK.isConnected()).thenReturn(true);
        when(socketMockOK.isClosed()).thenReturn(false);

        when(socketMockNotOK.isConnected()).thenReturn(false);

        //then
        Throwable exception = assertThrows(IOException.class, () -> {
            tr.clearSocket(socketMockOK);
        });

        when(socketMockOK.isConnected()).thenReturn(true);
        assertEquals("ThreadReader clearSocket IO Exception Close", exception.getMessage());

        exception = assertThrows(IOException.class, () -> {
            tr.clearSocket(socketMockNotOK);
        });

        assertEquals("ThreadReader clearSocket IO Exception Connection", exception.getMessage());

    }

    /**
     * Test of clearSocket method, of class ThreadReader.
     */
    @Test
    public void testRead() throws Exception {
        Socket socketMockOK = Mockito.mock(Socket.class);
        InputStream imput = Mockito.mock(InputStream.class);
        ArrayList<UserSocket> socketList = new ArrayList<>();
        ArrayList<Message> msgList = new ArrayList<>();
        ReentrantLock lock = new ReentrantLock();

        ThreadReader tr = new ThreadReader(socketList, msgList, lock);
        when(socketMockOK.getInputStream()).thenReturn(imput);

        tr.read(socketMockOK);

        verify(socketMockOK.getInputStream(), times(1));

    }

}

