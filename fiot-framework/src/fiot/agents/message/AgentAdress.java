/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents.message;

/**
 *Adress representations for FIoT agents
 * @version 1.0
 * @author Nathalia
 */
public class AgentAdress {
    String container;
    String name;
    String ip;
    String port;

    //unique identify of adress
    String id;
    
    /**
     * Agent adress constructor
     * @param container
     * @param name
     * @param ip
     * @param port 
     */
    public AgentAdress(String container, String name, String ip, String port){
        this.container = container;
        this.name = name;
        this.ip = ip;
        this.port = port;
        
        if(this.port.isEmpty()){
            this.id = ip;
        }
        else{
            this.id = ip+"-"+port;
        }
    }
    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
