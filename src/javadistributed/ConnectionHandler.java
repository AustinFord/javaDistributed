package javadistributed;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionHandler implements Runnable {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private int currentSocketsPort;

    public ConnectionHandler(Socket socket, int currentSocketsPort) {
        this.socket = socket;
        this.currentSocketsPort = currentSocketsPort;

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter((new OutputStreamWriter(socket.getOutputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
     while(true) {
         try {
             System.out.println("Waiting to read now");
             if(input.readLine()!= null) {
                 System.out.println("MESSAGE FROM: " + currentSocketsPort);
                 System.out.println(input.readLine());
             } else {
                 System.out.println("was not able to read");
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    }

    void sendMessage(String message) {
        System.out.println("Attempting to send a message to: " + currentSocketsPort);
        output.println(message);
        output.flush();
    }

    void close() throws IOException {
        socket.close();
    }
}
