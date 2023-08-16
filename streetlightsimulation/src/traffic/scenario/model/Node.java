/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.scenario.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nathi_000
 */
public class Node extends Element{
    //on, off or dim
 //   String status = "off";
    //time of duration
  //  double timeCurrentState;
    int numPeople = 0;
 //   int maximumNumPeople = 1;
    //int signalColor = 0;
  //  int timeSignalWithoutIA = 0;
   Map<String, Person> listPeople;
   // StreetLight streetLight;
    ArrayList<String> listEdgeEnd;
    ArrayList<String> listEdgeBegin;
    
    String closestNode="";
    String secondClosestNode="";
    //relação de lanes conectadas e sinal
 //   Map<String,Double>
   // String id;
    Point2D xy;
    
    public Node(String name, Point2D xy){
        super.type = "node";
        super.name = name;
        this.xy = xy;
        
        listPeople = new HashMap<>();
        listEdgeEnd = new ArrayList<>();
        listEdgeBegin = new ArrayList<>();
    }
    
  /*  public Node(String name){
        super.type = "node";
        super.name = name;
        listPeople = new HashMap<>();
        listEdgeEnd = new ArrayList<>();
        listEdgeBegin = new ArrayList<>();
      //  streetLight = new StreetLight(name);
    }*/

    public String getClosestNode() {
        return closestNode;
    }

    public void setClosestNode(String closestNode) {
        this.closestNode = closestNode;
    }

    public String getSecondClosestNode() {
        return secondClosestNode;
    }

    public void setSecondClosestNode(String secondClosestNode) {
        this.secondClosestNode = secondClosestNode;
    }

    public Point2D getXy() {
        return xy;
    }

    public void setXy(Point2D xy) {
        this.xy = xy;
    }
    
    
    
    

 /*   public int getTimeSignalPutOnRed() {
        return timeSignalWithoutIA;
    }

    public void setTimeSignalPutOnRed(int timeSignalPutOnRed) {
        this.timeSignalWithoutIA = timeSignalPutOnRed;
    }*/

    public void addEdgeEnd(String edgeName){
        listEdgeEnd.add(edgeName);
    }
    
    public void addEdgeBegin(String laneName){
        listEdgeBegin.add(laneName);
    }

    public ArrayList<String> getListEdgeEnd() {
        return listEdgeEnd;
    }

    public ArrayList<String> getListEdgeBegin() {
        return listEdgeBegin;
    }
    
    public boolean listEdgeBeginContains(String name){
       return this.listEdgeBegin.contains(name);
    }
    public boolean listEdgeEndContains(String name){
       return this.listEdgeEnd.contains(name);
    }
     
    public void addCar(Person car){
        listPeople.put(car.id, car);
        numPeople++;
    }
    
    public Person removeCar(String id){
        
       if(listPeople.containsKey(id)){
           numPeople--;
       return listPeople.remove(id);
       }
       return null;
    }
    
//    public int getNumCar(String laneBefore) {
//        if(laneBefore==null) return numPeople;
//        Edge l = World.getInstance().getLaneByRoads(laneBefore, this.getName());
//        int nC = l.getNumCar();
//        return nC;
//    }
//
//    public void setNumCar(int numPeople) {
//        this.numPeople = numPeople;
//    }

   /* public int getMaximumNumPeople() {
        this.maximumNumPeople = World.getInstance().getMaximumNumPeoplePerVia();
        return maximumNumPeople;
    }

    public void setMaximumNumPeople(int maximumNumPeople) {
        this.maximumNumPeople = maximumNumPeople;
    }*/
    
//    public double getSignal(String nameLane){
//        
//      return ((Edge)World.getInstance().getElement(nameLane)).getLightIntensity();
//        
//       // return this.signalColor;
//    }
    
    /*A princípio, o road altera o sinal de todos os lanes:
    ele sorteia o lane com o sinal ativo e coloca os demais em 1(vermelho)
    Posteriormente, com os agentes, cada agente vai dar um valor pro sinal do lane
    o road vai ativar o lane com o maior sinal (isso vai criar uma espécie de
    competição entre as lanes. Espera-se que elas desenvolvam algum sinal de
    comunicação para informar que ela precisa ativar o sinal
    e fazer com que as demais lanes desativem
    */
  /*  public void cycleChange(){
//        if(World.getInstance().isSimulationNotIA())
//            getSignalWithoutIA();
        
        //int numRandom = (int)(Math.round((Math.random() * (this.listEdgeEnd.size()-1))));
        double higger = 0;
        String laneHigger ="";
        for(String laneName: this.listEdgeEnd){
            Edge lane = (Edge)World.getInstance().getElement(laneName);
            double valueSignal = lane.getColorSignalNeuralOutput();
            if(valueSignal>=higger){
                higger = valueSignal;
                laneHigger = laneName;
            }
        }
        for(String laneName: this.listEdgeEnd){
            Edge lane = (Edge)World.getInstance().getElement(laneName);
            if(lane.getName().equals(laneHigger)){
                if(higger>0.6)
                lane.setLightIntensity(0);
                else lane.setLightIntensity(1);
            }
            else lane.setLightIntensity(1);
            
            World.getInstance().changeElement(laneName, lane);
        }   
    }*/
    
  /*  public void changeSignalPDeletarTempoFixo(){
//        if(World.getInstance().isSimulationNotIA())
//            getSignalWithoutIA();
        
        //int numRandom = (int)(Math.round((Math.random() * (this.listEdgeEnd.size()-1))));
        double higger = 0;
        String laneHigger ="";
        double cont = 0.7;
        //put == 1 se quser que os semaforos que nao estam em intersecção fiquem sempre verde
        if(this.listEdgeEnd.size()==7){
            String laneName = listEdgeEnd.get(0);
              Edge lane = (Edge)World.getInstance().getElement(laneName);
               lane.setLightIntensity(0);
             World.getInstance().changeElement(laneName, lane);
        }
        else{
        for(String laneName: this.listEdgeEnd){
            Edge lane = (Edge)World.getInstance().getElement(laneName);
             double valueSignal = lane.getColorSignalNeuralOutput();
            if(lane.getPreviousLightIntensity()==0){
                valueSignal = 0;
            }
            else valueSignal = cont;
            cont = 0.9;
           
            double rateAmbulance = lane.getRateAmbulance();
            if(rateAmbulance >0){
                valueSignal = 1.0;
            }
            if(valueSignal>=higger){
                higger = valueSignal;
                laneHigger = laneName;
            }
        }
        for(String laneName: this.listEdgeEnd){
            Edge lane = (Edge)World.getInstance().getElement(laneName);
            if(lane.getName().equals(laneHigger)){
                if(higger>0.6)
                lane.setLightIntensity(0);
                else lane.setLightIntensity(1);
            }
            else lane.setLightIntensity(1);
            
            World.getInstance().changeElement(laneName, lane);
        }   
        }
    }
*/
    
    @Override
    public String readValuesFromInputSensors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processActuatorInformation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
