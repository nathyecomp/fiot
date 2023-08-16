/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.agents;

import fiot.agents.behaviors.CreateNewAdaptiveAgent;
import fiot.agents.logs.LogManagerAgent;
import fiot.agents.message.AgentAdress;

/**
 * Responsible to verify open IPs and create new Agents
 *
 * @author Nathalia
 */
public class ManagerAgent extends FIoTAgent {
     @Override
    public String getNameClass() {
        return "ManagerAgent"; 
    }

//    @Override
//    public boolean getTestMode() {
//        return true;
//    }

    public ManagerAgent(String typeControllerMsg, AgentAdress adress) {
        super(typeControllerMsg, adress);
    }

    protected void setup() {

        super.setup();
        //System.out.println("Agent started: " + this.getAID());
        this.sendLog(LogManagerAgent.Action.connect, LogManagerAgent.TypeLog.INFO,LogManagerAgent.MethodName.setup,"36", LogManagerAgent.Resource.agent, "Agent started: " + this.getAID());
      
        this.create();
        this.printAdresses();
        // this.controlLoop = buildControlLoop();
        addBehaviour(new CreateNewAdaptiveAgent(this));
    }
    public void create(){
        AgentList.getInstance().setManagerAgent(this);
    }
    
    public void printAdresses(){
        String[] adresses = this.getAID().getAddressesArray();
        for(int c = 0; c< adresses.length; c++){
            System.out.println("Adress>>::: "+ c);
            System.out.println(adresses[c]);
        }
    }

    //no use
//        public ACLMessage receiveMessage() {
//        ACLMessage msg = receive();
//        return msg;
//    } 
  
        //can delete
//    public String[] getSenderIP(ACLMessage msg){
//        return msg.getSender().getAddressesArray();
//    }
  
    //transfered for FIoTMessage class
//    public void sentMsgToSelectRecipient(String nameAgent, String content) throws FIPAException {
//        AMSAgentDescription[] agents = null;
//
//        SearchConstraints c = new SearchConstraints();
//        c.setMaxResults(new Long(-1));
//        agents = AMSService.search(this, new AMSAgentDescription(), c);
//        AID myID = getAID();
//        for (int i = 0; i < agents.length; i++) {
//            AID agentID = agents[i].getName();
//            // System.out.println("Name agent eh "+agentID.getLocalName());
//            if (agentID.getLocalName().equals(nameAgent)) {
//                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//                msg.setContent(content);
//                msg.addReceiver(agents[i].getName());
//                send(msg);
//            }
//        }
//
//    }

   
}
