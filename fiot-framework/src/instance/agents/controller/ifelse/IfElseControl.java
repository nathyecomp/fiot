/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package instance.agents.controller.ifelse;

import fiot.agents.controller.Controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author nathi_000
 */
public class IfElseControl implements Controller{
    double sensors[];                 // Entrada de dados da rede neural;
    String sensorsName[];
    double actuador[]; //saida de dados
    String actuadorName[];
    int numInputNeurons = 0;
    int numOutputNeurons = 0;
    String name;
    String type="";
    //name of controller on file
    String typeName="";
    String subtypeName="";
    double listeningDecisionAux = 1.0;
    
    File file;
    
 //   IndividualNeuralControler indi;
    
    public IfElseControl(File file) throws FileNotFoundException, IOException {
        type = "If-Else Control";
        this.file = file;
        this.processFile(file);
    }
    
    public Controller getIfElseControl() throws IOException{
        switch(this.subtypeName){
            case "P01":
                return new IfElseControlP01(this.file);
            case "P02":
                return new IfElseControlP02(this.file);
            case "P03":
                return new IfElseControlP03(this.file);
            case "P04":
                return new IfElseControlP04(this.file);
            case "P05":
                return new IfElseControlP05(this.file);
            case "P06":
                return new IfElseControlP06(this.file);
            case "P07":
                return new IfElseControlP07(this.file);
            case "P08":
                return new IfElseControlP08(this.file);
            case "P09":
                return new IfElseControlP09(this.file);
            case "P10":
                return new IfElseControlP10(this.file);
            case "P11":
                return new IfElseControlP11(this.file);
            case "P12":
                return new IfElseControlP12(this.file);
            case "P13":
                return new IfElseControlP13(this.file);
            case "P14":
                return new IfElseControlP14(this.file);
            case "GPT-V1":
                return new IfElseControlGPT_V1(this.file);
            case "GPT-V2":
                return new IfElseControlGPT_V2(this.file);
            case "GPT-V3":
                return new IfElseControlGPT_V3(this.file);
            default:
                return this;
        }
    }
    
    //Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Sim
    /*
    Intensidade_Lâmpada = ____Apagada____Média____Acesa 
    Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
    Habilitar_Audição = ___Sim ____Não
    */
    public void setPreviousListeningDecision(double value){
        this.sensors[0] = value;
    }
    public double getPreviousListeningDecision(){
        return this.sensors[0];
    }
    public double getLightingSensor(){
        return this.sensors[1];
    }
    public double getMotionSensor(){
        return this.sensors[2];
    }
    public void setWirelessReceiver(double value){
        this.sensors[3] = value;
    }
    public double getWirelessReceiver(){
        return this.sensors[3];
    }
    
    public void setWirelessTransmitter(double value){
        this.actuador[0] = value;
    }
    public void setListeningDecision(double value){
        this.actuador[1] = value;
    }
    public double getListeningDecision(){
        return this.actuador[1];
    }
    public void setLightDecision(double value){
        this.actuador[2] = value;
    }
    
    
    public String getNameInput(int i){
        return this.sensorsName[i];
    }
    public String getNameOutput(int i){
        return this.actuadorName[i];
    }
    public double getValueInput(int i){
        return this.sensors[i];
    }
    public double getValueOutput(int i){
        return this.actuador[i];
    }
    public int getNumInputNeurons() {
        return numInputNeurons;
    }

    public int getNumOutputNeurons() {
        return numOutputNeurons;
    }
    
    
    private void processFile(File file) throws FileNotFoundException, IOException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while ( ( line = bufferedReader.readLine() ) != null) {
            line = line.replaceAll(" ", "");
            if(line.contains("<Input>")){
                    this.numInputNeurons = Integer.valueOf(line.split(":")[1]);
                    this.sensors = new double[this.numInputNeurons];
                    this.sensorsName = new String[this.numInputNeurons];
                   // System.out.println("num road "+ this.numRoads);
                    for(int cont = 0; cont< this.numInputNeurons; cont++){
                        String line2 = bufferedReader.readLine().replaceAll(" ", "");
                        this.sensors[cont] = 0.0;
                        this.sensorsName[cont] = line2;
                    }                
            }
            else if(line.contains("<Output>")){
                    this.numOutputNeurons = Integer.valueOf(line.split(":")[1]);
                    this.actuador = new double[this.numOutputNeurons];
                    this.actuadorName = new String[this.numOutputNeurons];
                   // System.out.println("num road "+ this.numRoads);
                    for(int cont = 0; cont< this.numOutputNeurons; cont++){
                        String line2 = bufferedReader.readLine().replaceAll(" ", "");
                        this.actuador[cont] = 0.0;
                        this.actuadorName[cont] = line2;
                    }                
            }
            else if(line.contains("<Type>")){
                    typeName = line.split(":")[1];                
            }
            else if(line.contains("<SubType>")){
                    subtypeName = line.split(":")[1];                
            }
        }
    }

    private void updateSensors(double[] input){
        this.sensors = input;
    }
    
    @Override
    public double[] getOutput(double[] input){
        this.updateSensors(input);
        
        
    //    this.updateWeight(weights);
    // Se(Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Não) Então { 

    this.setPreviousListeningDecision(this.listeningDecisionAux);
    if(this.getPreviousListeningDecision()== 0.0){
        this.setWirelessReceiver(0.0);
    }
    
    //IF ELse entra aqui
    if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() == 0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() == 1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() == 0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() == 1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 0.5 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    
    
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 0.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==0.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    else if(this.getLightingSensor() == 1.0 && this.getMotionSensor()== 1.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() ==1.0){
        this.setLightDecision(0.0); //0/0.5/1.0
        this.setWirelessTransmitter(0.0); //0/0.5/1.0
        this.setListeningDecision(0.0); //0/1
    }
    
    this.listeningDecisionAux = this.getListeningDecision();
        
        return this.actuador;
    }
    
    /*
    double threshold1 = 0.4;
    double threshold2 = 0.7;
    double threshold3 = 0.6;
    double threshold1 = World.getInstance().streetWorld.threshold1;
        double threshold2 = World.getInstance().streetWorld.threshold2;
        double threshold3 = World.getInstance().streetWorld.threshold3;

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
        }*/
    
    /*
    Estou_Ouvindo = Habilitar_Audição; 

Se(Estou_Ouvindo = Não) Então{
     Dado_Recebido_Vizinho = 0.0
}

Se(Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Não) Então { 
  Intensidade_Lâmpada = ___Apagada____Média____Acesa 
  Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se(Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Sim) Então { 
  Intensidade_Lâmpada = ___Apagada____Média____Acesa 
  Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.5 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
  Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 1.0 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Não) Então{
  Intensidade_Lâmpada = ____Apagada____Média____Acesa
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Sim) Então{
  Intensidade_Lâmpada = ____Apagada____Média____Acesa
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 0.5 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 1.0 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Médio AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Não) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Médio AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
  Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Médio AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.5 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Médio AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 1.0 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Médio AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Não) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Médio AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Médio AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 0.5 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Médio AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 1.0 AND Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Claro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND 
Estou_Ouvindo = Não) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
  Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Claro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND 
Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
}
Se (Sensor_de_Luminosidade = Claro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.5 AND 
Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
}  
Se (Sensor_de_Luminosidade = Claro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 1.0 AND 
Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa
  Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Claro AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 0.0 AND 
Estou_Ouvindo = Não) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
  Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Claro AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 0.0 AND 
Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Claro AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 0.5 AND 
Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
   Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
Se (Sensor_de_Luminosidade = Claro AND Pessoa_Próxima = Sim AND Dado_Recebido_Vizinho = 1.0 AND 
Estou_Ouvindo = Sim) Então {
  Intensidade_Lâmpada = ____Apagada____Média____Acesa 
  Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
  Habilitar_Audição = ___Sim ____Não
} 
    */

    @Override
    public Controller create(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNameType() {
        return typeName;
    }

    @Override
    public int getNumInput() {
        return this.numInputNeurons;
    }

    @Override
    public int getNumOutput() {
       return this.numOutputNeurons;
    }
    
    @Override
    public String getType(){
        return type;
    }

    @Override
    public void change(double[] configuration) {
        //nao ha adaptacao, entao simplesmente ignoro o change
      //  this.updateWeight(configuration);
    }

    @Override
    public String[] getNameInput() {
        return this.sensorsName;
    }

    @Override
    public String[] getNameOutput() {
        return this.actuadorName;
    }

}
