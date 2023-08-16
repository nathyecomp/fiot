/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package traffic.scenario.gui;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import traffic.scenario.model.Node;

/**
 *
 * @author Administrador
 */
public class MapFunctions2 {
    public ArrayList<Node> graph = new ArrayList<>() ;
   // private ArrayList<Line2D.Double> graph = new ArrayList<>();
    double[][] distBetweenNodes;

   // double distsEntrePontos[][];
    public MapFunctions2(ArrayList<Node> graph){
        this.graph = graph;
        this.graphDists();
        
    }
    public ArrayList<Node> getGraph() {
        return graph;
    }

    public void setGraph(ArrayList<Node> graph) {
        this.graph = graph;
    }


    public double calcDist(Point2D a, Point2D b){
        return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY()));
    }

    private double[][] graphDists(){
        distBetweenNodes = new double[this.graph.size()][this.graph.size()];
        for(int cont =0; cont<graph.size(); cont++){
            for(int cont2 = 0; cont2< graph.size(); cont2++){
                distBetweenNodes[cont][cont2] = calcDist(graph.get(cont).getXy(),graph.get(cont2).getXy());
            }
        }
        return distBetweenNodes;
    }

    public double getDistBetweenPoints(int a, int b){
        return distBetweenNodes[a][b];
    }
    
    public String getNodeOnMinDistOrItself(int a, double minimumDist){
        int b = -1;
        double distMin = minimumDist;
        //double distMin = 100000;
        for(int cont = 0; cont<graph.size(); cont++){
            double dist = this.getDistBetweenPoints(a, cont);
            if(a!= cont && dist< distMin){
                distMin = dist;
                b = cont;
            }
        }
        //return itself
        if(b ==-1){
            return graph.get(a).getName();}
        return graph.get(b).getName();
    }
    public int getCloserPoint(int a){
        int b = 0;
        double distMin = 100000;
        for(int cont = 0; cont<graph.size(); cont++){
            double dist = this.getDistBetweenPoints(a, cont);
            if(a!= cont && dist< distMin){
                distMin = dist;
                b = cont;
            }
        }
        return b;
    }
    public int getSecondCloserPoint(int a){
        int b = 0;
        int c = 0;
        double distMin = 1000000;
        double distMin2 = 1000000;
        for(int cont = 0; cont<graph.size(); cont++){
            double dist = this.getDistBetweenPoints(a, cont);
            //distMin2 = distMin;
            if(a!= cont && dist<= distMin){
                distMin2 = distMin;
                c = b;
                distMin = dist;
                b = cont;
            }           
        }
        return c;
    }
}
