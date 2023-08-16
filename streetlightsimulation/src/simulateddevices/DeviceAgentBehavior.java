/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulateddevices;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import traffic.scenario.model.World;

/**
 *
 * @author Nathalia
 */
public class DeviceAgentBehavior extends CyclicBehaviour{

    //ServerTCP server = new ServerTCP(1099);
    String adressManagerAgent = "MANAGER";
    int numMsg = 0;
    DeviceAgent da;
    World traffic = World.getInstance();
    public DeviceAgentBehavior(Agent a){
        super(a);
        da = (DeviceAgent) a;
    }
    
    @Override
    public void action() {
     //   System.out.println("simulating device");
        //First Step:
        //To connect on the system (an adaptive agent is created for this
        //device
        //The device sends a message for ManagerAgent with its name and controllers type (previous
        //registered on FIoT system
        //The device waits for a signal from ManagerAgent with the adress/name
        //of its respective adaptive agent
        
        String messageFromManagerAgent = null;
//        String msgForManager = this.da.device.getName();
        String msgForManager = "";
        msgForManager+= this.da.device.getNameController();
       // msgForManager+=("-"+this.da.device.getSensorNameList());
        //msgForManager+=("-"+this.da.device.getActuatorNameList());
       // System.out.println("Message for Manager: "+ msgForManager);
        try {
            this.da.sentMsgToSelectRecipient(adressManagerAgent, msgForManager);
        } catch (FIPAException ex) {
            Logger.getLogger(DeviceAgentBehavior.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(messageFromManagerAgent == null){
                
                ACLMessage acmsg = this.da.receiveMessage();
                if(acmsg!=null){
                messageFromManagerAgent = acmsg.getContent();
                  //  System.out.println("Message from Manager: "+ messageFromManagerAgent);
                }
        }        
        
        //System.out.println("Message from Manager for "+ this.da.device.getName()+ ":  "+ messageFromManagerAgent);

        while(true){
        /*Second step:
            The device sends a message for its adaptive agent
        with the data read from its sensors and wait for a message
        containing the data for actuator or just "OK" (when the device doesn't have
        any actuator
        The adaptive agent can send a message containing "WAIT" if the
        adaptive process is on*/
        
        String messageFromAdaptiveAgent = "";
        String msgForAdaptiveAgent = "";
   
        while(!messageFromAdaptiveAgent.contains("desconnect") && !this.traffic.isFinishSimulation()){
            if(traffic.isReadOutput()){
            try {
                msgForAdaptiveAgent = this.da.device.readValuesFromInputSensors();
          //      System.out.println("Message for Adaptive Agent from "+ this.da.device.getName()+ ":  "+ msgForAdaptiveAgent);
                this.da.sentMsgToSelectRecipient(messageFromManagerAgent, msgForAdaptiveAgent);
            } catch (FIPAException ex) {
                Logger.getLogger(DeviceAgentBehavior.class.getName()).log(Level.SEVERE, null, ex);
            }
            messageFromAdaptiveAgent = "";
            while(messageFromAdaptiveAgent == null ||
                    messageFromAdaptiveAgent.isEmpty() ||
                    messageFromAdaptiveAgent.contains("WAIT")){
                ACLMessage acmsg = this.da.receiveMessage();
                if(acmsg!=null){
                    messageFromAdaptiveAgent = acmsg.getContent();
                }
            }
            if(!messageFromAdaptiveAgent.contains("desconnect")||
                    !messageFromAdaptiveAgent.contains("OK")){
                if(this.da.device.getActuatorName().length>=1){
                    String[] value;
                    value = new String[this.da.device.getActuatorName().length];
                    value = messageFromAdaptiveAgent.split(";");
                    this.da.device.setActuatorValue(value);
                    this.da.device.processActuatorInformation();
                }
              //  System.out.println("Message from Adaptive Agent for "+ this.da.device.getName()+ ":  "+ messageFromAdaptiveAgent);
            }
            this.traffic.incrementNumReadingOutput();
        }
            
        }
        }
    }
    
    public void read(){
        
      while(this.numMsg < 1){
             ACLMessage msg = this.myAgent.receive();
             this.numMsg++;
             if (msg != null) {
             
             AID sender = msg.getSender();
                // System.out.println("Sender local name "+ sender.getLocalName());
             //insertVector(sender.getLocalName(), msg.getContent());
             }
             }
           
    }
    
}
