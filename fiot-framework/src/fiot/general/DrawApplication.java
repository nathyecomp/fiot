/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.general;

import fiot.agents.AdaptiveAgent;
import fiot.agents.AgentList;
import fiot.agents.controller.ControllerList;
import fiot.agents.controller.neuralnetwork.Layer;
import fiot.agents.controller.neuralnetwork.ThreeLayerNetwork;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Nathalia
 */
public class DrawApplication {
    
    String typeController;
    ApplicationConfiguration app;
    private ArrayList<Point2D> points;
   private ArrayList<Line2D.Double> neuronConexion;
   boolean draw = false;
    boolean viewWeight = false;
    public DrawApplication(String typeController){
        this.typeController = typeController;
        app = ApplicationConfiguration.getInstance();
        this.init();
    }
     private void init(){
        points = new ArrayList<Point2D>();
        neuronConexion = new ArrayList<Line2D.Double>();
   }
       public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }
     public void turnViewWeight(){
       this.viewWeight = true;
       //repaint();
   }
  
    
      public void paintNeuron(Graphics g, int x, int y, String name, String value) {
        int size1 = 20;
        int size2 = 20;
        g.setColor(Color.BLACK);
        g.drawOval(x, y, size1, size2);
        g.fillOval(x, y, size1, size2);
        g.setColor(Color.BLACK);
        g.drawString(name, x, y);
        if(value!=null){
            DecimalFormat df = new DecimalFormat("#.##");
            double valueD = Double.valueOf(value);
            String m = df.format(valueD);
            Font f = new Font("Dialog", Font.BOLD, 10);
            g.setColor(Color.RED);
            g.setFont(f);
            g.drawString(m, x-10, y-10);
       }
    }

      
  //  @Override
    public void paint(Graphics g, int withPanel, int heightPanel) {
//        super.paint(g);
//        
//        g.drawRect(0, 0, this.getHeight(), this.getWidth());
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, this.getHeight(), this.getWidth());
        
        if(this.typeController.equals("Three Layer Network")){
            this.paintControlLayerNetwork((Graphics2D) g,withPanel,heightPanel);
        }

    }
    
     public  BufferedImage paintControlLayerNetwork(Graphics2D g, int withPanel, int heightPanel){
        ThreeLayerNetwork neural = (ThreeLayerNetwork) ControllerList.
                getInstance().getController(this.app.nameActivatedController);
        
     //    ByteArrayOutputStream os;
      //  Graphics2D g = (Graphics2D) g2.create();
        BufferedImage bufferedImg = new BufferedImage(withPanel, heightPanel, BufferedImage.TYPE_INT_RGB);
        g = bufferedImg.createGraphics();
        g.setColor(Color.WHITE); 
        g.drawRect(0, 0, withPanel, heightPanel);  
        g.fillRect(0, 0, withPanel, heightPanel);  
        this.init();
        int shiftWith = 50;
        int shiftHeight = 50;
    //     System.out.println("Neural num input "+ neural.getNumInputNeurons());
        int with = (withPanel-shiftWith)/neural.getNumInputNeurons();
        int height = (heightPanel-shiftHeight)/neural.numLayer();
//        int with = (this.getWidth()-shiftWith)/neural.getNumInputNeurons();
//        int height = (this.getHeight()-shiftHeight)/neural.numLayer();
//        int yLayer1 = 0;
//        int yLayer2 = yLayer1+height;
//        int yLayer3 = yLayer2+height;
        int yPosition = shiftHeight;
        
        g.setColor(Color.BLACK);
        g.drawString(this.app.nameActivatedController, shiftWith-20, shiftHeight-20);
        for(int cont = 0; cont< neural.numLayer(); cont++){
            //layer 0           
            int xPosition = shiftWith;
            if(cont ==0){
                //with = this.getWidth()/neural.getNumInputNeurons();
            String value[] = null;
            if(!this.app.selectedAgent.isEmpty()){
                    AdaptiveAgent ag = AgentList.getInstance().getAgent(this.app.selectedAgent);
                    if(ag!=null){
                        double v[]= ag.getInput();
                        value = new String[v.length];
                        for(int a = 0; a< v.length; a++){
                            value[a] = String.valueOf(v[a]);
                        }
                    }
           }
            for(int cont2 = 0; cont2< neural.getNumInputNeurons(); cont2++){
                Point2D p = new Point2D.Double(yPosition, xPosition);
                points.add(p);
                if(value!=null)
                    this.paintNeuron(g, yPosition, xPosition, neural.getNameInput(cont2), value[cont2]);
                else 
                    this.paintNeuron(g, yPosition, xPosition, neural.getNameInput(cont2), null);
                 xPosition+=with;
            }
            shiftWith = 120;
            }
            else if(cont==neural.numLayer()-1){
                String value[] = null;
                if(!this.app.selectedAgent.isEmpty()){
                    AdaptiveAgent ag = AgentList.getInstance().getAgent(this.app.selectedAgent);
                    if(ag!=null){
                        double v[]= ag.getOutput();
                        value = new String[v.length];
                        for(int a = 0; a< v.length; a++){
                            value[a] = String.valueOf(v[a]);
                        }
                    }
                }
                //with = this.getWidth()/neural.getNumOutputNeurons();
              for(int cont2 = 0; cont2< neural.getNumOutputNeurons(); cont2++){
                  Point2D p = new Point2D.Double(yPosition, xPosition);
                    points.add(p);
                    if(value!=null)
                        this.paintNeuron(g, yPosition, xPosition, neural.getNameOutput(cont2), value[cont2]);
                    else
                        this.paintNeuron(g, yPosition, xPosition, neural.getNameOutput(cont2), null);
                 xPosition+=with;
               }  
            }
            else{
                //with = this.getWidth()/neural.getNumHiddenNeurons();
                for(int cont2 = 0; cont2< neural.getNumHiddenNeurons(); cont2++){
                    Point2D p = new Point2D.Double(yPosition, xPosition);
                    points.add(p);
                this.paintNeuron(g, yPosition, xPosition, "H"+cont2, null);
                 xPosition+=with;
               } 
            }
            yPosition+=height;
            
        }
        
         for(int cont = 0; cont< neural.getNumInputNeurons(); cont++){
            for(int cont2 = neural.getNumInputNeurons(); 
                    cont2< (neural.getNumInputNeurons()+neural.getNumHiddenNeurons()); 
                    cont2++){
                Line2D.Double line = new Line2D.Double(this.points.get(cont), 
                        this.points.get(cont2));
                this.neuronConexion.add(line);
            }
        }
        for(int cont = neural.getNumInputNeurons(); 
                cont< neural.getNumInputNeurons()+neural.getNumHiddenNeurons();
                cont++){
            for(int cont2 = neural.getNumInputNeurons()+neural.getNumHiddenNeurons();
                    cont2< neural.getNumInputNeurons()+neural.getNumHiddenNeurons()+neural.getNumOutputNeurons();
                    cont2++){
                Line2D.Double line = new Line2D.Double(this.points.get(cont), 
                        this.points.get(cont2));
                this.neuronConexion.add(line);
            }
        } 
        
        if(neuronConexion != null) {
            int cont = 0;
            int wCont = 0;
            Layer layer = neural.getLayer(1);
            for (Line2D.Double line : neuronConexion) {
                Color cor = Color.RED;
                if(cont== ((neural.getNumInputNeurons()*neural.getNumHiddenNeurons()))){
                    layer = neural.getLayer(2);
                    wCont = 0;
                }
                double w = layer.getWeight(wCont);
//                DecimalFormat df = new DecimalFormat("#.##");
//
//                String weight = df.format(w);
               
                String weight = String.valueOf(w);
                
                this.drawArrow(g,(int)(line.getX1()), (int)(line.getY1()),(int)(line.getX2()), (int)(line.getY2()), cor,weight);
                cont++;
                wCont++;
            }
        }
           
           // g2.drawString("This is a text", 0, 10);
          //  this.paintExperimentController(g2, 500, 500);
      //     os = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(bufferedImg, "png", os);
//        } catch (IOException ex) {
//            Logger.getLogger(DrawApplication.class.getName()).log(Level.SEVERE, null, ex);
//        }
           
        return bufferedImg;
    }
    
     private final int ARR_SIZE = 5;
     public void drawArrow(Graphics g1, int x1, int y1, int x2, int y2, Color c, String w) {
                Graphics2D g = (Graphics2D) g1.create();

                
                double dx = x2 - x1, dy = y2 - y1;
                double angle = Math.atan2(dy, dx);
                int len = (int) Math.sqrt(dx*dx + dy*dy);
                AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
                at.concatenate(AffineTransform.getRotateInstance(angle));
               // at.setToScale(dx, dy);
                g.transform(at);

                g.setColor(c);
                // Draw horizontal arrow starting in (0, 0)
                g.drawLine(0, 0, len, 0);
                
                Font f = new Font("Dialog", Font.BOLD, 40);
                
                g.setFont(f);
                
                
                
                g.drawPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                              new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
                g.setColor(c);
                
                g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                              new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
                
                if(this.viewWeight){
                    f = new Font("Dialog", Font.BOLD, 10);
                    g.setColor(Color.BLACK);
                    g.setFont(f);
                    g.drawString(w, (len-6)/3, 6);
                }
//                
            }
     
     //PARA TESTES
     public void writeString(Graphics g){
        
        g.setColor(Color.WHITE);
        g.drawString("DESENHANDO GRAPHICS", 0, 10);
        this.drawArrow(g, 0, 10, 2, 15, Color.red, null);
    }
    
}
