/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulateddevices;

import br.pucrio.les.mas.publisher.framework.TestableAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nathi_000
 * http://www.iro.umontreal.ca/~vaucher/Agents/Jade/primer4.html
 *
 */
public abstract class AdaptationAgent extends TestableAgent {

    Map<String, Character> caracs = new HashMap<>();
    String nameAgent, nameContainer;
    private AID aid;
    private Serializable messageReceived;
    private Serializable messageToSend = null;
    /**
     *
     */
    private static final long serialVersionUID = 6776055151529992525L;

    protected void setup() {

        super.setup();
        System.out.println("Agent started: " + this.getAID());
       // this.controlLoop = buildControlLoop();
        //addBehaviour(new AgenteCriticarCardapioBehavior(this, 5000));
    }




    public String getNameAgent() {
        return nameAgent;
    }

    public void setNameAgent(String nameAgent) {
        this.nameAgent = nameAgent;
    }

    public String getNameContainer() {
        return nameContainer;
    }

    public void setNameContainer(String nameContainer) {
        this.nameContainer = nameContainer;
    }

    public void replyMessage(ACLMessage msg, String contentReply) {
        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
        reply.setContent(contentReply);
        reply.addReceiver(msg.getSender());
        send(reply);
    }

    public ACLMessage receiveMessage() {
        ACLMessage msg = receive();
        return msg;
    }

    public void sendMessage(String content, String containerAgent) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent(content);
        //msg.addReceiver( new AID( containerAgent, AID.ISLOCALNAME) );
        msg.addReceiver(this.aid);
        send(msg);
    }

    public void sentMsgToSelectRecipient(String nameAgent, String content) throws FIPAException {
        AMSAgentDescription[] agents = null;

        SearchConstraints c = new SearchConstraints();
        c.setMaxResults(new Long(-1));
        agents = AMSService.search(this, new AMSAgentDescription(), c);
        AID myID = getAID();
        for (int i = 0; i < agents.length; i++) {
            AID agentID = agents[i].getName();
            //System.out.println("Name agent eh "+agentID.getLocalName());
            if (agentID.getLocalName().equals(nameAgent)) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setContent(content);
                msg.addReceiver(agents[i].getName());
                send(msg);
            }
            /*System.out.println(
             ( agentID.equals( myID ) ? "*** " : "    ")
             + i + ": " + agentID.getName() 
             );*/
        }

    }
}
