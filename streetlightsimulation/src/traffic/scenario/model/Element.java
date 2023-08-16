/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.scenario.model;

//import framework.neat.network.IndividualNeuralControler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import simulateddevices.Device;


/**
 *
 * @author nathi_000
 */
public abstract class Element extends Device{
    String image;
    String name;
    String type;
    //Localization
    Position pos;
    double size;
    int numInput;
    int numOutput;
    Map<String,Double>input;
    Map<String,Double>output;

    public Element() {
        this.input = new HashMap<>();
        this.output = new HashMap<>();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return pos.getX();
    }

    public void setX(int X) {
        this.pos.setX(X);
    }

    public int getY() {
        return pos.getY();
    }

    public void setY(int Y) {
        this.pos.setY(Y);
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
    
    
}
