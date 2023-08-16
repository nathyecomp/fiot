///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package simulateddevices;
//
//
//import jade.core.Agent;
//import jade.lang.acl.ACLMessage;
//
///**
// * Responsible to verify open IPs and create new Agents
// *
// * @author Nathalia
// */
//public class ControlAgent extends AdaptationAgent {
//
//    public ControlAgent(String name, String container){
//            super.setNameAgent(name);
//            super.setNameContainer(container);
//    }
//    protected void setup() {
//
//        super.setup();
//        System.out.println("Agent started: " + this.getAID());
//        // this.controlLoop = buildControlLoop();
//        addBehaviour(new ControlAgentLoop(this));
//    }
//
//        public ACLMessage receiveMessage() {
//        ACLMessage msg = receive();
//        return msg;
//    } 
//    
//    public String[] getSenderIP(ACLMessage msg){
//        return msg.getSender().getAddressesArray();
//    }
//}
