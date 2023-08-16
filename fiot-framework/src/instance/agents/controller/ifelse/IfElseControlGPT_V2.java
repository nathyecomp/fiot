/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package instance.agents.controller.ifelse;

import fiot.agents.controller.Controller;

import java.io.*;

/**
 *
 * @author nathi_000
 * 13 August 2023
 */
public class IfElseControlGPT_V2 implements Controller {

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
    public IfElseControlGPT_V2(File file) throws FileNotFoundException, IOException {
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

    //V2
    @Override
    public double[] getOutput(double[] input) {
       // System.out.println("Executar if..else... control GPT ");
        this.updateSensors(input);

        this.setPreviousListeningDecision(this.listeningDecisionAux);
        double lightingSensor = this.getLightingSensor();
        double motionSensor = this.getMotionSensor();
        double wirelessReceiver = (this.getPreviousListeningDecision() == 1.0) ? this.getWirelessReceiver() : 0.0;

        double lightDecision = 0.0;
        double wirelessTransmit = 0.0;
        double listeningDecision = 1.0;  // Estará sempre escutando para otimizar o caminho da pessoa

        // Se detectarmos movimento
        if (motionSensor == 1.0) {
            lightDecision = 1.0;  // Ligue totalmente para o transeunte
            wirelessTransmit = 1.0;  // Avisar os postes próximos para preparar o caminho

            // Se ouvir de um poste vizinho sobre movimento
        } else if (wirelessReceiver == 1.0) {
            lightDecision = 0.5;  // Acenda parcialmente para antecipar a chegada do transeunte

            // Economia de energia sem comprometer a segurança
        } else if (lightingSensor == 0.0) {
            lightDecision = 0.5;  // Manter aceso parcialmente se estiver muito escuro, para garantir algum nível de segurança
        }

        // Decisões de atuação
        this.setLightDecision(lightDecision);
        this.setWirelessTransmitter(wirelessTransmit);
        this.setListeningDecision(listeningDecision);



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
