/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.scenario.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import traffic.scenario.model.Person;
import traffic.scenario.model.Node;
import traffic.scenario.model.World;
import traffic.scenario.model.Edge;

/**
 *
 * @author nathi_000
 */
public class ScenarioGraph {
    //vertices(roads) e arestas(lanes)
    //cada lane tem um ponto de inicio e um de fim
    Map<String, ArrayList<String>> graph;
    //Chego em um vertice a partir de determinadas lanes
    Map<String, ArrayList<Edge>> listV;
    World world;
    ArrayList<Edge> listLane;
    
    
    public ScenarioGraph(){
        world = World.getInstance();
        listLane = new ArrayList<>();
    }
    public void createGraph() {
   //         world = World.getInstance();
            graph = new HashMap<>();
            listV = new HashMap<>();
            System.out.println("Creating graph..");
            this.makeGraph();
            System.out.println("Graph made");
            this.world.resetSimulation();
            this.createPaths();
            this.putCars();
            if(this.world.isSortBrokenGlass())
                    this.world.sortNodesWithBrokenGlass();
            
            System.out.println("Cars added");
    }
    
    private void createPaths(){
        for(String departu: world.getOrigins()){
            for(String tar: world.getTargets())
            {
                getBestRouteBFS(departu, tar);
            }
        }
            
    }

    private void makeGraph() {
        for(int i = 0; i< world.getNumRoads(); i++){
            Node road = (Node) world.getElement("node"+i);
            ArrayList<String> arestas = new ArrayList<>();
            for(int c = 0; c<world.getNumLanes(); c++){
                Edge lane = (Edge) world.getElement("edge"+c);
                if(lane.getBegin().equals("node"+i)){
                    arestas.add("edge"+c);
                }
            }
            this.graph.put("node"+i, arestas);
        }

    }
    
    /*O MELHOR CAMINHO NAO DEVE FICAR ARMAZENADO NO CARRO,MAS ALGO DO CENARIO..
     POR ENQUANTO ESTÁ ARMAZENADO NO CARRO*/
    public void putCars(){
      //  System.out.println("Botando carros");
        
        for(int i = 0; i< world.getNumAmbulances(); i++){
            this.putCar(i,true);
        }
        for(int i = world.getNumAmbulances(); i< world.getNumPeople(); i++){
            this.putCar(i, false);
        }
    }
    
    public void putCar(int i, boolean isAmbulance){
        Person car = new Person(String.valueOf(i));
       //     System.out.println("aqui - antes");
            car.setIsAmbulance(isAmbulance);
            car.setNodeEnd(this.genRandomTarget());
            car.setNodeBegin(this.genRandomBegin(car.getNodeEnd()));
       //     System.out.println("Aqui - depois");
            car.setCurrentNode(car.getNodeBegin());
            
            car.setTimeBegin(0.0);
            car.setTimeTravel(0.0);
//            System.out.println("Person: "+ i);
//            System.out.println("Origin "+ car.getRoadBegin());
//            System.out.println("Dest "+car.getRoadEnd());
            car.setRoute(this.getBestRouteBFS(car.getNodeBegin(), car.getNodeEnd()));
            
            world.addPersonNode(car.getNodeBegin(), car);
//            String laneAfterName = this.world.getPerson(car.getId()).getCurrentEdge();
//            Edge laneAfter = (Edge) this.world.getElement(laneAfterName);
//            laneAfter.incrementNumCar();
//            this.world.changeElement(laneAfterName, laneAfter);
    }
    
    public void reinitPerson(int id){
        Person car = this.world.getPerson(String.valueOf(id));
        boolean isAmbulance = car.isIsAmbulance();
        String roadEnd = car.getNodeEnd();
        Node roE = (Node)this.world.getElement(roadEnd);
        roE.removeCar(String.valueOf(id));
        this.world.changeElement(roadEnd, roE);
        
        String laneEnd = car.getCurrentEdge();
        Edge laE = (Edge) this.world.getElement(laneEnd);
        laE.removePerson(String.valueOf(id));
        this.world.changeElement(laneEnd, laE);
        
        this.putCar(id, isAmbulance);

        //car.setRoadEnd(this.genRandomTarget());
       // car.setRoadBegin(this.genRandomBegin(car.getRoadEnd()));
        //car.setTimeBegin(0.0);
        //car.setTimeTravel(0.0);
        //car.setRoute(this.getBestRouteBFS(car.getRoadBegin(), car.getRoadEnd()));
        //this.world.changeRoadCar(roadEnd, car.getRoadBegin(), String.valueOf(id));
        //this.world.getPerson(String.valueOf(id)).incrementPosRoute();
    }
    
    public String getNextRoad(String carId){
        Person car = world.getPerson(carId);
        Node currentNode = (Node) world.getElement(car.getCurrentNode());
        String nameDest = car.getNextNodeInRoute();
        Node nodeDest = (Node) world.getElement(nameDest);
        
        String actualLaneName = car.getCurrentEdge();
        Edge currentEdge = ((Edge)world.getElement(actualLaneName));
        String nextLaneName = car.getNextEdge();
        Edge nextEdge = ((Edge)world.getElement(nextLaneName));


//        if(roadDest==null ||roadDest.getSignal(car.getCurrentEdge())!=0){
//Se a PROXIMA rua estiver escura, o destino vai ser o mesmo
        if(nodeDest==null ||currentEdge.getLightIntensity()==0||nextEdge.getLightIntensity()==0){
//Se a rua estiver escura, o destino vai ser o mesmo
        //if(nodeDest==null ||currentEdge.getLightIntensity()==0){
        //if(nodeDest==null ){
            return currentNode.getName();
        }
        
        if(nameDest.equals(car.getNodeEnd())){
            return nameDest;
        }
            
        //NESSE CASO, TERIA QUE RECALCULAR A ROTA, MAS ENFIM...
//        if(roadDest.getRatePeople(car.getNextEdge())>=1.0)
        if(nextEdge.getRatePeople()>=1.0){
            return currentNode.getName();
        }
//        car.decrementPosRoute();
        return nameDest;
    }
    
    private String genRandomBegin(String roadEnd){
        world = World.getInstance();
        boolean selected = false;
        
        List<String> origins = new ArrayList<>();
        origins.addAll(this.world.getOrigins());
        
//        origins.add("road4");
//        origins.add("road5");
//        origins.add("road6");
//        origins.add("road7");
        int random = 0;
        while(!selected){
        random = (int)(Math.round((Math.random() * (origins.size()-1))));
        Node road = (Node) this.world.getElement(origins.get(random));
        if(road!=null){
        String[] path = this.getBestRouteBFS(road.getName(), roadEnd);
        String laneName = this.world.getLaneByRoads(path[path.length-1], path[path.length-2]).getName();
        Edge lane = (Edge) this.world.getElement(laneName);
//        if(road.getRatePeople(laneName)<1.0){
         if(lane.getRatePeople()<1.0){
            selected = true;
        }
        }
        }
        return origins.get(random);
    }
    
        private String genRandomTarget(){
        List<String> target = new ArrayList<>();
        target.addAll(this.world.getTargets());
        int random = (int)(Math.round( Math.random()* (target.size()-1)));
        return target.get(random);
    }
    
        
//     private String[] getRouteInLanes(String[] routeInRoads){
//         for(int cont = 0; cont< routInRoads.)
//     }
//     
    //Retornar o menor caminho até um vértice, sem considerar
        //o peso das arestas
     private String[] getBestRouteBFS(String roadBegin, String roadEnd){
       //Soh pra guardar todos os elementos por nivel e poder testar
         
         if(this.world.containsBestRoute((roadBegin+roadEnd)))
             return this.world.getBestRoute((roadBegin+roadEnd));
        Map<Integer, ArrayList<String>> verticesPorNivel = new HashMap<>();
        
        Map<String, Integer> visitadosBfs = new HashMap<>();  
        int nivel = 0;
        List<String> listAux = new ArrayList<>();
        Map<String, String> previousPerNo = new HashMap<>();
     
        String no = roadBegin;  
        visitadosBfs.put(no,nivel);
        previousPerNo.put(no, no);
        listAux.add(no);
        Date inicio = new Date(System.currentTimeMillis());
           while(!listAux.isEmpty()){
               String noAux = listAux.remove(0);
               List<String> adj = this.graph.get(noAux);
               for(String vertice: adj){
                   Edge lane = (Edge)world.getElement(vertice);
                   String noadj = lane.getEnd();
                   
                  if(visitadosBfs.get(noadj)==null)
                    {
                        nivel = visitadosBfs.get(noAux)+1;
                        visitadosBfs.put(noadj,nivel);
                        listAux.add(noadj);
                        previousPerNo.put(noadj, noAux);
                       //posso deletar isso aqui depois.. 
                        if(verticesPorNivel.get(nivel)==null){
                            ArrayList<String> vert = new ArrayList<>();
                            vert.add(noadj);
                            verticesPorNivel.put(nivel, vert);
                        }
                        else{
                            verticesPorNivel.get(nivel).add(noadj);
                        }
                    }
               }
           }
               String noPrevious = roadEnd;
               String line = noPrevious;
               while(!noPrevious.equals(no)){
               // System.out.println(noPrevious); 
                noPrevious = previousPerNo.get(noPrevious);
                line += "," + noPrevious;
               }
               String[] caminho = line.split(","); 
               this.world.addBestRoute((roadBegin+roadEnd), caminho);
           return caminho;
                   
//           File f;
//        f = new File("distancias.txt");
//        try {
//            FileWriter w = new FileWriter(f);
//            BufferedWriter bw = new BufferedWriter(w);
//            PrintWriter wr = new PrintWriter(bw);
//            wr.append("INICIO: " + inicio + "\r\n");
//            wr.append("FIM: " + new Date(System.currentTimeMillis()) + "\r\n");
//            //imprimindo todos os nós
//            List<Integer> listNiveis = new ArrayList<>();
//            listNiveis.addAll(verticesPorNivel.keySet());
//          
//           for(Integer n: listNiveis){
//               // System.out.println("Distância: "+n );
//               wr.append("DISTÂNCIA:  " +n + "\r\n");
//                for(String s: verticesPorNivel.get(n) ){
//                    wr.append(s+"\r\n");
//                }
//                wr.append("\r\n");
//                
//           }
//           
//               wr.append("Caminho-------------------------------\n\n");
//               wr.append("RoadEnd: "+roadEnd+"\r\n");
//               String noPrevious = roadEnd;
//               String line = noPrevious;
//               while(!noPrevious.equals(no)){
//               // System.out.println(noPrevious); 
//                noPrevious = previousPerNo.get(noPrevious);
//                line += ", " + noPrevious;
//                
//               }
//               wr.append(line+"\r\n");
//              // System.out.println(noPrevious);
//
//            wr.close();
//            bw.close();
//        } catch (IOException e) {
//            System.out.println("Error" + e);
//        }
 /*         
        */   


        //}
    }
    /*
         public void setBeginEndLanes(){
        ArrayList<Lane> lanes = world.getAllEdgesList();
        Map<String, Node> roads = world.getAllRoads();
        for(Edge lane: lanes){
            boolean pass = false;
            while(!pass){
            int i = (int)(Math.random() * world.getNumRoads()) ;
            if(roads.containsKey("road"+i)){
                if(!listV.containsKey("road"+i)||(listV.containsKey("road"+i) && listV.get("road"+i).size()<4)){
                    if(!listV.containsKey("road"+i)){
                        listV.put("road"+i, new ArrayList<Lane>());
                    }
                    lane.setEnd(roads.get("road"+i));
                    //através de quais lanes chego nesse vertice
                    listV.get("road"+i).add(lane);
                    lanes.add(lane);
                    pass = true;
                }
            }
            }
        }
        
    }
     */
}
