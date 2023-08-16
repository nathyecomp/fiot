/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.scenario.model;


/**
 *
 * @author nathi_000
 */
public class Person extends Element{
    double velocity;
    double maxVelocity;
    String nodeBegin;
    String nodeEnd;
    String currentNode;
    String id;
    double timeBegin = 0.0;
    double timeTravel = 0.0;
    int tamRoute = 0;
    int posRoute = 0;
    String[] route;
    boolean isAmbulance = false;
    public Person(String id){
       // super.type = "Person";
        this.id = id;
    }

    public String[] getRoute() {
        return route;
    }

    public void setRoute(String[] route) {
        this.route = route;
        this.posRoute  = route.length-1;
        this.tamRoute = route.length;
    }
    
    
    //são pra ajudar no vetor caminho[]
    public void setTamRoute(int tam){
        this.tamRoute = tam;
    }
    
    public int getPosRoute(){
        return this.posRoute;
    }

    public boolean isIsAmbulance() {
        return isAmbulance;
    }

    public void setIsAmbulance(boolean isAmbulance) {
        this.isAmbulance = isAmbulance;
    }
    
    public String getCurrentEdge(){
        String laneName = "";
        if((this.posRoute)<=0){
            String road1 = this.route[1];
            String road2 = this.route[0];
            laneName= World.getInstance().getLaneByRoads(road1, road2).getName();
        }
        else{
        String road1 = this.route[this.posRoute];
        String road2 = this.route[(this.posRoute-1)];
        laneName= World.getInstance().getLaneByRoads(road1, road2).getName();
        }
        return laneName;
    }
    
    public String getNextEdge(){
        String laneName = "";
        if((this.posRoute)<=1){
            String road1 = this.route[1];
            String road2 = this.route[0];
            laneName= World.getInstance().getLaneByRoads(road1, road2).getName();
        }
        else{
        String road1 = this.route[this.posRoute-1];
        String road2 = this.route[(this.posRoute-2)];
        laneName= World.getInstance().getLaneByRoads(road1, road2).getName();
        }
        return laneName;
    }
    
    public String getNextNodeInRoute(){
        if(this.posRoute<=0)
            return this.route[0];
        return this.route[(this.posRoute-1)];
    }
    public void decrementPosRoute(){
        this.posRoute--;
    }
    
    public void incrementPosRoute(){
        this.posRoute++;
    }

    public double getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(double timeBegin) {
        this.timeBegin = timeBegin;
    }

    public double getTimeTravel() {
        return timeTravel;
    }

    public void setTimeTravel(double timeTravel) {
        this.timeTravel = timeTravel;
    }

    
    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public String getNodeBegin() {
        return nodeBegin;
    }

    public void setNodeBegin(String nodeBegin) {
        this.nodeBegin = nodeBegin;
    }

    public String getNodeEnd() {
        return nodeEnd;
    }

    public void setNodeEnd(String nodeEnd) {
        this.nodeEnd = nodeEnd;
    }

    public String getCurrentNode() {
         return this.route[this.posRoute];
      //  return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String readValuesFromInputSensors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processActuatorInformation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
