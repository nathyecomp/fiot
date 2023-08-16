 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulateddevices;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import traffic.scenario.model.Node;
import traffic.scenario.model.StreetLight;
//import traffic.scenario.model.StreetLight;
import traffic.scenario.model.World;

/**
 *
 * @author Nathalia
 */
public class DeviceAgentBehaviorOnlyOne extends CyclicBehaviour {

    //ServerTCP server = new ServerTCP(1099);
    String adressManagerAgent = "MANAGER";
    int numMsg = 0;
    DeviceAgent da;
    World traffic = World.getInstance();
    int time = 0;

    public DeviceAgentBehaviorOnlyOne(Agent a) {
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

        Map<String,String> messageFromManagerAgent = new HashMap<>();
        List<Node> nodeList = new ArrayList<>();//new ArrayList<>(traffic.streetWorld.getLightList());
      //  for (Edge lane : laneList) {
           // String msgForManager = "Name:"+lane.getName()+";";
            String msgForManager = "";
            msgForManager += this.da.device.getNameController();
                     // String msgForManager = "";
            // msgForManager+=("-"+this.da.device.getSensorNameList());
            //msgForManager+=("-"+this.da.device.getActuatorNameList());
           //  System.out.println("Message for Manager: "+ msgForManager);
            try {
                this.da.sentMsgToSelectRecipient(adressManagerAgent, msgForManager);
                this.da.sendLog(LogDeviceAgent.Action.sendMsg, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgManagerAgent,"64", LogDeviceAgent.Resource.ManagerAgent, msgForManager);
      
            } catch (FIPAException ex) {
                Logger.getLogger(DeviceAgentBehavior.class.getName()).log(Level.SEVERE, null, ex);
            }
            ACLMessage acmsg = null;
            while (acmsg==null) {

                acmsg = this.da.receiveMessage();
                if (acmsg != null) {
                    String msg = acmsg.getContent();
//                    messageFromManagerAgent.put(lane.getName(), msg);
                     if (acmsg != null) {
                         msg = acmsg.getContent().replace("*", "");
                     }
                    messageFromManagerAgent.put("lights", msg);
                    this.da.sendLog(LogDeviceAgent.Action.receiveMsg, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgManagerAgent,"80", LogDeviceAgent.Resource.ManagerAgent, msg);
      
                //    System.out.println("Message from Manager: "+ msg);
                }
            }

        //}

        //System.out.println("Message from Manager for "+ this.da.device.getName()+ ":  "+ messageFromManagerAgent);
        while (true) {
            /*Second step:
             The device sends a message for its adaptive agent
             with the data read from its sensors and wait for a message
             containing the data for actuator or just "OK" (when the device doesn't have
             any actuator
             The adaptive agent can send a message containing "WAIT" if the
             adaptive process is on*/

            String messageFromAdaptiveAgent = "";
            String msgForAdaptiveAgent = "";

            while (!messageFromAdaptiveAgent.contains("desconnect")) {

                while (time < this.traffic.getTimeSimulation()) {

                    if (World.getInstance().getPanel().isExecute()) {
                        //  
                        this.traffic.setFinishSimulation(false);
                        this.traffic.setActualTimeSimulation(time);
                        
                        //only log
                        this.da.sendLog(LogDeviceAgent.Action.calculatePeople, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"110", LogDeviceAgent.Resource.person, "Start position changes");
                       
                        World.getInstance().getPanel().calculePeople();
                        
                        
                       this.da.sendLog(LogDeviceAgent.Action.calculatePeople, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"117", LogDeviceAgent.Resource.person, "Partial Concluded People "+ this.traffic.getNumOfPeopleCompletedAfterSimulation() );
                       this.da.sendLog(LogDeviceAgent.Action.calculateTimeTrip, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"118", LogDeviceAgent.Resource.person, "Partial Trip Time "+ this.traffic.getTotalTimeTrip() );
                       
      
      
                    //this.traffic.setFinishSimulation(false);
                        //boolean readd = World.getInstance().isReadOutput();
                        // boolean readd = this.traffic.isFinishSimulation();
                        //System.out.println("READD EH "+ readd);
                        // if (readd) {
                        // this.traffic.setFinishSimulation(true);
                        //  System.out.println("lane agent");
                        try {
                           // lightsList = new ArrayList<>(traffic.streetWorld.getLightList());
                           nodeList = new ArrayList<>(traffic.getAllNodeList());
                            for (Node node : nodeList) {
//                                DeviceAgent laneAg = new DeviceAgent(lane, "lanes", "laneContainer");
                                //StreetLight sl = traffic.streetWorld.getStreetLight(node.getName());
                                
                                StreetLight lNode = traffic.streetWorld.getStreetLight(node.getName());
                                msgForAdaptiveAgent = lNode.readValuesFromInputSensors();
                               // msgForAdaptiveAgent = traffic.streetWorld.readInputValue(node.getName());
                                
                               
                           //only log
                           //Se decidi por luz, e meu sensor nao detecta luz..entao..
//                           if(lNode.getDecidedLight()>0 && lNode.getLightSensor()==0){
//                               this.da.sendLog(node.getName(),LogDeviceAgent.Action.readLightSensor, LogDeviceAgent.TypeLog.ERROR,LogDeviceAgent.MethodName.readValuesFromInputSensors,"83", LogDeviceAgent.Resource.streetlight, " lightsensor is :"+lNode.getLightSensor() +" and my Lamp is: "+lNode.getDecidedLight());
//                           }
                          // else{
                           this.da.sendLog(LogDeviceAgent.Action.readLightSensor, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.readValuesFromInputSensors,"83", LogDeviceAgent.Resource.streetlight, " lightsensor is :"+lNode.getLightSensor());
                           //}
                           if(lNode.getLightSensor()>0){
                               this.da.sendLog(LogDeviceAgent.Action.detectLight, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.readValuesFromInputSensors,"83", LogDeviceAgent.Resource.streetlight, " lightsensor is :"+lNode.getLightSensor());
                           }
                           this.da.sendLog(LogDeviceAgent.Action.readMotionSensor, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.readValuesFromInputSensors,"84", LogDeviceAgent.Resource.streetlight, " motiosensor is :"+lNode.getMotionSensor());
                           this.da.sendLog(LogDeviceAgent.Action.readPreviousListeningDecision, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.readValuesFromInputSensors,"85", LogDeviceAgent.Resource.streetlight, " is listening:"+lNode.getPreviousListeningDecision());
                           //log for wirelessReceiver is in StreetWorld - because of the closest node info (line 145)
                           //     this.da.sendLog(LogDeviceAgent.Action.readInputValue, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"128", LogDeviceAgent.Resource.streetlight, 
                           //            "Node: "+node.getName()+ " PrevWt: "+lNode.getPreviousWirelessTransmitter()+ " PrevListen: "+lNode.getPreviousListeningDecision()+ " PrevLight: "+lNode.getPreviousLightDecision()+ " Light: "+lNode.getLightSensor() +" Motion: "+lNode.getMotionSensor()+  " Wireless: "+lNode.getWirelessReceiver());
                           this.da.sendLog(LogDeviceAgent.Action.readInputValue, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"128", LogDeviceAgent.Resource.streetlight,
                                       " PrevListen: "+lNode.getPreviousListeningDecision()+ " Light: "+lNode.getLightSensor() +" Motion: "+lNode.getMotionSensor()+  " Wireless: "+lNode.getWirelessReceiver());
                       
                           
        
                              //  DeviceAgent lightAg = new DeviceAgent(sl, "lights", "lightContainer");
                                //msgForAdaptiveAgent = lightAg.device.readValuesFromInputSensors();
                            //    System.out.println("MSg for Adaptive Agent");
                            //System.out.println(msgForAdaptiveAgent);
                                //     System.out.println("Message for Adaptive Agent from "+ laneAg.device.getName()+ ":  "+ msgForAdaptiveAgent);
                                // System.out.println("send message adaptive");
                                
                                //only log
                                this.da.sendLog(LogDeviceAgent.Action.sendMsg, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"64", LogDeviceAgent.Resource.ApdativeAgent, msgForAdaptiveAgent);
      
                                
                                //send de verdade pro agente
                                this.da.sentMsgToSelectRecipient(messageFromManagerAgent.get("lights"),
                                        msgForAdaptiveAgent);
                                messageFromAdaptiveAgent = "";
                            //    System.out.println("Waiting msg from Adaptive Agent");
                                while (messageFromAdaptiveAgent == null
                                        || messageFromAdaptiveAgent.isEmpty()
                                        || messageFromAdaptiveAgent.contains("WAIT")) {
                                    acmsg = this.da.receiveMessage();
                                    if (acmsg != null) {
                                        messageFromAdaptiveAgent = acmsg.getContent().replace("*", "");
                            //            System.out.println("Message from Adaptive Agent"+messageFromAdaptiveAgent);
//                                        System.out.println("Name agent: "+ acmsg.getSender());
//                                        System.out.println("Name lane: "+lane.getName());

                                        this.da.sendLog(LogDeviceAgent.Action.receiveMsg, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"80", LogDeviceAgent.Resource.ApdativeAgent, messageFromAdaptiveAgent);
      
                                     
                                    }
                                }
                                //System.out.println("message from adaptive");
                                if (!messageFromAdaptiveAgent.contains("desconnect")
                                        || !messageFromAdaptiveAgent.contains("OK")) {
                                    int numberOfActuators = traffic.streetWorld.getNumberOfActuators(node.getName());
                                    if (numberOfActuators >= 1) {
                                        String[] value;
                                        // value = new String[this.da.device.getActuatorName().length];
                                        value = messageFromAdaptiveAgent.split(";");
                                        //log
                                        this.da.sendLog(LogDeviceAgent.Action.receiveNeuralNetworkCommand, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.processActuatorInformation,"202", LogDeviceAgent.Resource.ApdativeAgent, "value are: Wi "+value[0]+" - Listen "+value[1]+ " - Light "+value[2]);
                                        
                                        traffic.streetWorld.setActuatorValue(node.getName(), value);
                                        
                                        
                                        //only log
                                        lNode = traffic.streetWorld.getStreetLight(node.getName());
                                        
                                        //SEND LOGS 
                                        //o valor que decidiu colocar na lampada, e nao o valor q colocou (no caso de estar quebrada)
                                        if(lNode.getDecidedLight()>0.0){
                                            this.da.sendLog(LogDeviceAgent.Action.switchLightON, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.processActuatorInformation,"136", LogDeviceAgent.Resource.streetlight, " light value to:"+lNode.getDecidedLight());
                                        }
                                        else{
                                          this.da.sendLog(LogDeviceAgent.Action.switchLightOFF, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.processActuatorInformation,"139", LogDeviceAgent.Resource.streetlight, " light value to:"+lNode.getDecidedLight());
                                        }
          
                                        this.da.sendLog(LogDeviceAgent.Action.sendWirelessData, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.processActuatorInformation,"142", LogDeviceAgent.Resource.streetlight, " send data:"+lNode.getWirelessTransmitter());
                                        this.da.sendLog(LogDeviceAgent.Action.setListeningDecision, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.processActuatorInformation,"143", LogDeviceAgent.Resource.streetlight, " listening decision is :"+lNode.getListeningDecision());
        
                                        this.da.sendLog(LogDeviceAgent.Action.setOutputValue, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"179", LogDeviceAgent.Resource.streetlight,
                                        " Transmitter: "+lNode.getWirelessTransmitter()+ " Listening: "+lNode.getListeningDecision() + " Light: "+lNode.getTransmittedLightDecision());
                       
                                     //   lightAg.device.setActuatorValue(value);
                                      //  lightAg.device.processActuatorInformation();
                                    }
                                    //        System.out.println("Message from Adaptive Agent for "+ laneAg.device.getName()+ ":  "+ messageFromAdaptiveAgent);
                                }
                                //this.traffic.incrementNumReadingOutput();
                            }

                        } catch (FIPAException ex) {
                            Logger.getLogger(DeviceAgentBehavior.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        //System.out.println("change semaphores");
                       this.da.sendLog(LogDeviceAgent.Action.changeEdgeLights, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"199", LogDeviceAgent.Resource.edge, "Change edge lights");
                       
                         
                        World.getInstance().getPanel().changeLight();
                        
                        this.da.sendLog(LogDeviceAgent.Action.calculateEnergy, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"204", LogDeviceAgent.Resource.streetlight, "Partial Energy "+ this.traffic.getTotalEnergy() );
                       
                        //ou seja, se pedir ppra desenhar, o usuário ganha controle da execução,
                        //tendo que clicar em next
                        if (World.getInstance().getPanel().isDraw()) {
                            World.getInstance().getPanel().repaint();
                            World.getInstance().getPanel().setExecute(false);
                        }
                        //tem que botar false p poder usar o botão next
                        else{
                            World.getInstance().getPanel().setExecute(true);
                        }
                        time++;
                    }
                }

                time = 0;
                this.traffic.setActualTimeSimulation(time);
                this.traffic.setFinishSimulation(true);
                
                this.da.sendLog(LogDeviceAgent.Action.finishSimulation, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgAdaptiveAgent,"231", LogDeviceAgent.Resource.simulation, "Sorted Broken Lamps "+ this.traffic.getBrokenLightsString());
                 
                //System.out.println("reiniting");
                World.getInstance().getPanel().reinit();
                     
                //System.out.println("calling observer ");
                this.observer();

            }
            // World.getInstance().setReadOutput(false);
        }
    }

    public void observer() {
        String messageFromObserverAgent = null;
//        String msgForManager = this.da.device.getName();
        String msgForObserver = "";
        String adressObserver = "OBSERVER";
//        while(!this.traffic.isFinishSimulation()){
//        
//        }
        //  if(this.traffic.isFinishSimulation()){

        msgForObserver += this.traffic.isFinishSimulation();
        msgForObserver += ";";
        msgForObserver += this.traffic.getTimeSimulation();
        msgForObserver += ";";
        //msgForObserver += this.traffic.getPercentConcludedPeople();
        msgForObserver += this.traffic.getNumOfPeopleCompletedAfterSimulation();
        msgForObserver += ";";
        msgForObserver += this.traffic.getTotalTimeTrip();
        msgForObserver += ";";
        msgForObserver += this.traffic.getTotalEnergy();
        msgForObserver += ";";
        msgForObserver += this.traffic.getNumPeople();
        msgForObserver += ";";
        msgForObserver += this.traffic.getNumRoads();
        //msgForObserver += this.traffic.getNumOfAmbulancesCompletedAfterSimulation();
        
            this.da.sendLog(LogDeviceAgent.Action.finishSimulation, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.calculateEnergy,"259", LogDeviceAgent.Resource.streetlight, "Total Energy "+ this.traffic.getTotalEnergy() );
            this.da.sendLog(LogDeviceAgent.Action.finishSimulation, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.calculePeople,"259", LogDeviceAgent.Resource.streetlight, "Total People "+ this.traffic.getNumOfPeopleCompletedAfterSimulation() );
            this.da.sendLog(LogDeviceAgent.Action.finishSimulation, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.calculateTimeTrip,"259", LogDeviceAgent.Resource.streetlight, "Total Trip "+ this.traffic.getTotalTimeTrip() );
            
     //   System.out.println("Time simulation: "+ this.traffic.getTimeSimulation());
     //   System.out.println("Total People: "+ this.traffic.getNumPeople());
     //   System.out.println("Total Street Lights: "+ this.traffic.getNumRoads());
     //   System.out.println("Num Completed People: "+ this.traffic.getNumOfPeopleCompletedAfterSimulation());
     //   System.out.println("Total time trip: "+ this.traffic.getTotalTimeTrip());
     //   System.out.println("Total energy: "+ this.traffic.getTotalEnergy());
        //System.out.println("Num Ambulances: "+this.traffic.getNumOfAmbulancesCompletedAfterSimulation());
        //      System.out.println("MESSAGE FOR OBSERVER "+ msgForObserver);
        try {
             //only log
             this.da.sendLog(LogDeviceAgent.Action.sendMsg, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgObserverAgent,"269", LogDeviceAgent.Resource.ObserverAgent, msgForObserver);
      
             
            this.da.sentMsgToSelectRecipient(adressObserver, msgForObserver);
        } catch (FIPAException ex) {
         //   Logger.getLogger(ControlAgentLoop.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (messageFromObserverAgent == null) {
            ACLMessage acmsg = this.da.receiveMessage();
            if (acmsg != null) {
                messageFromObserverAgent = acmsg.getContent();
                
                this.da.sendLog(LogDeviceAgent.Action.receiveMsg, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.msgObserverAgent,"291", LogDeviceAgent.Resource.ObserverAgent, messageFromObserverAgent);
      
                
                
                if (messageFromObserverAgent.contains("initSimulation")) {
                    this.traffic.setFinishSimulation(false);
                }

                //           System.out.println("MESSAGE FROM OBSERVER "+ messageFromObserverAgent);
            }
        }
        // }

    }

    public void read() {

        while (this.numMsg < 1) {
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
