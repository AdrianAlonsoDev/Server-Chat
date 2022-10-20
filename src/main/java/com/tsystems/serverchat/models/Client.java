package com.tsystems.serverchat.models;

import static com.tsystems.serverchat.ConnectionDetails.IP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author aalonsoa
 */
public class Client {

    public static void main(String[] args)
    {
        run();
    }

    public static void run()
    {

        int port = 2525;

        try ( Socket socket = new Socket(IP, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            Scanner in = new Scanner(System.in);
            String message;
            //do {

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            do {
                System.out.println("Enter text: ");
                message = in.nextLine();

                writer.println("message");
                writer.println("test|pasweord");

            } while (!message.equals("bye"));

            socket.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
