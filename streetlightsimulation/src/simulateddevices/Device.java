/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulateddevices;

/**
 *
 * @author Nathalia
 */
public abstract class Device {
    public String name;
    //these sensors are used to collect important informations for
    //data presented on output sensors
    public String[] sensorName; 
    //output sensors to verify an specific fail
    public String []failSensorName = {"burned"};
    
    public String[] actuatorName;
    
    public String[] actuatorValue;
    
    public String nameController;
    
    public Device(){
        name = "machine";
        nameController = "";
        String[] sensorNames = {"temp", "presence", "rotation", "burned"};  
        this.sensorName = new String[sensorNames.length];
        this.sensorName = sensorNames;
        
        String[] actuators = {"alarm"};
        this.actuatorName = new String[actuators.length];
        this.actuatorName = actuators;
                
        actuatorValue = new String[actuatorName.length];
    }
    
    public Device (String name, String[] actuatorName, String[] sensorName){
        this.name = name;
        this.sensorName = new String[sensorName.length];
        this.sensorName = sensorName;
        
        this.actuatorName = new String[actuatorName.length];
        this.actuatorName = actuatorName;
        
        actuatorValue = new String[actuatorName.length];
    }

    public String getNameController() {
        return nameController;
    }

    public void setNameController(String nameController) {
        this.nameController = nameController;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSensorName() {
        return sensorName;
    }

    public void setSensorName(String[] sensorName) {
        this.sensorName = sensorName;
    }

    public String[] getActuatorName() {
        return actuatorName;
    }

    public void setActuatorName(String[] actuatorName) {
        this.actuatorName = actuatorName;
         actuatorValue = new String[actuatorName.length];
    }
   
    
    public abstract String readValuesFromInputSensors();
    
    public abstract void processActuatorInformation();
    
    public double randomValue(){     
        double value = Math.random();       
        return value;
    }
    
     
//    public  String readValuesFromInputSensors(){
//        
//        String read = sensorName[0]+"::";
//        read+= this.randomValue();
//        
//        for(int cont =1; cont<sensorName.length; cont++){
//            read+=(";"+sensorName[1]+"::"+this.randomValue());
//        }
//        return read;
//    }
    
    public String getActuatorNameList(){
        String read = "";
        for (String actuatorName1 : actuatorName) {
            read += actuatorName1 + ";";
        }
        return read;
    }
    
    public String getSensorNameList(){
        String read = "";
        for (String sensorName1 : sensorName) {
            read += sensorName1 + ";";
        }
        return read;
    }
    
//    public String readValuesFromFailSensors(){
//        String read = failSensorName[0]+"::";
//        read+= this.randomValue();
//        
//        for(int cont =1; cont<failSensorName.length; cont++){
//            read+=(";"+failSensorName[1]+"::"+this.randomValue());
//        }
//        return read;
//    }
    
    public void setActuatorValue(String[] value){
        System.arraycopy(value, 0, actuatorValue, 0, actuatorValue.length);
        //this.printActuatorVector();
    }
    
    public double[] getActuatorValue(){
        double[] values = new double[this.actuatorValue.length];
        for(int cont = 0; cont< values.length; cont++){
            this.actuatorValue[cont] = this.actuatorValue[cont].replaceAll(",", ".");
            values[cont] = Double.valueOf(this.actuatorValue[cont]);
        }
        return values;
    }
    
    public void printActuatorVector(){
        for(int cont = 0; cont< actuatorValue.length; cont++){
            System.out.println(actuatorName[cont]+"::"+actuatorValue[cont]);
        }
    }
}
