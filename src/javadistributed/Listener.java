package javadistributed;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Listener implements Runnable {

    private Process thisProcess;
    private ServerSocket serverSocket;
    private ArrayList<ConnectionHandler> connections;

    Listener(Process thisProcess) {

        this.thisProcess = thisProcess;
        /*try {
            serverSocket = new ServerSocket(thisProcess.getPort(), 20, InetAddress.getByName(thisProcess.getIp()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }*/

    }

    public void run() {
        connections = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(thisProcess.getPort(), 20, InetAddress.getByName(thisProcess.getIp()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.println("Created a thread and am waiting for connections");

            Socket clientSocket = null;

            try {
                clientSocket = serverSocket.accept(); // actually listening for a new connection
                System.out.println("Made a connection");
            } catch (IOException e) {
                System.out.println("IO Exception while creating socket");
            }

            ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket,clientSocket.getPort());
            System.out.println("Connection was made to: " + clientSocket.getPort());
            new Thread(connectionHandler).start();
            connections.add(connectionHandler);
        }
    }
    public ArrayList<ConnectionHandler> getConnections() {
        return connections;
    }
}
