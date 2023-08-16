/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package traffic.scenario.gui;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import traffic.scenario.model.Edge;
import traffic.scenario.model.Node;
import traffic.scenario.model.StreetLight;
import traffic.scenario.model.World;
//import traffic.scenario.model.old.Edge;
//import traffic.scenario.model.old.Node;
/**
 *
 * @author Nathalia
 */
public class MapFile {
    
    File file;
    ArrayList<Point2D> listPoints;
    ArrayList<Node> listNodes;
    ArrayList<Edge> listEdges;
    private ArrayList<Line2D.Double> listLines;
    List<String> origins;
    ArrayList<Point2D> originsPoints;
    List<String> target ;
    ArrayList<Point2D> targetPoints;
   // ArrayList<StreetLight> listThings;
    int numEdges;
    int numNodes;
    int numDepartureNodes;
    int numTargetNodes;
    int numThings;
    File mapFile;
    World world = World.getInstance();
    public MapFile(String nameFile){
      file = new File(nameFile);  
      this.init();
    }
    public MapFile(File file){
        this.file = file;
        this.init();
    }
    private void init(){
        world = World.getInstance();
        this.listPoints = new ArrayList<>();
        this.listLines = new ArrayList<>();
        this.listEdges = new ArrayList<>();
        this.listNodes = new ArrayList<>();
        this.origins= new ArrayList<>();
        this.target= new ArrayList<>();
        this.originsPoints = new ArrayList<>();
        this.targetPoints = new ArrayList<>();
       // this.listThings = new ArrayList<>();
                
        numEdges = 0;
        numNodes = 0;
        numDepartureNodes = 0;
        numTargetNodes = 0;
        numThings = 0;
        
        
    }
    
    public void setMapInformations() throws FileNotFoundException, IOException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while ( ( line = bufferedReader.readLine() ) != null) {
            line = line.replaceAll(" ", "");
            if(line.contains("<File>")){
                String line2 = bufferedReader.readLine().replaceAll(" ", "");
                this.mapFile = new File(line2);
            }
            else if(line.contains("<Points>")){
                    this.numNodes = Integer.valueOf(line.split(":")[1]);
                    world.setNumRoads(numNodes);
                   // System.out.println("num road "+ this.numRoads);
                    for(int cont = 0; cont< this.numNodes; cont++){
                        String line2 = bufferedReader.readLine().replaceAll(" ", "");
                       // System.out.println("Point line "+cont+":  "+ line2);
                        String[] coordinate = line2.split(";");
                        double x = Double.valueOf(coordinate[0]);
                        double y = Double.valueOf(coordinate[1]);
                        Point2D point = new Point2D.Double(x,y);
                        this.listPoints.add(cont, point);
                        Node node = new Node(("node"+cont),point );
                        this.listNodes.add(cont,node);
                        
                        
                        //adicionando lampadas pra cada Node
                       // System.out.println("Adicionando street light to node "+ node.getName());
                        StreetLight light = new StreetLight(("streetlight"+cont), node.getName());
                        world.streetWorld.addNewLight(node.getName(), light);
                     //   System.out.println("Added Light ");
                      //  System.out.println(world.streetWorld.getStreetLight(node.getName()).getName());
                    }                
            }
            else if(line.contains("<Lanes>")){
                this.numEdges = Integer.valueOf(line.split(":")[1]);
                world.setNumLanes(numEdges);
                for(int cont = 0; cont< this.numEdges; cont++){
                        String[] nodes = bufferedReader.readLine().replaceAll(" ", "").split(";");
                        String roadBegin = "node"+nodes[0];
                        String roadEnd = "node"+nodes[1];
                        Point2D p1 = this.listPoints.get(Integer.valueOf(nodes[0]));
                        Point2D p2 = this.listPoints.get(Integer.valueOf(nodes[1]));
                        Line2D.Double lane = new Line2D.Double(p1, p2);
                        this.listLines.add(lane);
//                        Edge lLane = new Edge("lane"+cont, roadBegin, roadEnd);
//                        this.listLanes.add(lLane);
                        world.setBeginAndLane("edge"+cont, listNodes.get(Integer.valueOf(nodes[0])), listNodes.get(Integer.valueOf(nodes[1])));
                        
                }   
            }
            else if(line.contains("<DeparturePoints>")){
                this.numDepartureNodes = Integer.valueOf(line.split(":")[1]);
                world.setNumDepartureRoads(numDepartureNodes);
                for(int cont = 0; cont< this.numDepartureNodes; cont++){
                    String origin = bufferedReader.readLine().replaceAll(" ", "");
                    this.world.addOrigin("node"+origin);
                    Point2D p1 = this.listPoints.get(Integer.valueOf(origin));
                    this.originsPoints.add(p1);
                }
            }
            else if(line.contains("<TargetPoints>")){
                this.numTargetNodes = Integer.valueOf(line.split(":")[1]);
                world.setNumTargetRoads(numTargetNodes);
                for(int cont = 0; cont< this.numTargetNodes; cont++){
                    String dest = bufferedReader.readLine().replaceAll(" ", "");
                    this.world.addTarget("node"+dest);
                    Point2D p1 = this.listPoints.get(Integer.valueOf(dest));
                    this.targetPoints.add(p1);
                }
            }
            /*else if(line.contains("<StreetLights>")){
                this.numThings = Integer.valueOf(line.split(":")[1]);
                    traffic.setNumThings(numThings);
                    for(int cont = 0; cont< this.numThings; cont++){
                        String nameNode = bufferedReader.readLine().replaceAll(" ", "");
                       // System.out.println("Point line "+cont+":  "+ line2);
                      //  String[] coordinate = line2.split(";");
                       // double x = Double.valueOf(coordinate[0]);
                       // double y = Double.valueOf(coordinate[1]);
                       // Point2D point = new Point2D.Double(x,y);
                       // this.listPoints.add(point);
                      //  Thing thing = new Thing(("thing"+cont),point);
                      //  this.listThings.add(thing);
                    }
            }*/
            //System.out.println(line);
        }
 
        //liberamos o fluxo dos objetos 
        // ou fechamos o arquivo
        fileReader.close();
        bufferedReader.close();
    }

    public ArrayList<Point2D> getListPoints() {
        return listPoints;
    }

    public ArrayList<Node> getListNodes() {
        return listNodes;
    }

    public ArrayList<Edge> getListEdges() {
        return listEdges;
    }

    public ArrayList<Line2D.Double> getListLines() {
        return listLines;
    }

    public List<String> getOrigins() {
        return origins;
    }

    public List<String> getTarget() {
        return target;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public int getNumDepartureNodes() {
        return numDepartureNodes;
    }

    public int getNumTargetNodes() {
        return numTargetNodes;
    }

    public File getMapFile() {
        return mapFile;
    }

    public ArrayList<Point2D> getOriginsPoints() {
        return originsPoints;
    }

    public ArrayList<Point2D> getTargetPoints() {
        return targetPoints;
    }
    
    
}
