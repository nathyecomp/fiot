/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.agents.controller.neuralnetwork;

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
public class ThreeLayerNetwork implements Controller{
    Layer camadas[];
    double sensors[];                 // Entrada de dados da rede neural;
    String sensorsName[];
    double actuador[]; //saida de dados
    String actuadorName[];
    double[][] pesos;
    int numInputNeurons = 0;
    int numHiddenNeurons = 0;
    int numOutputNeurons = 0;
    int numWeight = 0;
    String name;
    String type="";
    //name of controller on file
    String typeName="";
    String activationFunctionName="";
    
 //   IndividualNeuralControler indi;
    
    public ThreeLayerNetwork(File file) throws FileNotFoundException, IOException {
    //    this.indi = this.processFile(file);
        type = "Three Layer Network";
      //  System.out.println("Aki 1");
        this.processFile(file);
       // System.out.println("Aki 2");
        camadas = new Layer[3];                   // Determina o numero de camadas da rede;
        camadas[0] = new Layer("input", this.numInputNeurons);
        camadas[1] = new Layer("hidden", this.numHiddenNeurons);      // Determina o tipo e a quantidade de neuronios por camada;
        camadas[2] = new Layer("output", this.numOutputNeurons);
        
        this.initWeight();
        
        //this.setEntradas(indi.getNumSensorInput());       
        // Determina o numero de entradas da primeira camada;
    }

    public int getNumLayer(){
        return camadas.length;
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

    public int getNumHiddenNeurons() {
        return numHiddenNeurons;
    }

    public int getNumOutputNeurons() {
        return numOutputNeurons;
    }

    public int getNumWeight() {
        return numWeight;
    }

    public void setNumWeight(int numWeight) {
        this.numWeight = numWeight;
    }
    
    
    public int numLayer(){
        return this.camadas.length;
    }
    
    public Layer getLayer(int num){
        return this.camadas[num];
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
            else if(line.contains("<NHiddens>")){
                    this.numHiddenNeurons = Integer.valueOf(line.split(":")[1]);                
            }
            else if(line.contains("<NWeight>")){
                    this.numWeight = Integer.valueOf(line.split(":")[1]);                
            }
            else if(line.contains("<Type>")){
                    typeName = line.split(":")[1];                
            }
            else if(line.contains("<AFunction>")){
                    activationFunctionName = line.split(":")[1];                
            }
        }
    }
//    public void updateSensors(){
//        this.sensors = this.indi.getNetInput();
//    }
    private void updateSensors(double[] input){
        this.sensors = input;
    }
    private void updateWeight(double[] weights){
        //o peso da camada de entrada é sempre 1
        pesos = new double[camadas.length][this.numWeight];
        int numPeso = 0;
        for (int i = 1; i < camadas.length; i++) {
            int numW = camadas[i].getNumeroDeNeuronios()*camadas[i-1].getNumeroDeNeuronios();
            for(int cont = 0; cont<numW; cont++){
                pesos[i][cont] = weights[numPeso];
                numPeso++;
            }
            camadas[i].setWeights(this.pesos[i]);
         }
    }
    
    private void initWeight(){
        pesos = new double[camadas.length][this.numWeight];
        //int numPeso = 0;
        for (int i = 1; i < camadas.length; i++) {
            int numW = camadas[i].getNumeroDeNeuronios()*camadas[i-1].getNumeroDeNeuronios();
            for(int cont = 0; cont<numW; cont++){
                pesos[i][cont] = 0.0;
                //numPeso++;
            }
            camadas[i].setWeights(this.pesos[i]);
         }
    }
    private double[] getOutputNetwork(){
        return this.camadas[this.camadas.length-1].getSaidas();
    }
    @Override
    public double[] getOutput(double[] input){
        this.updateSensors(input);
    //    this.updateWeight(weights);
        for (int i = 0; i < camadas.length; i++) {
            if (i > 0) {
                camadas[i].setNumDeEntradas(camadas[i-1].getNumeroDeNeuronios());
                int numW = camadas[i].getNumeroDeNeuronios()*camadas[i-1].getNumeroDeNeuronios();
                camadas[i].setNumeroWeights(numW);
                camadas[i].setWeights(this.pesos[i]);
                camadas[i].setEntradas(camadas[i-1].getSaidas());
                camadas[i].getLayerOutput(this.activationFunctionName);
              //  camadas[i].setNeuronios(camadas[i - 1].getNumeroDeNeuronios());
            } else {
                camadas[i].setNumeroDeNeuronios(this.numInputNeurons);
                camadas[i].setSaidas(input);

//                camadas[i].setSaidas(this.indi.getNetInput());
              //  camadas[i].setNeuronios(numEntradas);
            }
        }
        return getOutputNetwork();
    }
//    public double[] processNetwork(double[] input, double[] weights){
//        this.updateSensors(input);
//        this.updateWeight(weights);
//        for (int i = 0; i < camadas.length; i++) {
//            if (i > 0) {
//                camadas[i].setNumDeEntradas(camadas[i-1].getNumeroDeNeuronios());
//                int numW = camadas[i].getNumeroDeNeuronios()*camadas[i-1].getNumeroDeNeuronios();
//                camadas[i].setNumeroWeights(numW);
//                camadas[i].setWeights(this.pesos[i]);
//                camadas[i].setEntradas(camadas[i-1].getSaidas());
//                camadas[i].getLayerOutput();
//              //  camadas[i].setNeuronios(camadas[i - 1].getNumeroDeNeuronios());
//            } else {
//                camadas[i].setNumeroDeNeuronios(this.numInputNeurons);
//                camadas[i].setSaidas(input);
//
////                camadas[i].setSaidas(this.indi.getNetInput());
//              //  camadas[i].setNeuronios(numEntradas);
//            }
//        }
//        return getOutputNetwork();
//    }
    
     /* Baseando-se no numero de neuronios da camada anterior, determina a quantidade de entradas da camada atual,
     * onde 'numEntradas' eh a quantidade de entradas dos neuronios da primeira camada;
     */
//    private void setEntradas() {
//
//        x = new double[numEntradas];
//
//        for (int i = 0; i < camadas.length; i++) {
//
//            if (i > 0) {
//                camadas[i].setNeuronios(camadas[i - 1].getNumeroDeNeuronios());
//            } else {
//                camadas[i].setNumeroDeNeuronios(numEntradas);
//                camadas[i].setSaidas(this.indi.getNetInput());
//              //  camadas[i].setNeuronios(numEntradas);
//            }
//        }
//    }
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
        this.updateWeight(configuration);
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
