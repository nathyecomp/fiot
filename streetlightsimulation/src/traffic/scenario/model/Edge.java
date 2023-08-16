/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.scenario.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nathi_000
 * 
 * As arestas tëm luz ambiente e tem pessoas passando por ela
 * A luz interfere na velocidade das pessoas
 */
public class Edge extends Element {

    double lightIntensity = 0;
    double previousLightIntensity = 0;

    double maxVelocity;
    int numberPeople = 0;
    int maximumNumPeople = 10;

    String nodeBegin;
    String nodeEnd;
  //  Node roadFinal;

    String id;

    Map<String, Person> listPeople;

    public Edge(String name) {
        super.type = "edge";
        super.name = name;
        listPeople = new HashMap<>();
        numberPeople = 0;
    }
    
    public void addPerson(Person person){
        listPeople.put(person.id, person);
        numberPeople++;
    }
    
    public Person removePerson(String id){
        
       if(listPeople.containsKey(id)){
           numberPeople--;
           return listPeople.remove(id);
       }
       return null;
    }
    

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public String getBegin() {
        return nodeBegin;
    }

    public void setBegin(String begin) {
        this.nodeBegin = begin;
    }

    public String getEnd() {
        return nodeEnd;
    }

    public void setEnd(String end) {
        this.nodeEnd = end;
    }
    
    public int getNumberPeople(){
        return this.numberPeople;
    }
    
    public boolean containsPerson(){
        if (this.numberPeople>0) return true;
        else return false;
    }


    public int getMaximumNumPeople() {
        maximumNumPeople = World.getInstance().getMaximumNumPeoplePerVia();
        return maximumNumPeople;
    }
    

    public void setMaximumNumPeople(int maximumNumPeople) {
        this.maximumNumPeople = maximumNumPeople;
    }

    
    public double getRatePeople() {
        double people = this.getNumberPeople();
        double maximum = this.getMaximumNumPeople();
        if (people == 0) {
            return 0;
        }
        double div = people/maximum;
        return div;
    } 


    public double getLightIntensity() {
        return this.lightIntensity;
    }
    
    
    
   /* public void addLightIntensity(double value){
        this.previousLightIntensity = this.lightIntensity;
        this.lightIntensity = this.lightIntensity+value;
         if (this.lightIntensity > 0.8) {
            this.lightIntensity =  1.0;
        } else if (this.lightIntensity > 0.2) {
            this.lightIntensity =  0.5;
        } else {
            this.lightIntensity =  0;
        }
    }*/
    
    public String getColor() {
        String color = "";
        if(this.lightIntensity==0.0){
            color = "black";
        }
        else if(this.lightIntensity==0.5){
            color = "gray";
        }
        else {
            color = "yellow";
        }
        return color;
    }

    public void setLightIntensity(double lightValue) {
        this.previousLightIntensity = this.lightIntensity;
        this.lightIntensity = lightValue;
    }

    public double getPreviousLightIntensity() {
        return this.previousLightIntensity;
    }

 /*   public double changeSignalColor() {
        return (int) (Math.round((Math.random())));
    }*/

    public String readValuesFromInputSensors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 /*   public double getColorSignalNeuralOutput() {
        if (!this.agentActivated) {
            return this.randomValue();
        }
        return colorSignalNeuralOutput;
    }*/

  //  public void setColorSignalNeuralOutput(double colorSignalNeuralOutput) {
   //     this.colorSignalNeuralOutput = colorSignalNeuralOutput;
    //}

  /*  public double getSignalVoice() {
        if (!this.agentActivated) {
            this.signalVoice = this.randomValue();
        }
        if (this.signalVoice > 0.7) {
            return 1.0;
        } else if (this.signalVoice > 0.3) {
            return 0.5;
        } else {
            return 0;
        }
        //return signalVoice;
    }*/

/*    public void setSignalVoice(double signalVoice) {
        this.signalVoice = signalVoice;
    }

    public boolean isAgentActivated() {
        return agentActivated;
    }

    public void setAgentActivated(boolean agentActivated) {
        this.agentActivated = agentActivated;
    }*/

    public void processActuatorInformation() {
throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.


    }
    

}
