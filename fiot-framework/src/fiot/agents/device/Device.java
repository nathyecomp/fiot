/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents.device;

import fiot.agents.controller.Controller;
import fiot.agents.controller.ControllerList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Nathalia
 */
public class Device {
    
    String ip;
    String port;
    String nameDevice;
    String controllerID;
    String controllerType;
    double inputValue[];
    double outputValue[];
    boolean sameInput;
    private int timesWithoutSend;
    
    Date firstConnection;
    Date actualConnection;
    
    public Device(String ip, String port,String nameDevice, String controllerID){
        this.ip = ip;
        this.port = port;
        this.nameDevice = nameDevice;
        this.controllerID = controllerID;
        this.init();
    }
    
    private void init(){
        Controller controller = ControllerList.getInstance().getController(this.controllerID);
        this.controllerType = controller.getType();
        this.inputValue = new double[controller.getNumInput()];
        this.outputValue = new double[controller.getNumOutput()];
        this.timesWithoutSend = 0;
        this.sameInput = false;
        this.firstConnection = Calendar.getInstance().getTime();
        this.actualConnection = firstConnection;
        for(int cont =0 ; cont< this.inputValue.length; cont++){
            this.inputValue[cont] = 0.0;
        }
        for(int cont =0; cont< this.outputValue.length; cont++){
            this.outputValue[cont] = 0.0;
        }
        
    }
    
    public int getTimesWithoutSend() {
        return timesWithoutSend;
    }

    public void setTimesWithoutSend(int timesWithoutSend) {
        this.timesWithoutSend = timesWithoutSend;
    }

    public double[] getInputValue() {
        return inputValue;
    }

    public void setInputValue(double[] inputValue) {
        if(Arrays.equals(this.inputValue, inputValue)){
            this.setSameInput(true);
        }
        if(inputValue.length==0){
           this.setTimesWithoutSend(this.getTimesWithoutSend()+1);
        }
        else{
            this.setTimesWithoutSend(0);
        }
        this.inputValue = inputValue;
        
        this.actualConnection = Calendar.getInstance().getTime();
    }

    public double[] getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(double[] outputValue) {
        this.outputValue = outputValue;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public String getControllerID() {
        return controllerID;
    }

    public void setControllerID(String controllerID) {
        this.controllerID = controllerID;
    }

    public String getControllerType() {
        return controllerType;
    }
    
    public Controller getController(){
        return ControllerList.getInstance().getController(this.controllerID);
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isSameInput() {
        return sameInput;
    }

    public void setSameInput(boolean sameInput) {
        this.sameInput = sameInput;
    }

    public Date getFirstConnection() {
        return firstConnection;
    }

    public void setFirstConnection(Date firstConnection) {
        this.firstConnection = firstConnection;
    }

    public Date getActualConnection() {
        return actualConnection;
    }

    public void setActualConnection(Date actualConnection) {
        this.actualConnection = actualConnection;
    }

    
}
