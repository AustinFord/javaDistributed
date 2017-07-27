/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javadistributed;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dsoresc
 */
public class JavaDistributed {

    //TODO change to a relative path
    public static File setup = new File("./javadistributed/setup.txt");
    public static ArrayList<Process> processList = new ArrayList<Process>();
    public static Process thisProcess;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        setupFile(Integer.parseInt(args[0])); // read in all the process data and map them to Process instances.

        System.out.println(thisProcess); // letting the user know which process is currently running in this window.
        

        /*for(Process p : processList) {
        System.out.println("I am " + p.getPid());
        System.out.println("I am located at: " + p.getIp());
        System.out.println("I am will be listening: " + p.getPort());
        System.out.println("-----------------------------------");
        }*/


        //create our listener to listen for new connections.
        Listener listener = new Listener(thisProcess);

        new Thread(listener).start();

        //try to connect to all of the other processes.
        setupConnections();

        /*while(true) {
            String message = "Hello from " + thisProcess;
            broadcast(message, listener);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }*/
    }

    public static void setupFile(int pid) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(setup));
            String fileLine;
            while ((fileLine = br.readLine()) != null) {
                String brokenLine[] = fileLine.split(" ");

                int pidToAdd = Integer.parseInt(brokenLine[0]);
                String ipToAdd = brokenLine[1];
                int portToAdd = Integer.parseInt(brokenLine[2]);

                if (pidToAdd == pid) {
                    thisProcess = new Process(pidToAdd, ipToAdd, portToAdd);
                } else {
                    processList.add(new Process(pidToAdd,ipToAdd, portToAdd));
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaDistributed.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavaDistributed.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void setupConnections() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < thisProcess.getPid() - 1; i++) {
            try {
                System.out.println("Trying to connect to: " + processList.get(i).toString());
                Socket s = new Socket(processList.get(i).getIp(), processList.get(i).getPort());
                System.out.println("Connected to: " + processList.get(i).toString());


            } catch (IOException e) {
                System.out.println("IO Exception while creating socket");
            }
        }
    }

    public static void broadcast(String message, Listener listener) {
        for(ConnectionHandler ch: listener.getConnections()) {
            ch.sendMessage(message);
        }
    }
}
