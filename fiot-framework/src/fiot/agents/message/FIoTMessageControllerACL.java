/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents.message;

import fiot.agents.FIoTAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathalia
 */
public class FIoTMessageControllerACL extends FIoTMsgController{

    FIoTAgent a;

    
    public FIoTMessageControllerACL(FIoTAgent agent) {
        this.a = agent;
        this.setType("ACL");
   //     super(agent,type);
     //   this.type = "ACL";
    }
//    @Override
//    public FIoTMessageControllerACL getInstance(FIoTAgent a) {
//        this.a = a;
//        return this;
//    }

    @Override
    public FIoTMessage readFIoTMessage() {
        FIoTMessage m = new FIoTMessage();
        ACLMessage msg = null;
        while(msg ==null){
                msg= this.a.receive();
        }
        if(msg!=null){
        String deviceLocalName = msg.getSender().getLocalName();
        m.setIp(deviceLocalName);
        m.setContent(msg.getContent());
        m.setPort("");
        return m;
        }
        
        else return null;
        
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendFIoTMessage(FIoTMessage msg) {
        try {
            return this.sentMsgToSelectRecipient(msg.getIp(), msg.getContent());
        } catch (FIPAException ex) {
            Logger.getLogger(FIoTMessageControllerACL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
     private boolean sentMsgToSelectRecipient(String nameAgent, String content) throws FIPAException {
         
       //  System.out.println("NO CONTEXTO DE FIOTMESSAGECONTROLLERACL");
        // System.out.println("quem vai receber a mensagem é "+nameAgent);
         //System.out.println("o conteudo da mensagem eh "+content);
        AMSAgentDescription[] agents = null;

        SearchConstraints c = new SearchConstraints();
        c.setMaxResults(new Long(-1));
        agents = AMSService.search(this.a, new AMSAgentDescription(), c);
        AID myID = this.a.getAID();
        for (int i = 0; i < agents.length; i++) {
            AID agentID = agents[i].getName();
            // System.out.println("Name agent eh "+agentID.getLocalName());
            if (agentID.getLocalName().equals(nameAgent)) {
               // System.out.println("foud agent id com o nome do agente");
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setContent(content);
                msg.addReceiver(agents[i].getName());
                this.a.send(msg);
            }
        }
        return true;

    }

}
