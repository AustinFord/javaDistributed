/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javadistributed;

/**
 *
 * @author dsoresc
 */
public class Process {
    
    private final int pid;
    private final String ip;
    private final int port;
    
    public Process(int pid, String ip, int port) {
        this.pid = pid;
        this.ip = ip;
        this.port = port;
    }

    public int getPid() {
        return pid;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
    
    public String toString() {
    return "I am: " + pid + " My address is: " + ip + " I am listening on port: " + port;  
    }
}
