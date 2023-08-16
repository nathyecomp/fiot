///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package simulateddevices;
//
//import jade.core.Agent;
//import jade.core.behaviours.CyclicBehaviour;
//import jade.core.behaviours.SequentialBehaviour;
//import jade.domain.FIPAException;
//import jade.lang.acl.ACLMessage;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import traffic.scenario.model.TrafficWorld;
//
///**
// *
// * @author Nathalia
// */
//public class ControlAgentLoop extends CyclicBehaviour{
//
//    TrafficWorld traffic;
//    ControlAgent ca;
//    public ControlAgentLoop(Agent a){
//        super(a);
//        ca = (ControlAgent) a;
//        traffic = TrafficWorld.getInstance();
//    }
//    
//@Override
//    public void action() {
//        String messageFromObserverAgent = null;
////        String msgForManager = this.da.device.getName();
//        String msgForObserver = "";
//        String adressObserver = "OBSERVER";
////        while(!this.traffic.isFinishSimulation()){
////        
////        }
//        if(this.traffic.isFinishSimulation()){
//            
//            msgForObserver+= this.traffic.isFinishSimulation();
//            msgForObserver+=";";
//            msgForObserver+=this.traffic.getTimeSimulation();
//            msgForObserver+=";";
//            msgForObserver+=this.traffic.getNumOfCarsCompletedAfterSimulation();
//            System.out.println("MESSAGE FOR OBSERVER "+ msgForObserver);
//            try {
//                this.ca.sentMsgToSelectRecipient(adressObserver, msgForObserver);
//            } catch (FIPAException ex) {
//                Logger.getLogger(ControlAgentLoop.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//             while(messageFromObserverAgent == null){
//                ACLMessage acmsg = this.ca.receiveMessage();
//                if(acmsg!=null){
//                messageFromObserverAgent = acmsg.getContent();
//                if(messageFromObserverAgent.contains("initSimulation")){
//                    this.traffic.setFinishSimulation(false);
//                }
//                
//                    System.out.println("MESSAGE FROM OBSERVER "+ messageFromObserverAgent);
//                }
//             }
//        }
//
//    }
//    
//    
//}
