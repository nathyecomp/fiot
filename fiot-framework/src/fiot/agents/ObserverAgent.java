/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents;

import fiot.agents.logs.LogObserverAgent;
import fiot.agents.message.AgentAdress;
import instance.agent.behaviors.ObserverLoop_MerryChristmas;
import instance.agent.behaviors.ObserverLoop_StreetLight;

/**
 *
 * @author Nathalia
 */
public class ObserverAgent extends FIoTAgent{
     @Override
    public String getNameClass() {
        return "ObserverAgent"; 
    }
//    @Override
//    public boolean getTestMode() {
//        return true;
//    }

	private static final long serialVersionUID = -2120102533050279597L;

    public ObserverAgent(String typeControllerMsg, AgentAdress adress) {
        super(typeControllerMsg, adress);
    }

    protected void setup() {

        super.setup();
        //System.out.println("Agent started: " + this.getAID());
        this.sendLog(LogObserverAgent.Action.connect, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.setup,"38", LogObserverAgent.Resource.agent, "Agent started: " + this.getAID());

        this.create();
        // this.controlLoop = buildControlLoop();
        addBehaviour(new ObserverLoop_StreetLight(this));
        //addBehaviour(new ObserverLoop_MerryChristmas(this));
    }
    
    public void create(){
        AgentList.getInstance().setObserverAgent(this);
    }


   
    
    
}
