package fiot.learning;


import fiot.agents.AgentList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fiot.learning.methods.neat.Cromossomo;
import fiot.learning.methods.neat.Individuo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *You have to define the time of each simulation
 * for example: a simulation is relative with a total simulation of traffic controll
 * @author Nathalia
 */
public class Simulation {
   
    private static Simulation instance;
    private int numberAgentsExecuted;
    private final AgentList agentList;
    private int numOfCyclesSimulations;
    private int timeOfSimulation;
    private boolean finishedSimulation;
    
    //number of tests do be done with each configuration
    private int numOfSimulations;
    private final AdaptationMethodList adaptationList;
    private String methodInUse;
            
            //SOLUÇÃO PROVISÓRIA, POIS DEPOIS POSSO TER AGENTES HETEROGENEOS
    private String controllerInUse;
    
    private boolean onAdaptation;
    private String[] configurationExecution;

    
    //about genetic algorithm
    int numOfGenerations;
    int numOfBestToBeSelected;
    int numOfChildrens;
    int numberOfPopulation;
    boolean saveFitness;
    int numGenes;
    double valueMaxOfGene;
    double rateMutation;
    List<Individuo> firstGeneration;
    boolean firstGenerationIsOn = false;
    double forceSelection = 0;
    
    //about backpropagation
    double rate;
    double error;
    double numStandarts; //padrões de entrada
    double numEpochs;
    public List<Double[]> dataSet = new ArrayList<>();
    public Double[] firstWeights;
    
    
    private Simulation(){
        numberAgentsExecuted = 0;
        agentList = AgentList.getInstance();
        numOfCyclesSimulations = 0;
        timeOfSimulation = 0;
        this.finishedSimulation = false;
        numOfSimulations = 0;
        numOfGenerations = 0;
        adaptationList = AdaptationMethodList.getInstance();
        methodInUse="";
    }
    public static Simulation getInstance(){
           // Verifica se a variável possui algum valor,caso não, é criada a instancia.
           if (instance == null) {
               instance = new Simulation();
           }
           // Se a variavel possui algum valor, é retornado para quem está pedindo
           return instance;
     }

    public int getNumOfGenerations() {
        return numOfGenerations;
    }

    public void setNumOfGenerations(int numOfGenerations) {
        this.numOfGenerations = numOfGenerations;
    }

    
    public int getNumOfBestToBeSelected() {
        return numOfBestToBeSelected;
    }

    public void setNumOfBestToBeSelected(int numOfBestToBeSelected) {
        this.numOfBestToBeSelected = numOfBestToBeSelected;
    }

    public int getNumOfChildrens() {
        return numOfChildrens;
    }

    public void setNumOfChildrens(int numOfChildrens) {
        this.numOfChildrens = numOfChildrens;
    }

    public int getNumberOfPopulation() {
        return numberOfPopulation;
    }

    public void setNumberOfPopulation(int numberOfPopulation) {
        this.numberOfPopulation = numberOfPopulation;
    }

    public int getNumGenes() {
        return numGenes;
    }

    public void setNumGenes(int numGenes) {
        this.numGenes = numGenes;
    }

    public double getValueMaxOfGene() {
        return valueMaxOfGene;
    }

    public void setValueMaxOfGene(double valueMaxOfGene) {
        this.valueMaxOfGene = valueMaxOfGene;
    }

    public double getRateMutation() {
        return rateMutation;
    }

    public void setRateMutation(double rateMutation) {
        this.rateMutation = rateMutation;
    }

    
    public String getMethodInUse() {
        return methodInUse;
    }

    public void setMethodInUse(String methodInUse) {
        this.methodInUse = methodInUse;
    }
    
    public void incrementNumberAgentsExecution(){
        this.numberAgentsExecuted++;
        int numAdaptiveAgents = agentList.getNumAdaptiveAgentList();
        if(numberAgentsExecuted==numAdaptiveAgents){
            this.incrementNumOfCyclesSimulation();
            numberAgentsExecuted = 0;
        }
        
    }
    private void incrementNumOfCyclesSimulation(){
        this.numOfCyclesSimulations++;
        if(this.numOfCyclesSimulations==this.timeOfSimulation){
            this.setFinishedSimulation(true);
            this.numOfCyclesSimulations = 0;
            this.incrementNumOfSimulations();
        }
    }
    
    public void initSimulation(){
        this.setFinishedSimulation(false);
    }
    private void incrementNumOfSimulations(){
        this.numOfSimulations++;
    }
    public int getNumOfCyclesSimulation(){
        return this.numOfCyclesSimulations;
    }
    public int getNumberAgentsExecution(){
        return this.numberAgentsExecuted;
    }

    public int getTimeOfSimulation() {
        return timeOfSimulation;
    }

    public void setTimeOfSimulation(int timeOfSimulation) {
        this.timeOfSimulation = timeOfSimulation;
    }

    public boolean isFinishedSimulation() {
        return finishedSimulation;
    }

    public void setFinishedSimulation(boolean finishedSimulation) {
        this.finishedSimulation = finishedSimulation;
    }

    public int getNumOfSimulations() {
        return numOfSimulations;
    }

    public void setNumOfSimulations(int numOfSimulations) {
        this.numOfSimulations = numOfSimulations;
    }

    public String getControllerInUse() {
        return controllerInUse;
    }
    
    
    public void setSimulationConfiguration(String typeMethod, String controller, File file) throws IOException{
     
        this.methodInUse = typeMethod;
        this.controllerInUse = controller;
        this.processFile(file);
    }
    private void processFile(File file) throws FileNotFoundException, IOException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while ( ( line = bufferedReader.readLine() ) != null) {
            line = line.replaceAll(" ", "");
            if(line.contains("<numberOfGeneration>")){
                    this.numOfGenerations = Integer.valueOf(line.split(":")[1]);       
            }
            if(line.contains("<numberOfTests>")){
                    this.numOfSimulations = Integer.valueOf(line.split(":")[1]);              
            }
            if(line.contains("<numOfBestToBeSelected>")){
                    this.numOfBestToBeSelected = Integer.valueOf(line.split(":")[1]);              
            }
            if(line.contains("<numOfChildren>")){
                    this.numOfChildrens = Integer.valueOf(line.split(":")[1]);              
            }
            if(line.contains("<numberOfPopulation>")){
                    this.numberOfPopulation = Integer.valueOf(line.split(":")[1]);              
            }
            if(line.contains("<elitismSaveFitness>")){
                    int saveF = Integer.valueOf(line.split(":")[1]);
                    if(saveF==0)this.saveFitness = false;
                    else this.saveFitness = true;
            }
            if(line.contains("<maxNumGenes>")){
                    this.numGenes = Integer.valueOf(line.split(":")[1]);              
            }
            if(line.contains("<valueMaxOfGene>")){
                    this.valueMaxOfGene = Integer.valueOf(line.split(":")[1]);              
            }
            if(line.contains("<rateMutation>")){
                    this.rateMutation = Integer.valueOf(line.split(":")[1]);    
                    
            }
            /*Force selection will be used in the context of feature-selection.
            Percentage of valueMaxofGene*/
            if(line.contains("<forceSelection>")){
                    this.forceSelection = Integer.valueOf(line.split(":")[1]);    
                    
            }
            if(line.contains("<genotype>")){
                    this.setOnAdaptation(false);
                    int value = Integer.valueOf(line.split(":")[1]); 
                    this.configurationExecution = new String[value];
                    for(int cont = 0; cont< value; cont++){
                        String line2 = bufferedReader.readLine().replaceAll(" ", "");
                        this.configurationExecution[cont] = line2;
              //          System.out.println("Geracao pp executar "+ line2);
                    }
            }
            if(line.contains("<firstgeneration>")){
                this.setOnAdaptation(true);
                this.setFirstGenerationIsOn(true);
                int value = Integer.valueOf(line.split(":")[1]); 
                this.firstGeneration = new ArrayList<>();
                for(int cont = 0; cont< value; cont++){
                        String line2 = bufferedReader.readLine().replaceAll(" ", "");
                        double[] auxGenes = new double[this.getNumGenes()];
                        String[] pieces = line2.split(":");
                        String fit = pieces[2].replace("Genotype", "");
                        double fitness = Double.parseDouble(fit);
                        String ge = pieces[3].replace("[", "").replace("]", "");
                        String[] auxGenesString = ge.split(",");
                        for(int cont2 = 0; cont2< this.getNumGenes(); cont2++){
                            auxGenes[cont2] = Double.valueOf(auxGenesString[cont2]);
                        }
                        Cromossomo x = new Cromossomo(auxGenes);
                        Individuo indi =  new Individuo(x);
                        indi.setFitness(fitness);
//                        this.firstGeneration.add(new Individuo(x));
                        this.firstGeneration.add(indi);
                }

            
                     //   this.configurationExecution[cont] = line2;
                    }
            
            //related with backpropagation
            
            if(line.contains("<numEpochs>")){
                    this.numEpochs = Integer.valueOf(line.split(":")[1]);       
            }
            
            if(line.contains("<firstWeights>")){
                    String line2 = bufferedReader.readLine().replaceAll(" ", "");   
                    String[] dataLine = line2.split(";");
                    this.firstWeights= stringForDouble(dataLine);
            }
            
            if(line.contains("<datasetbackpropagation>")){
                    this.setOnAdaptation(true);
                    int value = Integer.valueOf(line.split(":")[1]); 
                   // this.configurationExecution = new String[value];
                    this.numStandarts = value;
                    for(int cont = 0; cont< value; cont++){
                        
                        String line2 = bufferedReader.readLine().replaceAll(" ", "");
                       if(!line.contains("?")){  // Exclui todas as linhas com '?';
                        String[] dataLine = line2.split(";");
                        Double[] data = stringForDouble(dataLine);
                        this.dataSet.add(data);
                       }
//                        this.configurationExecution[cont] = line2;
                    }
                    this.normalize(this.dataSet);
                    

    // Substitui os valores '4' e '2', do arquivo, para '1' e '0', respectivamente;
//                    switch (Integer.parseInt(valores[10])) {
//                        case 4:
//                            valores[10] = "1";
//                            break;
//                        case 2:
//                            valores[10] = "0";
//                            break;
//                    }


        }
        }
    }
    
    /**
     * 
     * @param s
     * @return 
     */
    private Double[] stringForDouble(String[] s) {

        Double[] temp = new Double[s.length - 1];

        for (int i = 1; i < s.length; i++) {
            temp[i - 1] = Double.parseDouble(s[i]);
        }
        return temp;
    }

    /**
     * 
     * @param v 
     */
    private void normalize(List<Double[]> v)
    {
        Double max;

        for (int i = 0; i < v.get(0).length; i++) {     // Percorre a linha de dados;

            max = v.get(i)[0];

            for (int j = 0; j < v.size(); j++) {        // Percorre a coluna de dados;
                if (max < v.get(j)[i]) {
                    max = v.get(j)[i];
                }
            }

            for (int count = 0; count < v.size(); count++) {
                v.get(count)[i] = v.get(count)[i] / max;
            }
        }
    }

    public boolean isFirstGenerationIsOn() {
        return firstGenerationIsOn;
    }

    public void setFirstGenerationIsOn(boolean firstGenerationIsOn) {
        this.firstGenerationIsOn = firstGenerationIsOn;
    }

    public List<Individuo> getFirstGeneration() {
        return firstGeneration;
    }

    public void setFirstGeneration(List<Individuo> firstGeneration) {
        this.firstGeneration = firstGeneration;
    }

    public boolean isSaveFitness() {
        return saveFitness;
    }

    public void setSaveFitness(boolean saveFitness) {
        this.saveFitness = saveFitness;
    }

    
    
    public boolean isOnAdaptation() {
        return onAdaptation;
    }

    public void setOnAdaptation(boolean onAdaptation) {
        this.onAdaptation = onAdaptation;
    }

    public String[] getConfigurationExecution() {
        return configurationExecution;
    }

    public void setConfigurationExecution(File file) throws IOException {
        this.processFile(file);
    }

    public double getForceSelection() {
        return forceSelection;
    }

    public void setForceSelection(double forceSelection) {
        this.forceSelection = forceSelection;
    }

    
}