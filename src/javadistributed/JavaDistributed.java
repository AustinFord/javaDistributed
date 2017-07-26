/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javadistributed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dsoresc
 */
public class JavaDistributed {

    //TODO change to a relative path
    public static File setup = new File("/Users/dsoresc/NetBeansProjects/javaDistributed/src/javadistributed/setup.txt");
    public static ArrayList<Process> processList = new ArrayList<Process>();
    public static Process thisProcess;

    public static void main(String[] args) {

        setupFile(Integer.parseInt(args[0]));

        System.out.println(thisProcess);
        
        for(Process p : processList) {
        System.out.println("I am " + p.getPid());
        System.out.println("I am located at: " + p.getIp());
        System.out.println("I am will be listening: " + p.getPort());
        System.out.println("-----------------------------------");
        }
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
}
