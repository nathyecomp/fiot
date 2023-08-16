/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.scenario.model;

import traffic.scenario.model.Edge;
import traffic.scenario.model.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import traffic.scenario.gui.MainPanel;

/**
 *
 * @author nathi_000
 */
public class World {

    //to synchronize agents
    //boolean readInput = false;
    MainPanel panel;
    boolean readOutput = true;
    boolean readOutput2 = true;
    //  int numReadingInput = 0;
    //If the agent receives 
    int numReadingOutput = 0;
    int numOfAgents = 0;

    int numberElements = 0;
    int numRoads = 0;
    int numLanes = 0;
    int numThings = 0;
    int numPeople = 0;
    int numAmbulances = 0;
    int numDepartureRoads = 0;
    int numTargetRoads = 0;
    int maximumNumCarPerVia = 40;
    int maximumNumAmbulancePerVia = 4;
    int brokenGlass = 0;
    //pra testar sem a rede neural
    boolean simulationNotIA = true;
    boolean finishSimulation = false;
    int timeDefaultSignal = 1;
    int actualTimeSimulation = 0;
    public int timeSimulation = 0;
    public int numberSimulationToChangeLight = 1; //ja tive a primeira execucao quando chamo ele aqui. So to chamando no reinit
    public int numOfPeopleCompletedAfterSimulation = 0;
    public int numOfAmbulancesCompletedAfterSimulation = 0;
    double totalTimeTrip = 0;
    double totalEnergy = 0;
    private static World instance;
    //Relação dos agentes  com os elementos mapeados no cenário
    Map<String, Element> elementsList;
    Map<String, Edge> laneList;
   // Map<String, StreetLight> streetLightByNode;
    Map<String, Node> roadList;
    Map<String,Integer> peopleByEdge;
    Map<String, Person> listPeople;
    Map<String, Person> listPeopleCopy;
    Map<String, String[]> bestRoutes;
    List<String> origins;
    List<String> targets;
    
    List<String> nodesWithBrokenGlass;
    double[] listSignal;

    boolean semaphoreForListCar = true;
    
    boolean sortBrokenGlass = false;
    
    public StreetWorld streetWorld;
    // Construtor privado. Suprime o construtor público padrao.
    private World() {
          // Operações de inicialização da classe
        this.init();
        //   this.start();
    }

    private void init() {
        
        readOutput = false;
  //  int numReadingInput = 0;
        //If the agent receives 
        numReadingOutput = 0;
        numOfAgents = 0;

        numberElements = 0;
        numRoads = 0;
        numLanes = 0;
        numThings = 0;
        numPeople = 0;
        numAmbulances = 0;
        numDepartureRoads = 0;
        numTargetRoads = 0;
        maximumNumCarPerVia = 40;
        //pra testar sem a rede neural
        simulationNotIA = true;
        finishSimulation = false;
        timeDefaultSignal = 1;
        actualTimeSimulation = 0;
        timeSimulation = 0;
        numberSimulationToChangeLight = 1;
        numOfPeopleCompletedAfterSimulation = 0;
        numOfAmbulancesCompletedAfterSimulation = 0;
        totalTimeTrip = 0;
        totalEnergy = 0;
        elementsList = new HashMap<>();
        laneList = new HashMap<>();
       // streetLightByNode = new HashMap<>();
        roadList = new HashMap<>();
        listPeople = new HashMap<>();
        listPeopleCopy = new HashMap<>();
        bestRoutes = new HashMap<>();
        origins = new ArrayList<>();
        targets = new ArrayList<>();
        peopleByEdge = new HashMap<>();
        
        nodesWithBrokenGlass = new ArrayList<>();
        
        streetWorld = StreetWorld.getInstance();
    }

    public MainPanel getPanel() {
        return panel;
    }

    public void setPanel(MainPanel panel) {
        this.panel = panel;
    }

    public boolean isSortBrokenGlass() {
        return sortBrokenGlass;
    }

    public void setSortBrokenGlass(boolean sortBrokenGlass) {
        this.sortBrokenGlass = sortBrokenGlass;
    }
    
    
    public boolean isSemaphoreForListCar() {
        return semaphoreForListCar;
    }

    public void setSemaphoreForListCar(boolean semaphoreForListCar) {
        this.semaphoreForListCar = semaphoreForListCar;
    }
    
    

    public void reset() {
        this.init();
    }
    
    public void resetSimulation() {
        actualTimeSimulation = 0;
     //   numOfCarsCompletedAfterSimulation = 0;
        List<Person> list = new ArrayList<>(listPeople.values());
        for (Person car : list) {
            this.removePerson(car.getId());
        }
        this.listPeople.clear();
        //  List<Car> list = new ArrayList<>(listPeopleCopy.values());
        this.listPeopleCopy.clear();
        
        if(this.isSortBrokenGlass())
            this.nodesWithBrokenGlass.clear();
    }
    // Método público estático de acesso único ao objeto!
    public static World getInstance() {
        // Verifica se a variável possui algum valor,caso não, é criada a instancia.
        if (instance == null) {
            instance = new World();
        }
        // Se a variavel possui algum valor, é retornado para quem está pedindo
        return instance;
    }

//    public boolean isReadInput() {
//        return readInput;
//    }
//
//    public void setReadInput(boolean readInput) {
//        this.readInput = readInput;
//    }
    public int getTimeSimulation() {
        return timeSimulation;
    }

    public void setTimeSimulation(int timeSimulation) {
        this.timeSimulation = timeSimulation;
    }

    public boolean isReadOutput() {
        return readOutput;
    }

    public void setReadOutput(boolean readOutput) {
        this.readOutput = readOutput;
        this.readOutput2=!readOutput;
    }

    public boolean isReadOutput2() {
        return readOutput2;
    }

    public void setReadOutput2(boolean readOutput2) {
        this.readOutput2 = readOutput2;
    }
    
    

    public int getNumOfAgents() {
        return numOfAgents;
    }

    public void setNumOfAgents(int numOfAgents) {
        this.numOfAgents = numOfAgents;
    }

    public void incrementNumReadingOutput() {
        this.numReadingOutput++;
        if (this.numReadingOutput >= this.numOfAgents) {
            this.setReadOutput(false);
            this.numReadingOutput = 0;
        }
//        else{
//            this.readOutput = true;
//        }
    }

    public boolean isFinishSimulation() {
        return finishSimulation;
    }

    public void setFinishSimulation(boolean finishSimulation) {
        this.finishSimulation = finishSimulation;
    }

    public boolean isSimulationNotIA() {
        return simulationNotIA;
    }

    public void setSimulationNotIA(boolean simulationNotIA) {
        this.simulationNotIA = simulationNotIA;
    }

    public int getActualTimeSimulation() {
        return actualTimeSimulation;
    }

    public void setActualTimeSimulation(int actualTimeSimulation) {
        this.actualTimeSimulation = actualTimeSimulation;
    }

    public void incrementActualTimeSimulation() {
        this.actualTimeSimulation++;
        if (this.getActualTimeSimulation() % this.getTimeDefaultSignal() == 0) {
//            this.setReadOutput(true);
        }
    }

    public int getNumberSimulationToChangeLight() {
        return numberSimulationToChangeLight;
    }

    public void setNumberSimulationToChangeLight(int numberSimulationToChangeLight) {
        this.numberSimulationToChangeLight = numberSimulationToChangeLight;
    }
    
    

    public int getTimeDefaultSignal() {
        return timeDefaultSignal;
    }

    public void setTimeDefaultSignal(int timeDefaultSignal) {
        this.timeDefaultSignal = timeDefaultSignal;
    }

    public boolean containsBestRoute(String adj) {
        return this.bestRoutes.containsKey(adj);
    }

    public void addBestRoute(String adj, String[] route) {
        this.bestRoutes.put(adj, route);
    }

    public String[] getBestRoute(String adj) {
        return this.bestRoutes.get(adj);
    }

    public int getMaximumNumPeoplePerVia() {
        return maximumNumCarPerVia;
    }

    public void setMaximumNumCarPerVia(int maximumNumCarPerVia) {
        this.maximumNumCarPerVia = maximumNumCarPerVia;
    }

    public int getMaximumNumAmbulancePerVia() {
        return maximumNumAmbulancePerVia;
    }

    public void setMaximumNumAmbulancePerVia(int maximumNumAmbulancePerVia) {
        this.maximumNumAmbulancePerVia = maximumNumAmbulancePerVia;
    }

    public int getBrokenGlass() {
        return brokenGlass;
    }

    public void setBrokenGlass(int brokenGlass) {
        this.brokenGlass = brokenGlass;
    }
    
    

    


    public boolean addPersonNode(String road, Person car) {
        Node roadActual = (Node) this.elementsList.get(road);
        car.setCurrentNode(road);
        roadActual.addCar(car);
        this.changeElement(road, roadActual);
        
        String laneActualName = car.getCurrentEdge();
        Edge laneActual = (Edge) this.getElement(laneActualName);
        laneActual.addPerson(car);
        this.changeElement(laneActualName, laneActual);
        
        this.listPeople.put(car.id, car);
        this.listPeopleCopy.put(car.id, car);
        return true;
    }

    public boolean changeNodePerson(String roadOrigin, String roadTarget, String personId) {
        Node roadDest = (Node) this.elementsList.get(roadTarget);
        Node roadBegin = (Node) this.elementsList.get(roadOrigin);
        Person car = this.listPeople.get(personId);
        
        String actualLaneName = car.getCurrentEdge();
        Edge currentEdge = ((Edge)this.elementsList.get(actualLaneName));
        String nextLaneName = car.getNextEdge();
        Edge nextEdge = ((Edge)this.elementsList.get(nextLaneName));
        
       // if (roadDest == null || nextEdge.getLightIntensity()==0 ||
        if (roadDest == null || currentEdge.getLightIntensity()==0 || nextEdge.getLightIntensity()==0 ||
       // if (roadDest == null  ||
                ((car.getCurrentEdge() == null ? car.getNextEdge() != null : !car.getCurrentEdge().equals(car.getNextEdge()))&&(nextEdge.getRatePeople()>= 1.0))) {
           // this.listPeople.put(carId, car);
            return false;
        }

      //  String laneBeforeName = car.getCurrentEdge();
//         if(carId.equals("20")){
//             Edge laneBefore = (Edge) this.elementsList.get(laneBeforeName);
//              System.out.println("Edge Before: "+ laneBefore.getBegin()+"-> "+laneBefore.getEnd()+": " +laneBefore.getNumCar());
//         }
//         
        car.setCurrentNode(roadTarget);
        roadBegin.removeCar(personId);
        roadDest.addCar(car);
        this.changeElement(roadOrigin, roadBegin);
        this.changeElement(roadTarget, roadDest);
        
        currentEdge.removePerson(personId);
        nextEdge.addPerson(car);
        this.changeElement(nextLaneName, nextEdge);
        this.changeElement(actualLaneName, currentEdge);
        
        car.decrementPosRoute();
        this.listPeople.put(personId, car);

//        car = this.listPeople.get(carId);
//        String laneAfterName = car.getCurrentEdge();
//        Edge laneAfter = (Edge) this.elementsList.get(laneAfterName);
//        laneAfter.incrementNumCar();
        // System.out.println("");
//        Edge laneBefore = (Edge) this.elementsList.get(laneBeforeName);
//        laneBefore.decrementNumCar();
//        this.changeElement(laneBeforeName, laneBefore);
//        this.changeElement(laneAfterName, laneAfter);
      //   System.out.println("Passando aqui"+ carId);
        //if(carId.equals("20")){
        //System.out.println("Node Begin: "+ nodeBegin.getName()+ ": "+nodeBegin.getNumCar());
        //System.out.println("Node Dest: "+ roadDest.getName()+": "+ roadDest.getNumCar());
        //System.out.println("Edge Before: "+ laneBefore.getBegin()+"-> "+laneBefore.getEnd()+": " +laneBefore.getNumCar());
        //System.out.println("Edge After: "+ laneAfter.getBegin()+"-> "+laneAfter.getEnd()+": " +laneAfter.getNumCar());
        //}
        return true;
    }

    public void addElement(String nameElement, Element e) {
        elementsList.put(nameElement, e);
        //System.out.println("Adicionando "+ nameAgentRoad);
    }

    public void changeElement(String nameAgentRoad, Element e) {
//        if (elementsList.containsKey(nameAgentRoad)) {
//            elementsList.remove(nameAgentRoad);
//        }
        elementsList.put(nameAgentRoad, e);
    }

    public Element getElement(String nameAgentRoad) {
        return elementsList.get(nameAgentRoad);
    }

    public boolean constainsElement(String nameElement) {
        return elementsList.containsKey(nameElement);
    }

    public Map<String, Person> getListPeople() {
      
       return (Map<String, Person>) listPeople;
    }

    public void setListPeople(Map<String, Person> listPeople) {
        this.listPeople = listPeople;
    }

//    public void addPerson(Person car){
//        this.listPeople.put(car.id, car);
//    }
    public Person removePerson(String id) {
        String laneName = this.getPerson(id).getCurrentEdge();
        Person car = this.listPeople.remove(id);
        Edge lane = (Edge) this.getElement(laneName);
//        lane.decrementNumCar();
        this.changeElement(laneName, lane);
        String actual = car.getCurrentNode();
        Node roadActual = (Node) this.elementsList.get(actual);
        roadActual.removeCar(id);
        this.changeElement(actual, roadActual);
        
        lane.removePerson(id);
        this.changeElement(laneName, lane);
        return car;
    }
    
     public void removeConcludedPerson(String id){
        Person car = this.listPeople.remove(id);
        boolean isAmbulance = car.isIsAmbulance();
        String roadEnd = car.getNodeEnd();
        Node roE = (Node)this.getElement(roadEnd);
        roE.removeCar(id);
        this.changeElement(roadEnd, roE);
        
        String laneEnd = car.getCurrentEdge();
        Edge laE = (Edge) this.getElement(laneEnd);
        laE.removePerson(id);
        this.changeElement(laneEnd, laE);
       
    }
    

    public Person getPerson(String id) {
        return this.listPeople.get(id);
    }

    public boolean containsCar(String id) {
        return this.listPeople.containsKey(id);
    }
    
    public void refreshPersonByLane(){  
        for(Edge lane: this.getAllEdgesList()){
            this.peopleByEdge.put(lane.getName(), 0);
        }
        if (!this.listPeople.isEmpty()) {
        List<Person> list = new ArrayList<>(this.listPeople.values());
        for (Person car : list) {
            String laneCar = car.getCurrentEdge();
            int actualN = this.peopleByEdge.get(laneCar);
            actualN++;
//            if(car.getId().equals("0")){
//                System.out.println("LANE DO CAR EHHHHHHHHHHHHHHHHHHHHH "+ laneCar+ "COM QTD "+ actualN);
//            }
            this.peopleByEdge.put(laneCar, actualN);

        }
        }
    }
     public int getNumPeopleOnLane(String laneName) {
       if(!this.peopleByEdge.containsKey(laneName))
           return 0;
       else return this.peopleByEdge.get(laneName);
    }
//     public void setActivation(String nameAgentRoad, double[] activation){
////         if(elementsList.containsKey(nameAgentRoad))
////             System.out.println("O elemento "+nameAgentRoad+ " esta aqui");
//         Element el = elementsList.remove(nameAgentRoad);
//         el.setActivation(activation);
//         elementsList.put(nameAgentRoad, el);
//     }
//     public void setNetInput(String nameAgentRoad, double[] input){
////         if(elementsList.containsKey(nameAgentRoad))
////             System.out.println("O elemento "+nameAgentRoad+ " esta aqui");
//         Element el = elementsList.remove(nameAgentRoad);
//         el.setNetInput(input);
//         elementsList.put(nameAgentRoad, el);
//     }

    private void start() {
        listSignal = new double[this.getNumRoads()];
        for (int i = 0; i < this.getNumRoads(); i++) {
            listSignal[i] = 0.0;
        }
    }
//     public double[] signalColorAllSemaphores(){
//        listSignal = new double[this.getNumRoads()];
//        List<String> listKes = new ArrayList<>(elementsList.keySet());
//         for(String key: listKes){
//             if(key.contains("road")){
//                 int sub = Integer.parseInt(key.substring(4));
//             //    System.out.println("O NOME DO AGENTE EH "+ key);
//                // System.out.println("O INDICE EH "+ sub);
//                 Node road = (Node)elementsList.get(key);
//              //   System.out.println("Size listSignal eh "+ listSignal.length);
//                 listSignal[sub] = road.getSignal();
//             }
//         }
//         return listSignal;
//     }

    public Map<String, Edge> getAllEdges() {
        for (String key : elementsList.keySet()) {
            if (key.contains("edge")) {
                Edge lane = (Edge) elementsList.get(key);
                this.laneList.put(key, lane);
            }
        }
        return this.laneList;
    }

    public ArrayList<Edge> getAllEdgesList() {
        ArrayList<Edge> lanes = new ArrayList<>();
        for (String key : elementsList.keySet()) {
            if (key.contains("edge")) {
                Edge lane = (Edge) elementsList.get(key);
                lanes.add(lane);
            }
        }
        return lanes;
    }

    public Map<String, Node> getAllNodes() {
        for (String key : elementsList.keySet()) {
            if (key.contains("node")) {
                Node road = (Node) elementsList.get(key);
                this.roadList.put(key, road);
            }
        }
        return this.roadList;
    }

    public ArrayList<Node> getAllNodeList() {
        ArrayList<Node> roads = new ArrayList<>();
        for (String key : elementsList.keySet()) {
            if (key.contains("node")) {
                Node road = (Node) elementsList.get(key);
                roads.add(road);
            }
        }
        return roads;
    }

    public void setBeginAndLane(String laneName, Node roadNameBegin, Node roadNameEnd) {
        if (!this.elementsList.containsKey(laneName)) {
            Edge lane = new Edge(laneName);
            this.elementsList.put(laneName, lane);
        }
        if (!this.elementsList.containsKey(roadNameBegin.getName())) {
         //   Node road = new Node(roadNameBegin);
            this.elementsList.put(roadNameBegin.getName(), roadNameBegin);
        }
        if (!this.elementsList.containsKey(roadNameEnd.getName())) {
         //   Node road = new Node(roadNameEnd);
            this.elementsList.put(roadNameEnd.getName(), roadNameEnd);
        }
        Edge lane = (Edge) elementsList.get(laneName);
        lane.setBegin(roadNameBegin.getName());
        lane.setEnd(roadNameEnd.getName());
        changeElement(laneName, lane);

        Node road = (Node) elementsList.get(roadNameEnd.getName());
        road.addEdgeEnd(laneName);
        changeElement(roadNameEnd.getName(), road);

        road = (Node) elementsList.get(roadNameBegin.getName());
        road.addEdgeBegin(laneName);
        changeElement(roadNameBegin.getName(), road);
    }

    public Edge getLaneByRoads(String roadBegin, String roadEnd) {
        Node road = (Node) elementsList.get(roadBegin);
        Edge laneFind = null;

        for (String lane : road.getListEdgeBegin()) {
            laneFind = (Edge) elementsList.get(lane);
            if (laneFind.getEnd().equals(roadEnd)) {
                return laneFind;
            }
        }
        return laneFind;
    }

    public int getNumberElements() {
        return numberElements;
    }

    public void setNumberElements(int numberElements) {
        this.numberElements = numberElements;
    }

    public int getNumRoads() {
        return numRoads;
    }

    public void setNumRoads(int numRoads) {
        this.numRoads = numRoads;
    }

    public int getNumLanes() {
        return numLanes;
    }

    public void setNumLanes(int numLanes) {
        this.numLanes = numLanes;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public int getNumAmbulances() {
        return numAmbulances;
    }

    public void setNumAmbulances(int numAmbulances) {
        this.numAmbulances = numAmbulances;
    }

    public List<String> getOrigins() {
        return origins;
    }

    public void setOrigins(List<String> origins) {
        this.origins = origins;
    }

    public void addOrigin(String origin) {
        if (this.elementsList.containsKey(origin) && !this.origins.contains(origin)) {
            this.origins.add(origin);
        }
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public void addTarget(String target) {
        if (this.elementsList.containsKey(target) && !this.targets.contains(target)) {
            this.targets.add(target);
        }
    }

    public int getNumDepartureRoads() {
        return numDepartureRoads;
    }

    public void setNumDepartureRoads(int numDepartureRoads) {
        this.numDepartureRoads = numDepartureRoads;
    }

    public int getNumTargetRoads() {
        return numTargetRoads;
    }

    public void setNumTargetRoads(int numTargetRoads) {
        this.numTargetRoads = numTargetRoads;
    }

    public int getNumThings() {
        return numThings;
    }

    public void setNumThings(int numThings) {
        this.numThings = numThings;
    }
    
    

    public int getNumOfAmbulancesCompletedAfterSimulation() {
        return numOfAmbulancesCompletedAfterSimulation;
    }

    public void setNumOfAmbulancesCompletedAfterSimulation(int numOfAmbulancesCompletedAfterSimulation) {
        this.numOfAmbulancesCompletedAfterSimulation = numOfAmbulancesCompletedAfterSimulation;
    }


    public int getNumOfPeopleCompletedAfterSimulation() {
        return numOfPeopleCompletedAfterSimulation;
    }
    
   /* public double getPercentConcludedPeople(){
        double percentual =0;
        percentual = (this.getNumOfPeopleCompletedAfterSimulation() * 100)/this.getNumPeople();
      //  this.getNumPeople() - 100
      //this.getNumOfPeopleCompletedAfterSimulation()-x
        return percentual;
    }*/

    public void setNumOfPeopleCompletedAfterSimulation(int numOfPeopleCompletedAfterSimulation) {
        this.numOfPeopleCompletedAfterSimulation = numOfPeopleCompletedAfterSimulation;
    }

    public double getTotalTimeTrip() {
        return totalTimeTrip;
    }

    public void setTotalTimeTrip(double totalTimeTrip) {
        this.totalTimeTrip = totalTimeTrip;
    }

    public double getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(double totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public List<String> getNodesWithBrokenGlass() {
        return nodesWithBrokenGlass;
    }

    public boolean addNodeWithBrokenGlass(String nodeName) {
        if(this.nodesWithBrokenGlass.contains(nodeName)||nodeName.equals("-1"))
            return false;
        else{
            this.nodesWithBrokenGlass.add(nodeName);
            return true;
        }
        
    }
    
    public boolean brokenGlassListContains(String nodeName){
        return this.nodesWithBrokenGlass.contains(nodeName);
    }
    
    public void sortNodesWithBrokenGlass(){
        int numNodesToBroke = (int)(this.brokenGlass * this.numRoads)/100;
        String nodeName = "-1";
        for (int c =0; c< numNodesToBroke; c++){
            while (!this.addNodeWithBrokenGlass(nodeName)|| nodeName.equals("-1")){
                int randomN = World.getRandomNumberInRange(0, (this.numRoads-1));
                nodeName = "node"+randomN;
            }
        }
    }
    
    private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
    }
    
    public String getBrokenLightsString(){
        String broken = "";
        for(String br: this.nodesWithBrokenGlass){
            broken+=(";"+br);
        }
        return broken;
    }
    
    

}
