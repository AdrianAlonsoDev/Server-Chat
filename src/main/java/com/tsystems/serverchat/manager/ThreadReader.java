package com.tsystems.serverchat.manager;

import static com.tsystems.serverchat.ConnectionDetails.*;
import com.tsystems.serverchat.models.Chat;
import com.tsystems.serverchat.models.Message;
import com.tsystems.serverchat.models.UserSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads a socket which is a client with a message and sends it to ThreadWriter
 * so it can be sent to a chat.
 *
 * @author dpadilla
 */
public class ThreadReader implements Runnable {

    private ArrayList<UserSocket> clientSock;
    private ArrayList<Message> unProcessText;
    private ArrayList<Chat> activeChats;

    private ReentrantLock lock;
    private BanManager banManager;

    /**
     * ThreadReader constructor to read all the sockets.
     *
     * @param clientSock Socket that is linked with the user.
     * @param _unProcessText Will produce a Message object that contains all the
     * necessary information.
     * @param lock Lock that will prevent Concurrent modifications
     * @param activeChats List of the active chats in the server.
     */
    public ThreadReader(ArrayList<UserSocket> clientSock, ArrayList<Message> _unProcessText,
            ReentrantLock lock, ArrayList<Chat> activeChats, BanManager banManager) {
        this.unProcessText = _unProcessText;
        this.clientSock = clientSock;
        this.activeChats = activeChats;
        this.lock = lock;
        this.banManager = banManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadReader.class.getName()).log(Level.SEVERE, null, ex);
            }

            lock.lock();
            try {
                for (int i = 0; i < clientSock.size(); i++) {
                    try {
                        if (clientSock.get(i).getSocket().isConnected()) {
                            read(clientSock.get(i).getSocket());
                        } else {
                            clearSocket(clientSock.get(i).getSocket());
                            //true
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * Read from the socket the mensaje
     *
     * @param client socket to be readed
     * @return text that has been readed from the socket
     * @throws IOException the socket can not be readed
     */
    public void read(Socket client) throws IOException {
        InputStream input;
        String text = "";
        String command = "";
        String censure="";
        boolean badText = false;
        try {
            input = client.getInputStream();
        } catch (IOException ex) {
            throw new IOException("Imput read socket IO Exception");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        while (reader.ready()) {
            text += reader.readLine();
        }

        if (text.contains(COMMANDCHANGECHAT)) {
            command = text.split(" ")[1];
            censure=banManager.checkMessage(command);
            badText = command.equals(censure);

            Chat toLeave = null;
            UserSocket currentUser = null;

            for (UserSocket userSocket : clientSock) {
                if (userSocket.getSocket().equals(client)) {
                    toLeave = userSocket.getChat();
                    currentUser = userSocket;
                }
            }

            if (badText) {
                banManager.addWarning(currentUser.getUser());
                if (banManager.youBanForever(currentUser.getUser())) {
                    currentUser.getSocket().close();
                    clientSock.remove(currentUser);
                }
            } else {
                Chat toChange = searchChat(censure);
                toLeave.removeUser(currentUser);
                toChange.addUser(currentUser);
                currentUser.setChat(toChange);
            }

        } else if (!text.equals("")) {

            UserSocket currentUser = null;
            censure = banManager.checkMessage(command);
            badText = command.equals(censure);
            
            for (UserSocket userSocket : clientSock) {
                if (userSocket.getSocket().equals(client)) {
                    currentUser = userSocket;
                }
            }

            if (badText) {
                banManager.addWarning(currentUser.getUser());

            }
            unProcessText.add(new Message(censure, client, currentUser.getUser(), currentUser.getChat()));

            if (banManager.youBanForever(currentUser.getUser())) {
                currentUser.getSocket().close();
                clientSock.remove(currentUser);
            }
        }
    }

    /**
     * This method safely deletes a socket.
     *
     * @param client socket to be deleted
     * @throws IOException if the socket is already close or disconnected
     */
    public void clearSocket(Socket client) throws IOException {
        if (client.isConnected()) {
            client.close();
            if (client.isClosed()) {
                clientSock.remove(client);
                if (clientSock.contains(client)) {
                    throw new IOException("ThreadReader clearSocket IO Exception Remove");
                }
            } else {
                throw new IOException("ThreadReader clearSocket IO Exception Close");
            }
        } else {
            throw new IOException("ThreadReader clearSocket IO Exception Connection");
        }
    }

    /**
     * Searches a chat with the specified name
     *
     * @param command Chat name
     * @param client Client that will join the specified chat
     * @return returns the chat created.
     */
    private Chat searchChat(String command) {
        Chat toAdd = null;
        for (Chat activeChat : activeChats) {
            if (activeChat.getNameChat().equals(command)) {
                toAdd = activeChat;
            }
        }

        if (toAdd == null) {
            toAdd = new Chat(command, new ArrayList<>());
            activeChats.add(toAdd);

        }

        return toAdd;
    }

}
