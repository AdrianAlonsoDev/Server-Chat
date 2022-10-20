package com.tsystems.serverchat.manager;

import static com.tsystems.serverchat.ConnectionDetails.*;
import com.tsystems.serverchat.models.Chat;
import com.tsystems.serverchat.models.Message;
import com.tsystems.serverchat.models.UserSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Runs the server client and setups a Socker writer/reader that sends messages
 * to a Chat.
 *
 * @author aalonsoa
 */
public class ServerChat {

    private ServerSocket serverSocket;
    private Chat chatDefault;
    private UserManager userManager;
    private ReentrantLock lock;
    private ExecutorService executorService;

    private ArrayList<UserSocket> clientSock;
    private ArrayList<Message> unProcessText;
    private ArrayList<Chat> chatList;

    /**
     * Set ups and initializes the lists and runs the server.
     *
     * @throws IOException if there is an I/O error
     */
    public ServerChat() throws IOException
    {
        this.clientSock = new ArrayList<>();
        this.unProcessText = new ArrayList<>();
        this.chatList = new ArrayList<>();

        this.serverSocket = new ServerSocket(PORT);
        this.executorService = Executors.newFixedThreadPool(22);
        this.chatDefault = new Chat("All", clientSock);
        this.chatList.add(chatDefault);

        this.userManager = new UserManager();
        this.lock = new ReentrantLock();

    }

    /**
     * Runs the server that will listen for connections until it is shutdown.
     *
     * @throws IOException If there is no connection to the server.
     */
    public void run() throws IOException
    {

        startThreads();

        while (true) {

            InetAddress ia = InetAddress.getLocalHost();
            System.out.println("ip:" + ia);
            System.out.println("Server listening for a connection");
            Socket clientSocket = serverSocket.accept();

            ThreadLogin tl = new ThreadLogin(clientSocket, clientSock, userManager, chatDefault);
            executorService.execute(tl);
        }

    }

    private void startThreads()
    {
        ThreadReader tr = new ThreadReader(clientSock, unProcessText, lock, chatList);
        ThreadWriter tw = new ThreadWriter(unProcessText, chatDefault, lock);

        executorService.execute(tr);
        executorService.execute(tw);
    }

}
