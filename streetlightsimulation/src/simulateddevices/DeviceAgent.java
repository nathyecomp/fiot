/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulateddevices;


import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 * Responsible to verify open IPs and create new Agents
 *
 * @author Nathalia
 */
public class DeviceAgent extends AdaptationAgent {

    World world;
    Device device;
    public DeviceAgent(Device device, String name, String container){
            super.setNameAgent(name);
            super.setNameContainer(container);
            this.device = device;
            world= World.getInstance();
            if(world.getElement(name)==null)
            world.addElement(name, device);
    }
    protected void setup() {

        super.setup();
        System.out.println("Agent started: " + this.getAID());
        
        sendLog(LogDeviceAgent.Action.connect, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.initAgent,"33", LogDeviceAgent.Resource.streetlight, "");
        
        // this.controlLoop = buildControlLoop();
//        addBehaviour(new DeviceAgentBehavior(this));
        addBehaviour(new DeviceAgentBehaviorOnlyOne(this));
    }

        public ACLMessage receiveMessage() {
        ACLMessage msg = receive();
        return msg;
    } 
    
    public String[] getSenderIP(ACLMessage msg){
        return msg.getSender().getAddressesArray();
    }

    @Override
    public String getNameClass() {
            //return this.getClass().getSimpleName();     
            return super.getNameAgent();
    }

    @Override
    public boolean getTestMode() {
        return false;
    }
}
