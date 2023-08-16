/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.scenario.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import simulateddevices.LogDeviceAgent;

/**
 *
 * @author nathalianascimento
 */
public class StreetWorld {
    private static StreetWorld instance;
    double environmentalLight = 0;
    //List<StreetLight> lights;
    Map<String, StreetLight> lights; //String - Node 
    
    double threshold1 = 0.4;
    double threshold2 = 0.7;
    double threshold3 = 0.6;
    
     private  StreetWorld(){
        environmentalLight = 0;
        lights = new HashMap<>();
    }
    public static StreetWorld getInstance() {
        if (instance == null) {
            instance = new StreetWorld();
        }
        return instance;
    }
    
    public void addNewLight(String nodeName, StreetLight light){
        lights.put(nodeName,light);
    }
    
    public StreetLight getStreetLight(String nodeName){
        return lights.get(nodeName);
    }

    public Map<String, StreetLight> getLights() {
        return lights;
    }
    
    public List<StreetLight> getLightList(){
        return (List<StreetLight>) lights.values();
    }

    public void setLights(Map<String, StreetLight> lights) {
        this.lights = lights;
    }
    
    /*A luz em cada edge varia de acordo com os postes conectados à ela + luz ambiente*/
    public void changeEnvironmentalLightFromAllEdges(){
        double envLight = getEnvironmentalLight();
       // System.out.println("Ênvironmental light is");
      // System.out.println(lightIntensity);
        
        List<Edge> edgeList = new ArrayList<>(World.getInstance().getAllEdgesList());
        for(Edge edge: edgeList){
            Node nBegin = (Node)World.getInstance().getElement(edge.getBegin());
            Node nEnd = (Node)World.getInstance().getElement(edge.getEnd());
            double lightFromNodeBegin = this.lights.get(nBegin.getName()).getTransmittedLightDecision();
            double lightFromNodeEnd = this.lights.get(nEnd.getName()).getTransmittedLightDecision();
            double lightIntensity = envLight + lightFromNodeBegin + lightFromNodeEnd;
             if (lightIntensity > this.threshold2) {
                lightIntensity =  1.0;
            } else if (lightIntensity > this.threshold1) {
                lightIntensity =  0.5;
            } else {
                lightIntensity =  0;
            }
             edge.setLightIntensity(lightIntensity);
             World.getInstance().changeElement(edge.getName(), edge);
             
             //only log
              String msgFromEdge = "Light "+ lightIntensity+ ": "+ "Env.Light "+ envLight+ " NBegin " +nBegin.getName()+ "-"+lightFromNodeBegin
                      + " NEnd " +nEnd.getName()+ "-"+lightFromNodeEnd;
                World.getInstance().getPanel().getAgentOnlyOne().sendLog(LogDeviceAgent.Action.calculateEdgeLight, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.changeLightFromEdges,"85", LogDeviceAgent.Resource.edge, msgFromEdge);
            //
     
                 //edge.addLightIntensity(environmentalLight);
        }
    }
      
    
  /*  public void changeEnvironmentalLightFromEdgesEnd(String nodeName, double value){
         Node node = (Node)World.getInstance().getElement(nodeName);
         for(String edgeName: node.getListEdgeEnd()){
            Edge edge = (Edge)World.getInstance().getElement(edgeName);
            edge.addLightIntensity(value);
        }
    }*/
    
    //Environmental light also depends on local light
   double readLightingSensor(double localLight){
        double lightIntensity = getEnvironmentalLight()+localLight;//analogRead(lightPin);
        if (lightIntensity > this.threshold2) {
            lightIntensity =  1.0;
        } else if (lightIntensity > this.threshold1) {
            lightIntensity =  0.5;
        } else {
            lightIntensity =  0;
        }
        return lightIntensity;
    } 
   
   //Verifies if there is a person in any edge linked to a node
   double readMotionSensor (String nodeName){
        Node node = (Node)World.getInstance().getElement(nodeName);
       boolean peopleDetected = false;
       for(String edgeName: node.getListEdgeBegin()){
            Edge edge = (Edge)World.getInstance().getElement(edgeName);
            if(edge.containsPerson()){
                peopleDetected = true;
            }
        }
       if(!peopleDetected){
           for(String edgeName: node.getListEdgeEnd()){
            Edge edge = (Edge)World.getInstance().getElement(edgeName);
            if(edge.containsPerson()){
                peopleDetected = true;
            }
        }
       }
       
       if(peopleDetected == false) return 0.0;
       else return 1.0;
   }
   
   //Get the output signal from light in the closest node
   double receiveWirelessData(String nodeName){
      Node node = (Node)World.getInstance().getElement(nodeName);
      Node closestNode = (Node) World.getInstance().getElement(node.getClosestNode());
      
      double valueListen = this.getStreetLight(closestNode.getName()).getWirelessTransmitter();
      
     //only log
      World.getInstance().getPanel().getAgentOnlyOne().sendLog(LogDeviceAgent.Action.receiveWirelessData,LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.receiveWirelessData,"136", LogDeviceAgent.Resource.streetlight, " is listening :"+closestNode.getName()+ "-"+valueListen);
      //
      
      
      return valueListen;
     //  World.getInstance().
   }

    public double getEnvironmentalLight() {
        return environmentalLight;
    }

    public void setEnvironmentalLight(double environmentalLight) {
        this.environmentalLight = environmentalLight;
    }
   
    public String readInputValue(String nodeName){
        return this.getStreetLight(nodeName).readValuesFromInputSensors();
    }
    
    public void setActuatorValue(String nodeName, String[] value){
        this.getStreetLight(nodeName).setActuatorValue(value);
         this.getStreetLight(nodeName).processActuatorInformation();
         
    }
    
    public int getNumberOfActuators(String nodeName){
        return this.getStreetLight(nodeName).getActuatorName().length;
    }
   
}
