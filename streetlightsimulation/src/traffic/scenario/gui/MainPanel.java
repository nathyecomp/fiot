/*
 * MainPanel.java
 *
 * Created on 23 de Janeiro de 2010, 15:17
 */
package traffic.scenario.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.event.MouseInputListener;
import simulateddevices.DeviceAgent;
import simulateddevices.InitAgent;
import simulateddevices.LogDeviceAgent;
import traffic.scenario.model.Person;
import traffic.scenario.model.Edge;
import traffic.scenario.model.Node;
import traffic.scenario.model.StreetLight;
import traffic.scenario.model.World;

/**
 * @version 2.0
 * @author nathy - i.a.
 */
public abstract class MainPanel extends javax.swing.JPanel implements MouseInputListener {

    private final float proporcao = 0.5f;
    private ArrayList<Point2D> cidades = new ArrayList<Point2D>();
    private ArrayList<Point2D> departures = new ArrayList<Point2D>();
    private ArrayList<Point2D> targets = new ArrayList<Point2D>();
    
    //a lista mais atual de nodes esta em Word por causa da geracao de edges
    //private ArrayList<Node> nodes = new ArrayList<Node>();
    //private ArrayList<Point2D> things = new ArrayList<Point2D>();
    private String pathMap = "C:/wazeFSA.jpg";
    private String pathGraph = "";
    MapFile mapControl;
    ConfigurationFile configurationControl;
    World world = World.getInstance();
//    String path = "C:/wazeFSA.jpg";
    ScenarioGraph graph;
    boolean drawPeople = false;
    Map<String, Person> listPeople = null;
    int peopleCompleted = 0;
    int ambulanceCompleted = 0;
    double totalTimeTrip = 0;
    double totalEnergy = 0;
    String personSelected = "";
    String routePersonSelected = "";
    String edgePersonSel = "";
    
    boolean execute= true;

    boolean draw =false;
    
    DeviceAgent agentOnlyOne;
    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }
    
    

    public ArrayList<Point2D> getCidades() {
        return cidades;
    }

    public String getPersonSelected() {
        return personSelected;
    }

  /*  public ArrayList<Point2D> getThings() {
        return things;
    }

    public void setThings(ArrayList<Point2D> things) {
        this.things = things;
    }
*/    
    

    public void setPersonSelected(String personSelected) {
        this.personSelected = personSelected;

        Person car = this.world.getPerson(this.personSelected);
        String[] route = car.getRoute();
        this.routePersonSelected = "";
        for (String route1 : route) {
            System.out.println(route1 + "->");
            this.routePersonSelected += (route1 + "->");
        }
        repaint();
    }

    public void controlConfiguration(String adressfile) throws IOException {
      //   System.out.println("Entrei em ControlConfiguration");
        configurationControl = new ConfigurationFile(adressfile);
        configurationControl.setConfigurationInformations();
      //  System.out.println("Informações do arquivo processadas");
        graph = new ScenarioGraph();
        graph.createGraph();

        if (world.getActualTimeSimulation() == 0) {
        //     System.out.println("Time simulation = 0");
            List<Node> nodeList = new ArrayList<>(world.getAllNodeList());
            
         //   System.out.println("SIZE DE NODELIST FROM WORLD "+ nodeList.size());
            
         //    System.out.println("imprimindo nodes na linha 128 controlConfiguration");
       // for(Node node: nodeList){
        //    System.out.println(node.getName());
         //   System.out.println("X "+ node.getXy().getX()+"Y "+ node.getXy().getY());
        //}

            
          //   System.out.println("Size node list "+ nodeList.size());
            //MapFunctions func = new MapFunctions(this.cidades);
            MapFunctions2 func = new MapFunctions2( (ArrayList<Node>) nodeList);
          //  int cont = 0;
//            InitAgent.init(new ControlAgent("CONTROLTRAFFIC", "laneContainer"),
//                    "CONTROLTRAFFIC", "laneContainer");
           // ArrayList<String> listLaneAgents = new ArrayList<>();
            Node node01 = null;
            System.out.println("imprimindo em controlconfiguration");
           for(int c = 0; c<nodeList.size(); c++){
               Node node = nodeList.get(c);
                System.out.println(node.getName());
               // int closer1 = func.getCloserPoint(cont);
               // int closer2 = func.getSecondCloserPoint(cont);
                String closestOnMinimum = func.getNodeOnMinDistOrItself(c,120);
                //Adaptando o projeto para ouvir o mais proximo dentro de uma dist min
                
               // closer1 = nodeOnMinimum;
                //Depois tenho que verificar as lanes que são agentes, pois estou fazendo
                //isso para pegar o sinal comunicado
               // String clo1 = "node" + String.valueOf(closer1);
               // String clo2 = "node" + String.valueOf(closer2);
                node.setClosestNode(closestOnMinimum);
             //   node.setSecondClosestNode(clo2);
                
                System.out.println("closest "+closestOnMinimum);
              //  node.setAgentActivated(true);
                this.world.changeElement(node.getName(), node);
              //  cont++;
                node01 = node;
            }
            if(node01!=null){
                StreetLight sl = this.world.streetWorld.getStreetLight(node01.getName());
                if(sl!=null){
               //     System.out.println("Init Agent");
                    agentOnlyOne = new DeviceAgent(sl, "lights", "lightContainer");
                    InitAgent.init(agentOnlyOne,"lights", "lightContainer");
                }
                else{
                  //  System.out.println("SL is null!!");
                    }
                }
            this.world.setNumOfAgents(nodeList.size());
            
             this.world.streetWorld.changeEnvironmentalLightFromAllEdges();
        }
        repaint();
    }

    public void controlMap(String adressfile) throws IOException {
        drawPeople = false;
        this.world.reset();
        mapControl = new MapFile(adressfile);
        mapControl.setMapInformations();

        this.pathMap = mapControl.getMapFile().getAbsolutePath();
        this.cidades = new ArrayList<>();
        this.cidades.addAll(mapControl.getListPoints());

        this.linhasGrafo = new ArrayList<>();
        this.linhasGrafo.addAll(mapControl.getListLines());

        this.departures = new ArrayList<>();
        this.departures.addAll(mapControl.getOriginsPoints());

        this.targets = new ArrayList<>();
        this.targets.addAll(mapControl.getTargetPoints());
        
       

        repaint();

    }

    public double randoEnvironmentalLight(){
        int rand = (int) (Math.random() * 4);
        switch (rand) {
            case 0:
                return 0.5;
            case 2:
                return 1.0;
            default:
                return 0.0;
        }
    }
    public void reinit(){
                this.world.setNumOfPeopleCompletedAfterSimulation(peopleCompleted);
                this.world.setNumOfAmbulancesCompletedAfterSimulation(ambulanceCompleted);
                this.world.setTotalEnergy(totalEnergy);
                this.world.setTotalTimeTrip(totalTimeTrip);
                
                this.peopleCompleted = 0;
                this.ambulanceCompleted = 0;
                this.totalTimeTrip = 0;
                this.totalEnergy = 0;
                /*Passo esse valor no arquivo de configuracao, mas adicionei aqui
                pra poder variar durante a simulacao e garantir que todo mundo vai lidar com dia,tarde e noite*/
                if(!this.configurationControl.isEnvironmentaLightIsSet()){
                    int currentNumberExecution = this.world.getNumberSimulationToChangeLight();
                    switch (currentNumberExecution) {
                        case 0:
                            this.world.streetWorld.setEnvironmentalLight(0.0);
                            break;
                        case 1:
                            this.world.streetWorld.setEnvironmentalLight(0.5);
                            break;
                        default:
                            this.world.streetWorld.setEnvironmentalLight(1.0);
                            break;
                    }
                
                    if(currentNumberExecution == 2){
                        this.world.setNumberSimulationToChangeLight(0);
                    }  
                    else{
                        currentNumberExecution++;
                        this.world.setNumberSimulationToChangeLight(currentNumberExecution);
                    }
                }
                
                this.world.setActualTimeSimulation(0);
                this.world.resetSimulation();
                if(this.world.isSortBrokenGlass())
                    this.world.sortNodesWithBrokenGlass();
                this.graph.putCars();
                
               // this.world.refreshPersonByLane();
    }

//    public int controlSimulation() {
//
//
//    }
    public int getPeopleCompleted() {
        return peopleCompleted;
    }
    
    public int getAmbulancesCompleted(){
        return ambulanceCompleted;
    }
    
    public int calculePeople(){
        
      //  System.out.println("Change people nodes");
        
                listPeople = World.getInstance().getListPeople();
                //Simulate car moviment after changes of semaphores
                List<Person> list = new ArrayList<>(listPeople.values());
                for (Person person : list) {
                   
                    if (person.getCurrentNode().equals(person.getNodeEnd())) {
                      //  System.out.println("A car completed its route");
                        String id = person.getId();
                        this.peopleCompleted++;
                        if(person.isIsAmbulance()){
                            this.ambulanceCompleted++;
                        }
                        
                        if (person.getId().equals(this.personSelected)) {
                           // System.out.println("car completed");
                           // System.out.println("Begin road "+ person.getNodeBegin());
                           // System.out.println("New road "+ person.getCurrentNode());
                            
                            this.agentOnlyOne.sendLog(LogDeviceAgent.Action.changePeoplePosition, LogDeviceAgent.TypeLog.WARNING,LogDeviceAgent.MethodName.calculePeople,"279", LogDeviceAgent.Resource.person, person.getId()+" completed route from :"+person.getNodeBegin()+ " to: "+person.getCurrentNode() +" at: "+this.world.getActualTimeSimulation()+ " trip time: ");
                        
                        }
                        
                        //comentei aqui pq a velocidade que as pessoas terminam suas rotas
                        //importa bastante
                        //this.graph.reinitPerson(Integer.valueOf(id));
                        this.world.removeConcludedPerson(id);
                      
                    }
                    else{
                        this.totalTimeTrip +=1.0;
                        String origin = person.getCurrentNode();
                        String dest = this.graph.getNextRoad(person.getId());
                        
                        Edge edge = (Edge) this.world.getElement(person.getCurrentEdge());
                        String nextLaneName = person.getNextEdge();
                        Edge nextEdge = ((Edge)this.world.getElement(nextLaneName));
                        
                        
                        //so para log
                        //uma edge sao dois nodes, e eu quero passar de um nó pra outro..Esse trecho
                        //precisa estar iluminado
                        //vou colocar nextEdge tb p forcar a cooperacao entre os postes
                        if(edge.getLightIntensity()==0 || nextEdge.getLightIntensity()==0){
                            this.agentOnlyOne.sendLog(LogDeviceAgent.Action.changePeoplePosition, LogDeviceAgent.TypeLog.WARNING,LogDeviceAgent.MethodName.calculePeople,"321", LogDeviceAgent.Resource.person, person.getId()+" not changed because my current edge or next edge is OFF. current:"+edge.getLightIntensity()+ " next: "+nextEdge.getLightIntensity());
                        
                        }
                        
                        //Se o ambiente estiver escurecido(onde eu estou ou para onde eu vou), a velocidade da pessoa é reduzida
                        //Se estiver escuro, ela nao se move, entao ja vou ter aumentado 1.0 sem ela
                        //ter trocado de node
                        
                        if(nextEdge.getLightIntensity()==0.5|| edge.getLightIntensity()==0.5){
                            this.totalTimeTrip+=0.5;
                        }
                        
                        
                        
                        this.world.changeNodePerson(origin, dest, person.getId());
                     //   edge = (Edge) this.world.getElement(person.getCurrentEdge());
                        
                      /*  else if(edge.getLightIntensity()==1.0){
                            this.totalTimeTrip+=2.0;
                        }*/
                        if (person.getId().equals(this.personSelected)) {
                            //edge = (Edge) this.world.getElement(person.getCurrentEdge());
                           // String actual = person.getCurrentNode();
                          //  Node roadActual = (Node) world.getElement(actual);
                         //   System.out.println("ABOUT CAR after"+ this.world.getActualTimeSimulation());
                          //  System.out.println("Current node: "+ roadActual.getName());
                          //  System.out.println("Current edge: "+edge.getBegin()+"->"+edge.getEnd()+ ": "+ edge.getNumberPeople() );
                            this.edgePersonSel = edge.getName();
                           //String color = this.world.streetWorld.getStreetLight(dest).getColor();
                        //    System.out.println("->"+dest+": "+color);
                            this.agentOnlyOne.sendLog(LogDeviceAgent.Action.changePeoplePosition, LogDeviceAgent.TypeLog.INFO,LogDeviceAgent.MethodName.calculePeople,"315", LogDeviceAgent.Resource.person, person.getId()+" current:"+origin+ " next: "+ dest+ " Intensity: "+ edge.getLightIntensity());

                        }
                    }
                      
                }
                
                //pra poder acompanhar a execucao, nao eh o valor final - ver reinit()
                this.world.setTotalTimeTrip(totalTimeTrip);
                this.world.setNumOfPeopleCompletedAfterSimulation(peopleCompleted);
             //   this.world.refreshPersonByLane();
          //      repaint();

        return this.peopleCompleted;
    }
    
    public void changeLight(){
                       
                List<Node> listNode = new ArrayList<>(this.world.getAllNodeList());
                if (this.world.getActualTimeSimulation() % this.world.getTimeDefaultSignal() == 0) {
                    
                    this.world.streetWorld.changeEnvironmentalLightFromAllEdges();
                    
                    //just to calculate the total of energy spent during a cycle
                //    if (this.world.getActualTimeSimulation() % 2 == 0) {
                 //   System.out.println("CHANGING SEMAPHORE");
                 double partialEnergy = 0;
                    for (Node node : listNode) {
                       String nodeName = node.getName(); 
                       partialEnergy = partialEnergy + this.world.streetWorld.getStreetLight(nodeName).getTransmittedLightDecision();
                       
                       //Vou fazer o fato de comunicar gastar um pouco de energia
                       double transmitterValue = this.world.streetWorld.getStreetLight(nodeName).getWirelessTransmitter();
                       if(transmitterValue>0){
                           partialEnergy = partialEnergy + 0.1;
                       }
                       
                      // node.cycleChange();
                       //   road.changeSignalPDeletarTempoFixo();
                    }
                    
                    this.totalEnergy += partialEnergy;
                 //   System.out.println("Energia gasta ate agora");
                  //  System.out.println(this.totalEnergy);
                }
                
                //poder ter uma parcial-mas esse valor eh substituido pelo total em reinit()
                this.world.setTotalEnergy(totalEnergy);
            //    repaint();
    }

    private Point2D pontoEscolhido;
//    private GrafoVisibilidade grafo;
    private ArrayList<Line2D.Double> linhasGrafo = null;
    int numCidades = 0;
    Point2D origem = null;
    Point2D destino = null;

    public int getNumCidades() {
        return numCidades;
    }

    public MainPanel() {
        initComponents();
        //   setPreferredSize(new java.awt.Dimension(600, 600));
        this.addMouseListener(this);
    }

    abstract void mouseClicado();

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(215, 215, 215));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    public void setOrigem(int index) {
        if (cidades.size() != 0) {
            origem = cidades.get(index);
            repaint();
        }
    }

    public void setDestino(int index) {
        if (cidades.size() != 0) {
            destino = cidades.get(index);
            repaint();
        }
    }

    public void reiniciarGrafo() {
        linhasGrafo = null;
        cidades = new ArrayList<Point2D>();
        numCidades = 0;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

//        Image bImage;
//         
//        bImage = this.createImage(path);
        //Desenha imagem de mapa
        Image im = new ImageIcon(pathMap).getImage();
        super.paintComponent(g);
        int x = 0;//(this.getWidth() - im.getWidth(null)) / 2;
        int y = 0;//(this.getHeight() - im.getHeight(null)) / 2;
        g.drawImage(im, x, y, this);
        if (linhasGrafo != null) {
            int cont = 0;
            for (Line2D.Double linha : linhasGrafo) {
                Font f = new Font("Dialog", Font.PLAIN, 12);
                g.setFont(f);
                g.setColor(Color.BLUE);
                g.drawLine((int) (linha.getX1()), (int) (linha.getY1()),
                        (int) (linha.getX2()), (int) (linha.getY2()));
                //    linha.
//draw arrow of each line   
                Edge lane = (Edge) this.world.getElement("edge" + cont);
                if (lane.getName().equals(this.edgePersonSel)) {
//                   System.out.println("ABOUT LANE AFTER CHANGE "+this.world.getActualTimeSimulation());
//                    System.out.println("Node begin: "+ edge.getBegin());
//                    System.out.println("Node end: "+ edge.getEnd());
//                    System.out.println("Num Person: "+ edge.getNumberCars());
//                    System.out.println("Signal "+ edge.getLightIntensity());
//                    System.out.println("Previous Signal "+ edge.getPreviousColorSignal());
//                    System.out.println("Voice "+ edge.getSignalVoice());
                }

                Node node = (Node) this.world.getElement(lane.getEnd());
//                String color = road.getColor(edge.getName());
               // String color = lane.getColor();
                String color = this.world.streetWorld.getStreetLight(node.getName()).getColor();
                Color cor = Color.BLACK;
                switch (color) {
                    case "black":
                        cor = Color.BLACK;
                        break;
                    case "gray":
                        cor = Color.GRAY;
                        break;
                    case "yellow":
                        cor = Color.YELLOW;
                        break;
                    case "red":
                        cor = Color.RED;
                        break;
                }

                this.drawArrow(g, (int) (linha.getX1()), (int) (linha.getY1()), (int) (linha.getX2()), (int) (linha.getY2()), cor);

                Node road2 = (Node) this.world.getElement(lane.getBegin());

                if (!this.personSelected.isEmpty() && this.world.containsCar(personSelected)) {
                    this.routePersonSelected = "";
                    Person car = (Person) this.world.getPerson(this.personSelected);
                    String[] route = car.getRoute();
                    for (String route1 : route) {
                        this.routePersonSelected += (route1 + "->");
                    }
                    String ro = car.getCurrentNode();
                    String nextRoad = car.getNextNodeInRoute();
                    //String roNext = car.getNextNodeInRoute();
                    //String roString = roNext+"->"+ro;
                    if (this.world.containsCar(car.getId()) && road2.getName().equals(ro) && node.getName().equals(nextRoad)) {
                   // if (this.world.containsCar(car.getId()) && road2.getName().equals(ro)) {
                        g.setColor(Color.RED);
                        g.drawLine((int) (linha.getX1()), (int) (linha.getY1()),
                                (int) (linha.getX2()), (int) (linha.getY2()));
                    }
                }

                if (drawPeople) {

                    int numCars = lane.getNumberPeople();
                   // int numAmbulance = lane.getNumberAmbulances();
//                 if(origem==pontos) g.setColor(Color.ORANGE);
//                 if(destino == pontos) g.setColor(Color.PINK);
//                 g.fillOval((int)(pontos.getX()) - 3, (int)(pontos.getY()) - 3, 7, 7);
                    g.setColor(Color.RED);
                    double x1 = linha.getX1();
                    double y1 = linha.getY1();
                    double x2 = linha.getX2();
                    double y2 = linha.getY2();
                    double dx = x2 - x1, dy = y2 - y1;
                    double angle = Math.atan2(dy, dx);
                    int len = (int) Math.sqrt(dx * dx + dy * dy);
                    AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
                    at.concatenate(AffineTransform.getRotateInstance(angle));
                    // at.setToScale(dx, dy);
                    Graphics2D g1 = (Graphics2D) g.create();
                    g1.transform(at);
                    f = new Font("Dialog", Font.BOLD, 14);

                    g1.setFont(f);

                    String toShow = "C:"+numCars+";"; //A:"+numAmbulance;
                    g1.drawString(toShow, (len - 6) / 2, 6);
                }

                cont++;
            }
        }

        //DESENHA CIDADES
        for (int contAux = 0; contAux < cidades.size(); contAux++) {

            Point2D pontos = cidades.get(contAux);
            if (pontos != null) {
                int h1 = 7;
                int h2 = 7;

                g.setColor(Color.BLACK);
                if (!this.personSelected.isEmpty() && this.world.containsCar(personSelected)) {
                    this.routePersonSelected = "";
                    Person car = this.world.getPerson(this.personSelected);
                    String[] route = car.getRoute();
                    for (String route1 : route) {
                        this.routePersonSelected += (route1 + "->");
                    }
                    String ro = "node" + contAux + "->";
                    if (this.routePersonSelected.contains(ro)) {
                        // g.setColor(Color.BLUE);
                        h1 = 12;
                        h2 = 12;
                    }
                }
                g.drawOval((int) (pontos.getX()) - 3, (int) (pontos.getY()) - 3, h1, h2);
//                 if(origem==pontos) g.setColor(Color.ORANGE);
//                 if(destino == pontos) g.setColor(Color.PINK);
                if (this.departures.contains(pontos)) {
                    g.setColor(Color.ORANGE);
                }
                if (this.targets.contains(pontos)) {
                    g.setColor(Color.RED);
                }
                g.fillOval((int) (pontos.getX()) - 3, (int) (pontos.getY()) - 3, h1, h2);
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(contAux), (int) (pontos.getX()) - 4, (int) (pontos.getY()) - 5);
            }
        }

    }

    public void mouseClicked(MouseEvent e) {

//        pontoEscolhido = new Point2D.Double(e.getPoint().x / proporcao, e.getPoint().y / proporcao);
        pontoEscolhido = new Point2D.Double(e.getPoint().x, e.getPoint().y);

        if (!cidades.isEmpty()) {
            numCidades = cidades.size();
        }

        cidades.add(numCidades, pontoEscolhido);
        numCidades++;
        
        if(numCidades>1){
            Point2D a = cidades.get(0);
            Point2D b = cidades.get(1);
            double dist = Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY()));
          //  System.out.println("Dist : "+ dist);
        }
//        System.out.println(" > PONTO NA TELA :  [" + e.getPoint().x + ", " + e.getPoint().y + "]");
//        System.out.println(" > PONTO REAL    :  [" + pontoEscolhido.getX() + ", " + pontoEscolhido.getY() + "]");
//        System.out.println(" ");

        System.out.println(e.getPoint().x + ";" + e.getPoint().y);
        repaint();

        mouseClicado();
    }

    public void PontoInicial() {
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public String getPathMap() {
        return pathMap;
    }

    public void setPathMap(String pathMap) {
        this.pathMap = pathMap;
        repaint();
    }

    public void setPathGraph(String pathGraph) {
        this.pathGraph = pathGraph;
        repaint();
    }

    public String getPathGraph() {
        return pathGraph;
    }

    private final int ARR_SIZE = 10;

    //c == red/green/yellow
    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2, Color c) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        // at.setToScale(dx, dy);
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);

        Font f = new Font("Dialog", Font.BOLD, 40);

        g.setFont(f);
        g.setColor(Color.BLACK);

        g.drawPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
        g.setColor(c);

        g.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);

//                
    }

    public DeviceAgent getAgentOnlyOne() {
        return agentOnlyOne;
    }

    public void setAgentOnlyOne(DeviceAgent agentOnlyOne) {
        this.agentOnlyOne = agentOnlyOne;
    }
    
    
    
    
}
