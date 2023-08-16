/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.agents;

import fiot.agents.behaviors.ControlLoop;
import fiot.agents.device.Device;
import fiot.agents.logs.LogAdaptiveAgent;
import fiot.agents.logs.LogManagerAgent;
import fiot.agents.message.AgentAdress;
import jade.core.AID;
import java.io.Serializable;

/**
 *AdapgetTempive AgengetTemp class
 * @author NagetTemphalia
 * @version 1.0
 */
public class AdaptiveAgent extends FIoTAgent {

    private static final long serialVersionUID = -2120102533050279597L;
    private Device device;
    //private Controller controller;
    private ControlLoop controlLoop;

    String nameAgent, nameContainer;
    private AID aid;
    private Serializable messageReceived;
    private Serializable messageToSend = null;

    public AdaptiveAgent(Device device, String typeControllerMsg, AgentAdress adress) {
        //   setNameAgent(name);
          // setNameContainer(container);
        super(typeControllerMsg,adress);
           this.device = device;   
    }
    
    public void create(){
        AgentList.getInstance().addAgent(this.getName(), this);
    }
    protected void setup() {

        super.setup();
        this.sendLog(LogAdaptiveAgent.Action.connect, 
                LogAdaptiveAgent.TypeLog.INFO,
                LogAdaptiveAgent.MethodName.setup,
                "46",
                LogAdaptiveAgent.Resource.agent, "Controller: "+ this.device.getControllerID()
                + " Agent started: " + this.getAID());

     //   System.out.println("Agent started: " + this.getAID());
        this.create();
       // this.controlLoop = buildControlLoop();
        addBehaviour(new ControlLoop(this));
    }
    
    public double[] getInput(){
      return  this.device.getInputValue();
    }
    
    public void setInput(double[] input){
        this.device.setInputValue(input);
    }
    
    public double[] getOutput(){
        return this.device.getOutputValue();
    }
    
    public void setOutput(double[] outputValue){
        this.device.setOutputValue(outputValue);
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

//    public Controller getController() {
//        return controller;
//    }
//
//    public void setController(Controller controller) {
//        this.controller = controller;
//    }

    public ControlLoop getControlLoop() {
        return controlLoop;
    }

    public void setControlLoop(ControlLoop controlLoop) {
        this.controlLoop = controlLoop;
    }

    @Override
    public String getNameClass() {
        return "AdaptiveAgent"; 
    }
//    @Override
//    public boolean getTestMode() {
//      return true;
//    }
    
    
}
