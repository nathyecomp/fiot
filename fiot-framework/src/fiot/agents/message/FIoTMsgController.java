/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents.message;

import fiot.agents.FIoTAgent;

/**
 *Abstract class to control FIoTMessage
 * the type of communication among devices and agents can be through ACL or socket
 * (or another one, if you prefer. You just have to extend this class)
 * @author Nathalia
 */
public abstract class FIoTMsgController {
//    FIoTAgent agent;
    public String type;
//    public FIoTMsgController (FIoTAgent agent, String type){
//        this.agent = agent;
//        
//    }
    /**
     * 
     * @return FIoTMessage readed
     */
    public abstract FIoTMessage readFIoTMessage();
    /**
     * Call this method to send FIoT message by ACL methods or Socket methods
     * @param msg
     * @return 
     */
    public abstract boolean sendFIoTMessage(FIoTMessage msg);
    public String getType(){
        return this.type;
    }
    public void setType(String typeName){
        this.type = typeName;
    }
    
//    public String getType(){
//        return this.type;
//    }
    /**
     * Method to create an instance for FIoTMsgController (if you add a new instance
     * for FIoTMsgController, you have to put this controller here
     * @param agent
     * @param typeName
     * @return a instance for FIoTMsgController based on type of Controller
     */
    public static FIoTMsgController getMsgControllerInstance(FIoTAgent agent,String typeName){
        
        if(typeName.equals("ACL"))
            return new FIoTMessageControllerACL(agent);
        else //if(type.equals("SOCKET"))
            return new FIoTMessageControllerSocket1(agent);
        
        
    }
   
}
