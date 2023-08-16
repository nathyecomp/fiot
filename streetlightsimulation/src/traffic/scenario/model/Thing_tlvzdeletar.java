///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package traffic.scenario.model;
//
//import java.awt.geom.Point2D;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// *
// * @author nathi_000
// */
//public class Thing extends Element {
//
//    //Signal
//
//    double colorSignal = 1;
//    double previousColorSignal = 1;
//
//    double colorSignalNeuralOutput = 1;
//    double signalVoice = 1;
//    //maximum allowed velocity
//    double maxVelocity;
//    //int numCar = 0;
//    int numberCars = 0;
//    int numberAmbulances = 0;
//    int maximumNumCar = 10;
//    int maximumNumAmbulance = 0;
//    //uma lane fica entre duas roads
//    String roadBegin;
//    String roadEnd;
//    Node roadFinal;
//
//    String id;
//
//    //Quero saber qual a lane mais próxima, para simular a captura do sinal da
//    //lane mais próxima
//    String closerLane;
//
//    String secondCloserLane;
//
//    boolean agentActivated = false;
//    Map<String, Person> listCar;
////    public Edge(String id, String road1, String road2){
////        this.id = id;
////        this.nodeBegin = road1;
////        this.nodeEnd = road2;
////    }
//
//    public Thing(String name, Point2D xy) {
//        super.type = "Lane";
//        super.nameController = "lightNeuralNetwork";
//        super.name = name;
//        String[] sensorNames = {"previousTransmitterSignal","previousListeningDecision",
//"previousLightDecision","lightSensor","motionSensor"};
//        //String[] sensorNames = {"microphone1", "previousColorSignal", "carRate","ambulanceRate"};
//       //  String[] sensorNames = {"microphone1", "previousColorSignal", "carRate"};
//        //String[] actuators = {"colorSignal", "signalVoice"};
//        String[] actuators = {"listeningDecision","transmitterSignal","lightDecision"};
//        super.setSensorName(sensorNames);
//        super.setActuatorName(actuators);
//        listCar = new HashMap<>();
////        numCar = 0;
//    }
//    
//    public void addCar(Person car){
//        listCar.put(car.id, car);
//        numberCars++;
//        
//        if(car.isIsAmbulance()){
//            numberAmbulances++;
//        }
//    }
//    
//    public Person removeCar(String id){
//        
//       if(listCar.containsKey(id)){
//           numberCars--;
//           if(listCar.get(id).isIsAmbulance()){
//               numberAmbulances--;
//           }
//           return listCar.remove(id);
//       }
//       return null;
//    }
//    
//    public String getCloserLane() {
//        return closerLane;
//    }
//
//    public void setCloserLane(String closerLane) {
//        // System.out.println("CloserLane for "+ this.getName()+ "-"+this.getEnd()+ ": "+ closerLane);
//        this.closerLane = closerLane;
//    }
//
//    public String getSecondCloserLane() {
//        return secondCloserLane;
//    }
//
//    public void setSecondCloserLane(String secondCloserLane) {
//        //System.out.println("SecondCloserLane for "+ this.getName()+ "-"+this.getEnd()+ ": "+ secondCloserLane);
//        this.secondCloserLane = secondCloserLane;
//    }
//
//    public double getMaxVelocity() {
//        return maxVelocity;
//    }
//
//    public void setMaxVelocity(double maxVelocity) {
//        this.maxVelocity = maxVelocity;
//    }
//
//    public String getBegin() {
//        return roadBegin;
//    }
//
//    public void setBegin(String begin) {
//        this.roadBegin = begin;
//    }
//
//    public String getEnd() {
//        return roadEnd;
//    }
//
//    public void setEnd(String end) {
//        this.roadEnd = end;
//    }
//    
//    public int getNumberCars(){
//        return this.numberCars;
//    }
//
//    public int getNumberAmbulances() {
//        return this.numberAmbulances;
//    }
//    
//
//    public int getMaximumNumCar() {
//       // Node road = (Node) World.getInstance().getElement(this.nodeEnd);
//        //     maximumNumCar = road.getMaximumNumCar();
//        maximumNumCar = World.getInstance().getMaximumNumPeoplePerVia();
//        return maximumNumCar;
//    }
//    
//
//    public void setMaximumNumCar(int maximumNumCar) {
//        this.maximumNumCar = maximumNumCar;
//    }
//
//    public int getMaximumNumAmbulance() {
//        maximumNumAmbulance = World.getInstance().getMaximumNumAmbulancePerVia();
//        return maximumNumAmbulance;
//    }
//
//    public void setMaximumNumAmbulance(int maximumNumAmbulance) {
//        this.maximumNumAmbulance = maximumNumAmbulance;
//    }
//
//    
//    public double getRatePeople() {
//        double people = this.getNumberCars();
//       // System.out.println("num people: "+people);
//        double maximum = this.getMaximumNumCar();
//        //System.out.println("Maximum "+ maximum);
//        if (people == 0) {
//            return 0;
//        }
//        double div = people/maximum;
//        return div;
//    } 
//    public double getRateAmbulance() {
//        double ambulances = this.getNumberAmbulances();
//       // System.out.println("num people: "+people);
//        //No total de carros que atravessaram, quantos eram ambulancia
//        double maximum = this.getMaximumNumAmbulance();
//        //System.out.println("Maximum "+ maximum);
//        if (ambulances == 0) {
//            return 0;
//        }
//        double div = ambulances/maximum;
//        return div;
//    }
//    
//
//    public double getSignalColor() {
//        return this.colorSignal;
//    }
//    
//    public String getColor() {
//        String color = "";
//        if(this.colorSignal==0){
//            color = "green";
//        }
//        else if(this.colorSignal==0.5){
//            color = "yellow";
//        }
//        else {
//            color = "red";
//        }
//        return color;
//    }
//
//    public void setSignal(double signal) {
//        this.previousColorSignal = this.colorSignal;
//        this.colorSignal = signal;
//    }
//
//    public double getPreviousColorSignal() {
//        return this.previousColorSignal;
//    }
//
//    public double changeSignalColor() {
//        return (int) (Math.round((Math.random())));
//    }
//
//            //{"microphone", "previousColorSignal", "carRate"};
//        //Microphone will get the value of sound emmited by the most nearly lane
//    @Override
//    public String readValuesFromInputSensors() {
//        String read = "";
//        double microphone = ((Thing) World.getInstance().getElement(this.getCloserLane())).getSignalVoice();
//        read += String.valueOf(microphone) + ";";
////        double microphone2 = ((Edge) World.getInstance().getElement(this.getSecondCloserLane())).getSignalVoice();
////        read += String.valueOf(microphone2) + ";";
//        double previousColor = ((Thing) World.getInstance().getElement(this.getName())).getSignalColor();
//        read += String.valueOf(previousColor) + ";";
//        double rate = ((Thing) World.getInstance().getElement(this.getName())).getRatePeople();
//        read += String.valueOf(rate) + ";";
//        double rateAmbulance = ((Thing) World.getInstance().getElement(this.getName())).getRateAmbulance();
//        read += String.valueOf(rateAmbulance);
//        return read;
//    }
//
//    public double getColorSignalNeuralOutput() {
//        if (!this.agentActivated) {
//            return this.randomValue();
//        }
//        return colorSignalNeuralOutput;
//    }
//
//    public void setColorSignalNeuralOutput(double colorSignalNeuralOutput) {
//        this.colorSignalNeuralOutput = colorSignalNeuralOutput;
//    }
//
//    public double getSignalVoice() {
//        if (!this.agentActivated) {
//            this.signalVoice = this.randomValue();
//        }
//        if (this.signalVoice > 0.7) {
//            return 1.0;
//        } else if (this.signalVoice > 0.3) {
//            return 0.5;
//        } else {
//            return 0;
//        }
//        //return signalVoice;
//    }
//
//    public void setSignalVoice(double signalVoice) {
//        this.signalVoice = signalVoice;
//    }
//
//    public boolean isAgentActivated() {
//        return agentActivated;
//    }
//
//    public void setAgentActivated(boolean agentActivated) {
//        this.agentActivated = agentActivated;
//    }
//
//    @Override
//    public void processActuatorInformation() {
//        //       {"colorSignal", "signalVoice"};
//        double value[] = this.getActuatorValue();
//        Thing l = ((Thing) World.getInstance().getElement(this.getName()));
//        l.setColorSignalNeuralOutput(value[0]);
//        l.setSignalVoice(value[1]);
//        World.getInstance().changeElement(this.getName(), l);
//
//    }
//    
//
//}
