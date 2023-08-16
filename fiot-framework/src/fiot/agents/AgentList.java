/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents;

import fiot.agents.AdaptiveAgent;
import fiot.agents.ManagerAgent;
import fiot.agents.ObserverAgent;
import jade.core.Agent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Nathalia
 */
public class AgentList {
    
   private final HashMap<String, AdaptiveAgent> adaptiveAgentList;
   //Caso eu tenha mais de uma aplicação/experimento, guardos os agentes por manager
   private final HashMap<String,List<String>>agentsListIp;
   private ObserverAgent observerAgent;
   private ManagerAgent managerAgent;
   private static AgentList instance;
   //port, nameagent
   private final HashMap<String, String> portAgentList; //futuramente posso ter uma relação de portas por agentes
   //,mas ainda nao quero
   private final HashMap<String,List<String>> ipListPort;
   
   double[] inputActual;
   double[] outputActual;
   private AgentList(){
       adaptiveAgentList = new HashMap<>();
       portAgentList = new HashMap<>();
       ipListPort = new HashMap<>();
       agentsListIp = new HashMap<>();
   }
   
  public static AgentList getInstance(){
           // Verifica se a variável possui algum valor,caso não, é criada a instancia.
           if (instance == null) {
               instance = new AgentList();
           }
           // Se a variavel possui algum valor, é retornado para quem está pedindo
           return instance;
     }
  
  public int getNumAdaptiveAgentList(){
      return adaptiveAgentList.size();
  }
    
    public boolean addAgent(String nameAgent, AdaptiveAgent agent){
        if(adaptiveAgentList.containsKey(nameAgent))
            return false;
        //if ip+port is ocupated, return false
        adaptiveAgentList.put(nameAgent, agent);
        this.addAgentListIp(nameAgent);
        return true;
    }
    
    //making a list of adaptive agents for each manegeragent
    public void addAgentListIp(String nameAgent){
        // agent = this.getManagerAgent();
         String ipAgent = this.managerAgent.getAdress().getIp();
        if(!this.agentsListIp.containsKey(ipAgent)){
            List<String> agents = new ArrayList<>();
            this.agentsListIp.put(ipAgent, agents);
        }
        if(!this.agentsListIp.get(ipAgent).contains(nameAgent))
        {
            this.agentsListIp.get(ipAgent).add(nameAgent);
        }
    }
    
    public boolean changeAgent(String nameAgent, AdaptiveAgent agent){
        if(adaptiveAgentList.containsKey(nameAgent)){
            adaptiveAgentList.remove(nameAgent);
            adaptiveAgentList.put(nameAgent, agent);
            return true;
        }
        return false;
    }
    
    public AdaptiveAgent getAgent(String nameAgent){
        return adaptiveAgentList.get(nameAgent);
    }
    
    public AdaptiveAgent removeAgent(String nameAgent){
        return adaptiveAgentList.remove(nameAgent);
    }

    public ObserverAgent getObserverAgent() {
        return observerAgent;
    }

    public void setObserverAgent(ObserverAgent observerAgent) {
        this.observerAgent = observerAgent;
    }

    public ManagerAgent getManagerAgent() {
        return managerAgent;
    }

    public void setManagerAgent(ManagerAgent managerAgent) {
        this.managerAgent = managerAgent;
    }
    
    
    public boolean containsAdaptiveAgent(String nameAgent){
        if(this.adaptiveAgentList.containsKey(nameAgent)){
            return true;
        }
        return false;
    }
    
    public String getPortForAgent(String ip){
        
        String port = this.getPortAvailable(ip);
        while(!this.portIsAvailable(ip, port)){
            port = this.getPortAvailable(ip);
        }
        this.ipListPort.get(ip).add(port);
        return port;
    }
    
    private boolean portIsAvailable(String ip, String port){
        if(!this.ipListPort.containsKey(ip)){
            List<String> portL = new ArrayList<>();
            this.ipListPort.put(ip, portL);
        }
        if(this.ipListPort.get(ip).contains(port))
            return false;
        return true;
    }
    private String getPortAvailable(String ip){
        int minPort = 1024;
        int maxPort = 60000;
        int port= (int) ((Math.random() * maxPort)+minPort);
        return String.valueOf(port);
    }
    
    //return list name of adaptive agents
    public List<String> getAgentListOnIp(String ipManagerAgent){
        
        if(this.agentsListIp.containsKey(ipManagerAgent))
        return this.agentsListIp.get(ipManagerAgent);
        else return new ArrayList<String>();
    }
//    private int getPort(){
//        int maxPort
//        (int) (Math.random() * this.simulation.getNumberOfPopulation());
//    }
    
    public Collection<AdaptiveAgent> getAllAdaptive(){
        return this.adaptiveAgentList.values();
    }
    
    public HashMap<String, AdaptiveAgent> getAllAdaptiveNames(){
        return this.adaptiveAgentList;
    }

    //P APAGAR DEPOIS
    public double[] getInputActual() {
        return inputActual;
    }

    public void setInputActual(double[] inputActual) {
        this.inputActual = inputActual;
    }

    public double[] getOutputActual() {
        return outputActual;
    }

    public void setOutputActual(double[] outputActual) {
        this.outputActual = outputActual;
    }
    
}
