/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.general;

import fiot.agents.AdaptiveAgent;
import fiot.agents.AgentList;
import fiot.agents.ManagerAgent;
import fiot.agents.InitAgent;
import fiot.agents.ObserverAgent;
import fiot.agents.controller.Controller;
import fiot.agents.controller.ControllerList;
import fiot.agents.controller.neuralnetwork.ThreeLayerNetwork;
import fiot.agents.device.Device;
import fiot.agents.message.AgentAdress;
import fiot.agents.message.quote.QuoteServerThread;
import java.io.File;
import java.io.IOException;
import fiot.learning.Simulation;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *To create new instances of FIoT, you have to use this class to make intermediation
 * between you application and FIoT features
 * @author Nathalia
 * @version 1.0
 */
public class ApplicationConfiguration {
   String typeController = ""; 
   String selectedAgent = "";
   String nameActivatedController="";
   ControllerList listControl ;
   private static ApplicationConfiguration instance;
   AgentList agentList;
   private AgentAdress adressManager;
   private AgentAdress adressObserver;
   private ApplicationConfiguration(){
       listControl = ControllerList.getInstance();
       agentList = AgentList.getInstance();
   }
   public static ApplicationConfiguration getInstance(){
           // Verifica se a variável possui algum valor,caso não, é criada a instancia.
           if (instance == null) {
               instance = new ApplicationConfiguration();
           }
           // Se a variavel possui algum valor, é retornado para quem está pedindo
           return instance;
     }
   /**
    * Set the name of controller to be used (e.g: "Three Layer Network")
    * @param typeController
    * @param nameController 
    */
   public void setNameController(String typeController, String nameController){
       this.typeController = typeController;
       this.nameActivatedController = nameController;
   }
   
   /**
    * Select agent to view its controller drawed
    * @param nameAgent 
    */
   public void setSelectedAgent(String nameAgent){
       this.selectedAgent = nameAgent.replaceAll(" ", "");
       AdaptiveAgent ag = AgentList.getInstance().getAgent(nameAgent);
       
       this.typeController = ag.getDevice().getControllerType();
       this.nameActivatedController = ag.getDevice().getControllerID();
   }
   
   /**
    * To set the controller to be used by adaptive agents on your application
    * The nameSelecteController has to be the name of type of controller, such as
    * "Three Layer Network"
    * The file contains configuration of controller and has to contains the specific
    * name for controller, such as "laneController", "bananaController"
    * @param nameSelectedController
    * @param file
    * @throws IOException 
    */
   public void createController(String nameSelectedController, File file) throws IOException{
       String selectedController = nameSelectedController;
        
        if(selectedController.equals("Three Layer Network")){

            System.out.println("Three Layer Selected");
            File f = file;
            ThreeLayerNetwork controlLayer = new ThreeLayerNetwork(f);
            System.out.println("Controller made");
            listControl.addController(controlLayer.getNameType(), controlLayer);
            this.setNameController(selectedController, controlLayer.getNameType());
            System.out.println("Controller painted");
        } 
        
   }
   
   /**
    * 
    * @return the controller being used by adaptive agent 
    */
   public Controller getActualController(){
       ThreeLayerNetwork neural = (ThreeLayerNetwork) ControllerList.
                getInstance().getController(this.nameActivatedController);
       return neural;
   }
   
   /**
    * To set the learning method to be used  to train the controller
    * @param typeLearning
    * @param file
    * @throws IOException 
    */
   public void setLearningMethod(String typeLearning, File file) throws IOException{
            // TODO add your handling code here:
            File fil = file;

            if(fil.getName().contains("Configuration")){
                Simulation.getInstance().setOnAdaptation(true);
                Simulation.getInstance().setSimulationConfiguration(typeLearning, this.nameActivatedController,fil);
            }
            else{
             Simulation.getInstance().setOnAdaptation(false);
             Simulation.getInstance().setConfigurationExecution(fil);
            }
   }
   
   /**
    * Start the main agents: Manager Agent and Observer Agent
    * The Manager Agent is already started on the port 9876
    * @param ip
    * @param typeMsgController 
    */
   public void startMainAgents(String ip, String typeMsgController){
       String port = "";
       if(!typeMsgController.equals("ACL"))
           port="9876";
      //  port = this.agentList.getPortForAgent(ip);
       adressManager = new AgentAdress("MANAGER","MANAGER", ip,port);
       InitAgent.init(new ManagerAgent(typeMsgController,adressManager), "MANAGER", "MANAGER");
       
       String portOB = "";
       if(!typeMsgController.equals("ACL"))
        portOB = this.agentList.getPortForAgent(ip);
       adressObserver = new AgentAdress("OBSERVER","OBSERVER", ip,portOB);
       InitAgent.init(new ObserverAgent(typeMsgController, adressObserver), "OBSERVER", "OBSERVER");
//       try {
//           new QuoteServerThread().start();
//       } catch (IOException ex) {
//           Logger.getLogger(ApplicationConfiguration.class.getName()).log(Level.SEVERE, null, ex);
//       }
   }
   
//   public void setMainAgents(ManagerAgent go, ObserverAgent ob){
//       
//   }
   public String getIpManager(){
       return this.adressManager.getIp();
   }
   public String getPortManager(){
       return this.adressManager.getPort();
   }
   
   public String getIpObserver(){
       return this.adressObserver.getIp();
   }
   public String getPortObserver(){
       return this.adressObserver.getPort();
   }
   
   /**
    * This method considers that each different application has one different Manager Agent
    * 
    * @return the list of agents created by an specific Manager Agent 
    */
   public List<String> getAdaptiveAgentList(){
       return this.agentList.getAgentListOnIp(this.getIpManager());
   }
   
   /**
    * 
    * @return all adaptive agents created 
    */
   public Collection<AdaptiveAgent> getAllAdaptiveAgents(){
       return this.agentList.getAllAdaptive();
   }
   /**
    * 
    * @return a hashmap relating the adaptive agents and their names
    */
   public HashMap<String, AdaptiveAgent> getTheListCompleteAdaptive(){
       return this.agentList.getAllAdaptiveNames();
   }
 
   /**
    * Giving the name of adaptive agent, returns the Device (this class contains
    * informations about ip and name of virtualized device
    * @param nameAgent
    * @return 
    */
   public Device getDeviceOfAgent(String nameAgent){
       return this.agentList.getAgent(nameAgent).getDevice();
   }
   
   /**
    * 
    * @param nameAgent
    * @return the input[] values readed by device's sensors
    */
   public double[] getInputDeviceAgent(String nameAgent){
       return this.agentList.getAgent(nameAgent).getDevice().getInputValue();
   }
   
   /**
    * The output computed for device based on its input
    * @param nameAgent
    * @return 
    */
   public double[] getOutputDeviceAgent (String nameAgent){
       return this.agentList.getAgent(nameAgent).getDevice().getOutputValue();
   }
           
}
