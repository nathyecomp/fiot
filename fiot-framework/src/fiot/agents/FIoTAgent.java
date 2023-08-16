/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents;

//import agents.SocketProxyAgent.SocketProxyAgent;

import br.pucrio.les.mas.publisher.framework.TestableAgent;
import fiot.agents.controller.ControllerList;
import fiot.agents.message.AgentAdress;
import fiot.agents.message.FIoTMsgController;


/**
 *
 * @author Nathalia
 */

public abstract class FIoTAgent extends TestableAgent {
  //    @Override
    public boolean getTestMode() {
        return false;
    }
    
    ControllerList listControl;
    AgentList listAgents;
    //to communicate with sockets
//    String ip;
//    String port;
    FIoTMsgController msgController;
    AgentAdress adress;
    //params: the agent will use acl or socket to communicate?
    public FIoTAgent(String typeControllerMsg, AgentAdress adress ){
        listControl = ControllerList.getInstance();
        listAgents = AgentList.getInstance();
        this.adress = adress;
        this.msgController = FIoTMsgController.getMsgControllerInstance(this, typeControllerMsg);
        
    }

//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public String getPort() {
//        return port;
//    }
//
//    public void setPort(String port) {
//        this.port = port;
//    }

    public ControllerList getListControl() {
        return listControl;
    }

    public void setListControl(ControllerList listControl) {
        this.listControl = listControl;
    }

    public AgentList getListAgents() {
        return listAgents;
    }

    public void setListAgents(AgentList listAgents) {
        this.listAgents = listAgents;
    }

    public FIoTMsgController getMsgController() {
        return msgController;
    }

    public void setMsgController(FIoTMsgController msgController) {
        this.msgController = msgController;
    }

    public AgentAdress getAdress() {
        return adress;
    }

    public void setAdress(AgentAdress adress) {
        this.adress = adress;
    }
    
    
}
