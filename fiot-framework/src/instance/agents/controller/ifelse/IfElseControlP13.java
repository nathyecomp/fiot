/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package instance.agents.controller.ifelse;

import fiot.agents.controller.neuralnetwork.*;
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
public class IfElseControlP13 implements Controller {

    double sensors[];                 // Entrada de dados da rede neural;
    String sensorsName[];
    double actuador[]; //saida de dados
    String actuadorName[];
    int numInputNeurons = 0;
    int numOutputNeurons = 0;
    String name;
    String type = "";
    //name of controller on file
    String typeName = "";

    double listeningDecisionAux = 1.0;

    //   IndividualNeuralControler indi;
    public IfElseControlP13(File file) throws FileNotFoundException, IOException {
        type = "If-Else Control";
        this.processFile(file);
    }

    //Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Sim
    /*
    Intensidade_Lâmpada = ____Apagada____Média____Acesa 
    Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
    Habilitar_Audição = ___Sim ____Não
     */
    public void setPreviousListeningDecision(double value) {
        this.sensors[0] = value;
    }

    public double getPreviousListeningDecision() {
        return this.sensors[0];
    }

    public double getLightingSensor() {
        return this.sensors[1];
    }

    public double getMotionSensor() {
        return this.sensors[2];
    }

    public void setWirelessReceiver(double value) {
        this.sensors[3] = value;
    }

    public double getWirelessReceiver() {
        return this.sensors[3];
    }

    public void setWirelessTransmitter(double value) {
        this.actuador[0] = value;
    }

    public void setListeningDecision(double value) {
        this.actuador[1] = value;
    }

    public double getListeningDecision() {
        return this.actuador[1];
    }

    public void setLightDecision(double value) {
        this.actuador[2] = value;
    }

    public String getNameInput(int i) {
        return this.sensorsName[i];
    }

    public String getNameOutput(int i) {
        return this.actuadorName[i];
    }

    public double getValueInput(int i) {
        return this.sensors[i];
    }

    public double getValueOutput(int i) {
        return this.actuador[i];
    }

    public int getNumInputNeurons() {
        return numInputNeurons;
    }

    public int getNumOutputNeurons() {
        return numOutputNeurons;
    }

    private void processFile(File file) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            line = line.replaceAll(" ", "");
            if (line.contains("<Input>")) {
                this.numInputNeurons = Integer.valueOf(line.split(":")[1]);
                this.sensors = new double[this.numInputNeurons];
                this.sensorsName = new String[this.numInputNeurons];
                // System.out.println("num road "+ this.numRoads);
                for (int cont = 0; cont < this.numInputNeurons; cont++) {
                    String line2 = bufferedReader.readLine().replaceAll(" ", "");
                    this.sensors[cont] = 0.0;
                    this.sensorsName[cont] = line2;
                }
            } else if (line.contains("<Output>")) {
                this.numOutputNeurons = Integer.valueOf(line.split(":")[1]);
                this.actuador = new double[this.numOutputNeurons];
                this.actuadorName = new String[this.numOutputNeurons];
                // System.out.println("num road "+ this.numRoads);
                for (int cont = 0; cont < this.numOutputNeurons; cont++) {
                    String line2 = bufferedReader.readLine().replaceAll(" ", "");
                    this.actuador[cont] = 0.0;
                    this.actuadorName[cont] = line2;
                }
            } else if (line.contains("<Type>")) {
                typeName = line.split(":")[1];
            }
        }
    }

    private void updateSensors(double[] input) {
        this.sensors = input;
    }

    @Override
    public double[] getOutput(double[] input) {
        this.updateSensors(input);

        //    this.updateWeight(weights);
        // Se(Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Não) Então { 
        this.setPreviousListeningDecision(this.listeningDecisionAux);
        if (this.getPreviousListeningDecision() == 0.0) {
            this.setWirelessReceiver(0.0);
        }
        //IF ELse entra aqui

        if (this.getLightingSensor() == 0.0 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 0.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(0.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.0 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(0.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.0 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.5);
            this.setWirelessTransmitter(0.5);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.0 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.5);
            this.setWirelessTransmitter(0.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.0 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 0.0) {
            this.setLightDecision(1.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(1.0);
        }
        if (this.getLightingSensor() == 0.0 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(1.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.0 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(1.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.0 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(1.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.5 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 0.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(0.0);
            this.setListeningDecision(1.0);
        }
        if (this.getLightingSensor() == 0.5 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(0.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.5 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(0.5);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.5 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.5);
            this.setWirelessTransmitter(0.5);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.5 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 0.0) {
            this.setLightDecision(1.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(1.0);
        }
        if (this.getLightingSensor() == 0.5 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(1.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.5 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(1.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 0.5 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(1.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 1.0 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 0.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(0.0);
            this.setListeningDecision(1.0);
        }
        if (this.getLightingSensor() == 1.0 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(0.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 1.0 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(0.5);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 1.0 && this.getMotionSensor() == 0.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(0.5);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 1.0 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 0.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(1.0);
        }
        if (this.getLightingSensor() == 1.0 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 0.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 1.0 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 0.5 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(0.0);
        }
        if (this.getLightingSensor() == 1.0 && this.getMotionSensor() == 1.0 && this.getWirelessReceiver() == 1.0 && this.getPreviousListeningDecision() == 1.0) {
            this.setLightDecision(0.0);
            this.setWirelessTransmitter(1.0);
            this.setListeningDecision(0.0);
        }

        this.listeningDecisionAux = this.getListeningDecision();

        return this.actuador;
    }

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
    public String getType() {
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
