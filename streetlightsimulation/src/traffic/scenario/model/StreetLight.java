/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.scenario.model;

/**
 *
 * @author nathalianascimento
 */
public class StreetLight extends Element {

    //output
    double wirelessTransmitter = 0; //0.0;0.5;1.0
    double listeningDecision = 1; //0 ou 1
    double lightDecision = 0; //0;0.5;1.0 (apagada, media, alta intensidade)

    //input
    double previousListeningDecision = 1;
    double previousWirelessTransmitter = 0;
    double previousLightDecision = 0;

    double lightSensor = 0; //light sensor
    double motionSensor = 0; //presence sensor
    double wirelessReceiver = 0; //0;1;2
    
    boolean brokenGlass = false;

    String nodeName;

    boolean agentActivated = false;

    public StreetLight(String name, String nodeName) {
        super.type = "streetlight";
        super.nameController = "lightNeuralNetwork";
        super.name = name;
       // String[] sensorNames = {"previousWirelessTransmitter", "previousListeningDecision",
        //    "previousLightDecision", "lightSensor", "motionSensor", "wirelessReceiver"};
         String[] sensorNames = { "previousListeningDecision", "lightSensor", "motionSensor", "wirelessReceiver"};
        String[] actuators = {"wirelessTransmitter", "listeningDecision", "lightDecision"};
        super.setSensorName(sensorNames);
        super.setActuatorName(actuators);

        this.nodeName = nodeName;
    }

    void getInputs() {
        // Node node = (Node)World.getInstance().getElement(this.nodeName);
        previousWirelessTransmitter = this.wirelessTransmitter;
        previousListeningDecision = this.listeningDecision;
        previousLightDecision = this.getTransmittedLightDecision(); //diferenciando a decisao real da decisao efetivada (se estiver queimada, uso o valor de queimado)
        //A luz que coleto do ambiente tem interferencia da luz que estou emitindo
        lightSensor = World.getInstance().streetWorld.readLightingSensor(previousLightDecision);

        motionSensor = World.getInstance().streetWorld.readMotionSensor(this.nodeName);
       
        if (listeningDecision == 1) {
            //receber o sinal de saida da lampada que esta no nó mais próximo     
            wirelessReceiver = World.getInstance().streetWorld.receiveWirelessData(this.nodeName);
        } else {
            wirelessReceiver = 0;
        }
        
    }

    @Override
    public String readValuesFromInputSensors() {
        this.getInputs();
        String read = "";

       // read += String.valueOf(this.previousWirelessTransmitter) + ";";
        read += String.valueOf(this.previousListeningDecision) + ";";
       // read += String.valueOf(this.previousLightDecision) + ";";
        read += String.valueOf(this.lightSensor) + ";";
        read += String.valueOf(this.motionSensor) + ";";
        read += String.valueOf(this.wirelessReceiver);
        
        return read;

    }

    @Override
    public void processActuatorInformation() {
        //  "listeningDecision","transmitterSignal","lightDecision"
        double value[] = this.getActuatorValue();
        double threshold1 = World.getInstance().streetWorld.threshold1;
        double threshold2 = World.getInstance().streetWorld.threshold2;
        double threshold3 = World.getInstance().streetWorld.threshold3;

        // StreetLight l = ((StreetLight) World.getInstance().getElement(this.getName()));
        // if(this.getListeningDecision()==0){
        if (value[0] > threshold2) {
            this.setWirelessTransmitter(1.0);
        } else if (value[0] > threshold1) {
            this.setWirelessTransmitter(0.5);
        } else {
            this.setWirelessTransmitter(0.0);
        }
        // } else{
        //    this.setWirelessTransmitter(0.0);
        //}

        if (value[1] > threshold3) {
            this.setListeningDecision(1.0);
        } else {
            this.setListeningDecision(0.0);
        }

        if (value[2] > threshold2) {
            this.setLightDecision(1.0);
        } else if (value[2] > threshold1) {
            this.setLightDecision(0.5);
        } else {
            this.setLightDecision(0.0);
        }
        
        //Se meu nome estiver na relacao de nodes sorteados pra ter a lampada quebrada,
        //a luz q vou emitir vai ser sempre zero
        if(World.getInstance().brokenGlassListContains(nodeName)){
            brokenGlass = true;
        }
        else brokenGlass = false;

        // this.setListeningDecision(value[0]);
        //this.setTransmitterSignal(value[1]);
        // this.setLightDecision(value[2]);
        //World.getInstance().changeElement(this.getName(), l);
    }

    public String getColor() {
        String color = "";
        double threshold1 = World.getInstance().streetWorld.threshold1;
        double threshold2 = World.getInstance().streetWorld.threshold2;
        if (this.lightDecision > threshold2) {
            color = "yellow";
        } else if (this.lightDecision > threshold1) {
            color = "gray";
        } else {
            color = "black";
        }
        if(this.brokenGlass){
            color = "red";
        }

        return color;
    }

    public double getListeningDecision() {
        return listeningDecision;
    }

    public void setListeningDecision(double listeningDecision) {
        this.listeningDecision = listeningDecision;
    }

    public double getWirelessTransmitter() {
        return wirelessTransmitter;
    }

    public void setWirelessTransmitter(double wirelessTransmitter) {
        this.wirelessTransmitter = wirelessTransmitter;
    }

    public double getTransmittedLightDecision() {
        if(this.brokenGlass)
            return 0.0;
        return lightDecision;
    }
    
    public double getDecidedLight(){
        return lightDecision;
    }

    public void setLightDecision(double lightDecision) {
        this.lightDecision = lightDecision;
    }

    public double getPreviousListeningDecision() {
        return previousListeningDecision;
    }

    public void setPreviousListeningDecision(double previousListeningDecision) {
        this.previousListeningDecision = previousListeningDecision;
    }

    public double getPreviousWirelessTransmitter() {
        return previousWirelessTransmitter;
    }

    public void setPreviousWirelessTransmitter(double previousWirelessTransmitter) {
        this.previousWirelessTransmitter = previousWirelessTransmitter;
    }

    public double getPreviousLightDecision() {
        return previousLightDecision;
    }

    public void setPreviousLightDecision(double previousLightDecision) {
        this.previousLightDecision = previousLightDecision;
    }

    public double getWirelessReceiver() {
        return wirelessReceiver;
    }

    public void setWirelessReceiver(double wirelessReceiver) {
        this.wirelessReceiver = wirelessReceiver;
    }

    public double getLightSensor() {
        return lightSensor;
    }

    public void setLightSensor(double lightSensor) {
        this.lightSensor = lightSensor;
    }

    public double getMotionSensor() {
        return motionSensor;
    }

    public void setMotionSensor(double motionSensor) {
        this.motionSensor = motionSensor;
    }

}
