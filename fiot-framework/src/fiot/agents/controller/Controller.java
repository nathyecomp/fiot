/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents.controller;

import java.io.File;

/**
 *Interface of controllers. This interface allows to create similar methods
 * for different types of controllers, such as neural network, state machine
 * @version 1.0
 * @author Nathalia
 */
public interface Controller {
    
    public String getNameType();
    public String getType();
    public int getNumInput();
    public int getNumOutput();
    public String[] getNameInput();
    public String[] getNameOutput();
    public double[] getOutput(double[] input);
    public void change(double[] configuration);
    public Controller create(File file);
}
