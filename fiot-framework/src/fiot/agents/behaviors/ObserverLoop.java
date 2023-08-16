/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.agents.behaviors;

import fiot.agents.AgentList;
import fiot.agents.ObserverAgent;
import fiot.agents.controller.Controller;
import fiot.agents.controller.ControllerList;
import fiot.agents.logs.LogObserverAgent;
import fiot.agents.message.FIoTMessage;
import fiot.general.ChatGPT;
import fiot.gui.PanelControl;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import fiot.learning.AdaptationMethod;
import fiot.learning.AdaptationMethodList;
import fiot.learning.Simulation;
import fiot.learning.methods.neat.Individuo;
import fiot.learning.methods.neat.NeatControl;

/**
 * Behavior of ObserverAgent
 * @author Nathalia
 * @version 1.0
 */
public abstract class ObserverLoop extends OneShotBehaviour {

    AgentList agentList;
    public ObserverAgent observerAgent;
    Simulation simulation;
    public String adressToAnswer = "";
    ControllerList listControl;
    
    
    //backpropagation
     double taxa = 0.1;
    double eq, eqm = 0.0;
    double eqm_atual = 1.0;
    double eqm_anterior = 987654321987654.0;
    double precisao;
    double yDesejado;
    double erro;
    double yFinal = 0.0;
    //AdaptationMethodList adaptList;
    // AdaptationMethod method;
    
    /*DELETAR AQUI E NA CLASSE INDIVIDUO e na classe ObserverLoop_StreetLight*/
     private double totalEnergy = 0;
     private double completedPeople = 0;
     private double totalTimeTrip = 0;
                

    public double getTotalenergy() {
        return totalEnergy;
    }

    public void setTotalenergy(double totalenergy) {
        this.totalEnergy = totalenergy;
    }

    public double getCompletedPeople() {
        return completedPeople;
    }

    public void setCompletedPeople(double totalpeople) {
        this.completedPeople = totalpeople;
    }

    public double getTotaltriptime() {
        return totalTimeTrip;
    }

    public void setTotaltriptime(double totaltriptime) {
        this.totalTimeTrip = totaltriptime;
    }
     //DELETAR ATE AQUI - NAO ESQUECA DE DELETAR DENTRO DE TRAINING CONTROL E NO MOMENTO QUE ESCREVE NO ARQUIVO
    
    
    public ObserverLoop(Agent a) {
        super(a);
        observerAgent = (ObserverAgent) a;
        agentList = AgentList.getInstance();
        simulation = Simulation.getInstance();
        listControl = ControllerList.getInstance();
       /// adaptList = AdaptationMethodList.getInstance();
        // method = adaptList.getMethod(simulation.getMethodInUse());
    }
    

    @Override
    public void action() {
        this.observerAgent.sendLog(LogObserverAgent.Action.chooseAdaptationMethod, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"116", LogObserverAgent.Resource.agent, "Method in use is "+ simulation.getMethodInUse());

        if (simulation.getMethodInUse().equals("Genetic Algorithm")) {
            try {
                if (simulation.isOnAdaptation()) {
                    this.observerAgent.sendLog(LogObserverAgent.Action.startGeneticAlgorithm, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"118", LogObserverAgent.Resource.agent, "Start adaptation ");

                    this.trainingNeatControl();
                } else {
                    this.executingNeatControl();
                }
            } catch (FIPAException | IOException ex) {
                Logger.getLogger(ObserverLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (simulation.getMethodInUse().equals("No Adaptation")) {
            try {
                this.doNotAdapt();
            } catch (IOException ex) {
                Logger.getLogger(ObserverLoop.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FIPAException ex) {
                Logger.getLogger(ObserverLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // }
    }

    /* Vou chamar esse se eu já entrar com um indivíduo pré-selecionado - só quero executar, não evoluir
    * */
    private void executingNeatControl() throws IOException, FIPAException {
        NeatControl neat;
        neat = NeatControl.getInstance();
        File f;
        f = new File("execution02072017.txt");
        FileWriter w = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(w);
        PrintWriter wr = new PrintWriter(bw);
        wr.append("INICIO: " + new Date(System.currentTimeMillis()) + "\r\n");
        String[] configs = simulation.getConfigurationExecution();
        double[] genesList;
        for (int cont = 0; cont < configs.length; cont++) {
            wr.append("Selected Individual" + configs[cont] + "\r\n");
            
            this.observerAgent.sendLog(LogObserverAgent.Action.startExecutionWithControllerConfiguration, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"155", LogObserverAgent.Resource.individual, "Selected individual: "+ configs[cont]);

            String[] genConfig = configs[cont].split(",");
            genesList = new double[genConfig.length];
            for (int cont2 = 0; cont2 < genConfig.length; cont2++) {
                DecimalFormat df = new DecimalFormat("#.#");

                String weight = df.format(Double.valueOf(genConfig[cont2]));
                weight = weight.replaceAll(",", ".");
                genesList[cont2] = Double.valueOf(weight);

                // System.out.println("gen "+ cont2+": "+genesList[cont2]);
            }

            NeatControl.getInstance().setGenes(genesList);
            this.listControl.getController(simulation.getControllerInUse()).change(genesList);
            this.observerAgent.sendLog(LogObserverAgent.Action.readSimulationResults, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"184", LogObserverAgent.Resource.individual, "");
            
            double fitness = this.readResultSimulation();
            double totalFEnergy = this.getTotalenergy();
            double totalFPeople = this.getCompletedPeople();
            double totalFTrip = this.getTotaltriptime();
            System.out.println("Fitness " + fitness);
            System.out.println("Energy " + totalFEnergy);
            System.out.println("People " + totalFPeople);
            System.out.println("totalFTrip " + totalFTrip);
            wr.append("Fitness " + fitness + "\r\n");
            wr.append("Energy " + totalFEnergy + "\r\n");
            wr.append("People " + totalFPeople + "\r\n");
            wr.append("totalFTrip " + totalFTrip + "\r\n");
            
            this.observerAgent.sendLog(LogObserverAgent.Action.calculateEnergy, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"184", LogObserverAgent.Resource.individual, ""+totalFEnergy);
            if(totalFEnergy<=70)
                this.observerAgent.sendLog(LogObserverAgent.Action.achieveEnergyTarget, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"184", LogObserverAgent.Resource.individual, "target is 70%");
            
            
            this.observerAgent.sendLog(LogObserverAgent.Action.calculatePeople, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"184", LogObserverAgent.Resource.individual, ""+totalFPeople);
            if(totalFPeople==100)
                this.observerAgent.sendLog(LogObserverAgent.Action.achievePeopleTarget, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"184", LogObserverAgent.Resource.individual, "target is 100%");
            
            this.observerAgent.sendLog(LogObserverAgent.Action.calculateTripDuration, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"184", LogObserverAgent.Resource.individual, ""+totalFTrip);


            this.observerAgent.sendLog(LogObserverAgent.Action.calculateFitness, LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,"186", LogObserverAgent.Resource.individual, ""+fitness);
            
            

            answerMessage("initSimulation");
        }

        wr.append("FIM: " + new Date(System.currentTimeMillis()) + "\r\n");

        wr.close();
        bw.close();

    }

    private void gptAdaptation() throws Exception {
        String context = "You are observing a set of smart street lights that are controlled by a three-layer neural network. Each iteration, you receive ";
        String prompt = "Hello, my boy. I love you." ;
        System.out.println(prompt);
        ChatGPT chatgpt = new ChatGPT();
        String resp = chatgpt.chatGPT(prompt);
        System.out.println(resp);
    }
    
    private void doNotAdapt() throws IOException, FIPAException {
        File f;
        f = new File("ifElseExecution30062017.txt");
        FileWriter w = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(w);
        PrintWriter wr = new PrintWriter(bw);
        wr.append("INICIO: " + new Date(System.currentTimeMillis()) + "\r\n");
        
        this.observerAgent.sendLog(LogObserverAgent.Action.startExecutionWithoutAdaptation,
                LogObserverAgent.TypeLog.INFO,LogObserverAgent.MethodName.observerLoop,
                "206", LogObserverAgent.Resource.agent, "No adaptation");

           // this.listControl.getController(simulation.getControllerInUse()).change(genesList);
        double fitness = this.readResultSimulation();
        double totalFEnergy = this.getTotalenergy();
        double totalFPeople = this.getCompletedPeople();
        double totalFTrip = this.getTotaltriptime();
        System.out.println("Fitness " + fitness);
        System.out.println("Energy " + totalFEnergy);
        System.out.println("People " + totalFPeople);
        System.out.println("totalFTrip " + totalFTrip);
        
        this.observerAgent.sendLog(LogObserverAgent.Action.calculateFitness, 
                LogObserverAgent.TypeLog.INFO,
                LogObserverAgent.MethodName.observerLoop,
                "222", LogObserverAgent.Resource.individual, 
                "Result "+ fitness);
        
        answerMessage("initSimulation");
        //answerMessage("stopSimulation");
        wr.append("Fitness " + fitness + "\r\n");
        wr.append("Energy " + totalFEnergy + "\r\n");
        wr.append("People " + totalFPeople + "\r\n");
        wr.append("totalFTrip " + totalFTrip + "\r\n");
        wr.append("FIM: " + new Date(System.currentTimeMillis()) + "\r\n");
        wr.close();
        bw.close();
    }

    private void trainingBackPropagation(){
        
    }
    private void trainingNeatControl() throws FIPAException {
        FileWriter w2 = null;
        try {
            NeatControl neat;
            int numberOfTests = simulation.getNumOfSimulations();
            int numberOfGenerations = simulation.getNumOfGenerations();
            int numOfBestToBeSelected = simulation.getNumOfBestToBeSelected();
            int numOfChildrens = simulation.getNumOfChildrens();
            int sizeOfPopulation = simulation.getNumberOfPopulation();
            int numExecutions = 0;          
            //elitism saving the fitness
            boolean saveFitness = simulation.isSaveFitness();
            neat = NeatControl.getInstance();
            // neat.setTimeSimulation(2);
            List<Individuo> listIndividuos;
            
            //to continue a simulation from file
            if(simulation.isFirstGenerationIsOn()){
                listIndividuos = new ArrayList<>(simulation.getFirstGeneration());
            }
            else{
                listIndividuos = NeatControl.getInstance().getFirstPopulation(sizeOfPopulation);
            }
            List<Individuo> ordenadosPorFitness = new ArrayList<>();
            Individuo[] best = new Individuo[numberOfGenerations];
            Individuo[] worst = new Individuo[numberOfGenerations];
            File f;
            f = new File("evolutionSimulation20032017.txt");
            System.out.println("File for Evolution Simulation "+ f.getAbsolutePath());
            try {
                FileWriter w = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.append("INICIO: " + new Date(System.currentTimeMillis()) + "\r\n");

                while (numExecutions < numberOfGenerations) {
                    
                this.observerAgent.sendLog(LogObserverAgent.Action.startNewGeneration, 
                LogObserverAgent.TypeLog.INFO,
                LogObserverAgent.MethodName.observerLoop,
                "279", LogObserverAgent.Resource.generation, 
                "Generation "+ numExecutions);
                    ordenadosPorFitness = new ArrayList<>();
                    double average = 0;
                    //APAGAR A PARTIR DAQUI
                    double averagePeople = 0;
                    double averageTrip = 0;
                    double averageEnergy = 0;
                    //APAGAR ATE AQUI
                    System.out.println("Iniciando Geração: " + numExecutions);
                    double fitnessMax = 0;
                    double fitnessMin = 1000000000;
                    for (int cont = 0; cont < sizeOfPopulation; cont++) {
                        Individuo indi = listIndividuos.get(cont);
                        double[] genes = indi.getCromossomo().getGenes();
                        NeatControl.getInstance().setGenes(genes);
                        this.listControl.getController(simulation.getControllerInUse()).change(genes);
                        double fitness;
                        
                        /*APAGAR DAQUI*/
                        double fitnessE, fitnessP, fitnessT;
                        //APAGAR ATE AQUI
                        if(saveFitness && indi.getFitness()>0){
                            fitness = indi.getFitness();
                            
                            /*APAGAR DAQUI*/
                             fitnessE = indi.getTotalenergy();
                             fitnessP = indi.getTotalpeople();
                             fitnessT = indi.getTotaltriptime();
                        //APAGAR ATE AQUI
                        }
                        else{
                            double totalFitness = 0;
                            /*apagar depois daqui*/
                            double totalFEnergy = 0;
                            double totalFPeople = 0;
                            double totalFTrip = 0;
                            //apagar ate aqui!!!!!
                            for (int test = 0; test < numberOfTests; test++) {
                                totalFitness += this.readResultSimulation();
                                //APAGAR DAQUI
                                totalFEnergy += this.getTotalenergy();
                                totalFPeople += this.getCompletedPeople();
                                totalFTrip+= this.getTotaltriptime();
                                //APAGAR ATE AQUI
                                answerMessage("initSimulation");
                            }
                            fitness= totalFitness / numberOfTests;   
                          //  DecimalFormat formato2 = new DecimalFormat("#.#");      
                          //  fitness = Double.valueOf(formato2.format((totalFitness / numberOfTests)));

                            indi.setFitness(fitness);
                            
                            //APAGAR DAQUI
                             fitnessE = totalFEnergy / numberOfTests;
                             fitnessP = totalFPeople / numberOfTests;
                             fitnessT = totalFTrip / numberOfTests;
                             
                        //     fitnessE = Double.valueOf(formato2.format((totalFEnergy / numberOfTests)));
                         //    fitnessP = Double.valueOf(formato2.format((totalFPeople / numberOfTests)));
                          //   fitnessT = Double.valueOf(formato2.format((totalFTrip / numberOfTests)));
                             
                            indi.setTotalenergy(fitnessE);
                            indi.setTotalpeople(fitnessP);
                            indi.setTotaltriptime(fitnessT);
                            listIndividuos.get(cont).setTotalenergy(fitnessE);
                            listIndividuos.get(cont).setTotalpeople(fitnessP);
                            listIndividuos.get(cont).setTotaltriptime(fitnessT);
                            
                            //A´PAGAR ATE AQUI
                            listIndividuos.get(cont).setFitness(fitness);//add p teste
                        }
                        //average of individuals of one generation
                        average += fitness;
                        
                        //APAGAR DAQUI
                            averagePeople += fitnessP;
                             averageTrip += fitnessT;
                            averageEnergy += fitnessE;
                        //APAGAR ATE AQUI
//                        if (!ordenadosPorFitness.containsKey(fitness)) {
//                            List<Individuo> li = new ArrayList<>();
//                            ordenadosPorFitness.put(fitness, li);
//                        }
                        if (fitness >= fitnessMax) {
                            best[numExecutions] = indi;
                            fitnessMax = fitness;
                        }
                        if (fitness <= fitnessMin) {
                            worst[numExecutions] = indi;
                            fitnessMin = fitness;
                        }
                        //Select the best ones
                        //Os melhores são os de maior fitness
                      //  ordenadosPorFitness.get(fitness).add(indi);
                       
                        ordenadosPorFitness.add(cont,indi);

                    }
                    double divAverage = (average / sizeOfPopulation);
                    
                    //APAGAR DAQUI
                     double divAverageP = (averagePeople / sizeOfPopulation);
                      double divAverageT = (averageTrip / sizeOfPopulation);
                       double divAverageE = (averageEnergy / sizeOfPopulation);

                        //APAGAR ATE AQUI
                    ////Evolution
                    //   System.out.println("Cheguei aqui");
                   //  System.out.println("List before ord ");
                   //  this.printList(listIndividuos, numExecutions);
                     Collections.sort(listIndividuos);
                     
                   //  System.out.println("List after ord ");
                    // this.printList(listIndividuos, numExecutions);
//test                    Collections.sort(ordenadosPorFitness);
                    

//                    System.out.println("Geração " + numExecutions + ":");
//                    System.out.println("Average: " + divAverage);
//                    System.out.println("Melhor fitness: " + best[numExecutions].getFitness());
//                    System.out.println("Genes: " + Arrays.toString(best[numExecutions].getCromossomo().getGenes()));
//                    System.out.println("Pior fitness: " + worst[numExecutions].getFitness());
//                    System.out.println("Genes: " + Arrays.toString(worst[numExecutions].getCromossomo().getGenes()));
                    //O melhor de cada geração
                    this.observerAgent.sendLog(LogObserverAgent.Action.selectBestFitIndividuals, 
                    LogObserverAgent.TypeLog.INFO,
                    LogObserverAgent.MethodName.observerLoop,
                    "407", LogObserverAgent.Resource.generation, 
                    "Generation "+ numExecutions + "Best fit " + best[numExecutions].getFitness());
                    
                    wr.append("Geração " + numExecutions + ":");
                    wr.append("Fitness Average: " + divAverage);
                    wr.append("Energy Average: " + divAverageE);
                    wr.append("Trip Average: " + divAverageT);
                    wr.append("C. People Average: " + divAverageP);
                    wr.append("Melhor fitness: " + best[numExecutions].getFitness());
                    wr.append("Melhor ft Energy: " + best[numExecutions].getTotalenergy());
                    wr.append("Melhor ft Trip: " + best[numExecutions].getTotaltriptime());
                    wr.append("Melhor ft People: " + best[numExecutions].getTotalpeople());
                    wr.append("Genes: " + Arrays.toString(best[numExecutions].getCromossomo().getGenes()));
                    wr.append("Born in generation: " + best[numExecutions].getBornGeneration());
                    wr.append("Father 1's Genes: " + Arrays.toString(best[numExecutions].getFatherCromossomo1().getGenes()));
                    wr.append("Father 2's Genes: " + Arrays.toString(best[numExecutions].getFatherCromossomo2().getGenes()));
                    wr.append("Pior fitness: " + worst[numExecutions].getFitness());
                    wr.append("Pior ft Energy: " + worst[numExecutions].getTotalenergy());
                    wr.append("Pior ft Trip: " + worst[numExecutions].getTotaltriptime());
                    wr.append("P ft People: " + worst[numExecutions].getTotalpeople());
                    wr.append("Genes: " + Arrays.toString(worst[numExecutions].getCromossomo().getGenes()));
                    wr.append("\n");
                    wr.flush();

                    //   if ((numExecutions - 1)==0||(numExecutions - 1)==1||(numExecutions - 1) == Math.round(numberOfGenerations / 10)||(numExecutions - 1) == Math.round(numberOfGenerations / 20)||(numExecutions - 1) == Math.round(numberOfGenerations / 6)||(numExecutions - 1) == Math.round(numberOfGenerations / 4)||(numExecutions - 1) == Math.round(numberOfGenerations / 2)) {
                       //save last generation on file
                    this.makeCopyGeneration(listIndividuos, (numExecutions));
//test                    this.makeCopyGeneration(ordenadosPorFitness, (numExecutions)); 
                    
                    numExecutions++;  
                    listIndividuos = NeatControl.getInstance().getNewPopulation(listIndividuos, sizeOfPopulation, numOfBestToBeSelected, numExecutions);
                    
                    System.out.println("List created for New Generation");
                    this.printList(listIndividuos, numExecutions-1);
                    
                    System.out.println("-------------------END FOR GENERATION "+ (numExecutions-1)+" ------------------");
//test                    listIndividuos = NeatControl.getInstance().getNewPopulation(ordenadosPorFitness, sizeOfPopulation, numOfBestToBeSelected);

                   // }
                }
                
                
                
                wr.append("Melhor da ultima geracao: ");
                wr.append("Geração " + (numExecutions - 1) + ":");
                wr.append("Melhor fitness: " + best[numExecutions - 1].getFitness());
                wr.append("Energy: " + best[numExecutions - 1].getTotalenergy());
                wr.append("Trip Time: " + best[numExecutions - 1].getTotaltriptime());
                wr.append("Completed People: " + best[numExecutions - 1].getTotalpeople());
                wr.append("Genes: " + Arrays.toString(best[numExecutions - 1].getCromossomo().getGenes()));
                wr.append("Born in generation: " + best[numExecutions - 1].getBornGeneration());
                wr.append("Father 1's Genes: " + Arrays.toString(best[numExecutions - 1].getFatherCromossomo1().getGenes()));
                wr.append("Father 2's Genes: " + Arrays.toString(best[numExecutions - 1].getFatherCromossomo2().getGenes()));


                double bestFitness = 0;
                Individuo bestIndividuo = null;
                int ger = 0;
                int bestGer = 0;
                System.out.println("Final do arquivo");
                for(int contIndi = 0; contIndi<best.length;contIndi++){
                //for (Individuo best1 : best) {
                    Individuo best1 = best[contIndi];
                    double fitBest = best1.getFitness();
                    System.out.println(contIndi+": ");
                    System.out.println("ger: " + ger);
                    System.out.println("Fitness "+ fitBest);
                    if (fitBest >= bestFitness) {
                        bestIndividuo = best1;
                        bestFitness = fitBest;
                        bestGer = ger;
                    }
                    ger++;
                //}
                }
                
                this.observerAgent.sendLog(LogObserverAgent.Action.finishGeneticAlgorithm, 
                    LogObserverAgent.TypeLog.INFO,
                    LogObserverAgent.MethodName.observerLoop,
                    "451", LogObserverAgent.Resource.generation, 
                    "Best Generation " + bestGer + "Best fit " + bestIndividuo.getFitness());
                    
                
                wr.append("Melhor de todas as gerações: ");
                wr.append("Geração " + bestGer + ":");
                wr.append("Fitness: " + bestIndividuo.getFitness());
                wr.append("Energy: " + bestIndividuo.getTotalenergy());
                wr.append("Trip Time: " + bestIndividuo.getTotaltriptime());
                wr.append("Completed People: " + bestIndividuo.getTotalpeople());
                wr.append("Genes: " + Arrays.toString(bestIndividuo.getCromossomo().getGenes()));
                wr.append("Born in generation: " + bestIndividuo.getBornGeneration());
                wr.append("Father 1's Genes: " + Arrays.toString(bestIndividuo.getFatherCromossomo1().getGenes()));
                wr.append("Father 2's Genes: " + Arrays.toString(bestIndividuo.getFatherCromossomo2().getGenes()));

                double worstFitness = 1000000000;
                Individuo worstIndividuo = null;
                ger = 0;
                int worstGer = 0;
                for (Individuo worst1 : worst) {
                    double fitWorst = worst1.getFitness();
                    if (fitWorst <= worstFitness) {
                        worstIndividuo = worst1;
                        worstFitness = fitWorst;
                        worstGer = ger;
                    }
                    ger++;
                }
                wr.append("Pior de todas as gerações: ");
                wr.append("Geração " + worstGer + ":");
                wr.append("Fitness: " + worstIndividuo.getFitness());
                wr.append("Energy: " + worstIndividuo.getTotalenergy());
                wr.append("Trip Time: " + worstIndividuo.getTotaltriptime());
                wr.append("Completed People: " + worstIndividuo.getTotalpeople());
                wr.append("Genes: " + Arrays.toString(worstIndividuo.getCromossomo().getGenes()));

                wr.append("FIM: " + new Date(System.currentTimeMillis()) + "\r\n");

                wr.close();
                bw.close();
                
                //save last generation on file
                //this.makeCopyGeneration(ordenadosPorFitness, ger);
            } catch (IOException e) {
                System.out.println("Error" + e);
            }
            
        }  finally {
           
        }
    }
    
//   public int readResultSimulationTest(){
//      return (int) (Math.random() * this.simulation.getNumberOfPopulation());
//   }
    //NEED TO BE ADAPT - depends on application     
    public abstract double readResultSimulation() throws FIPAException;
   

    /*Must review the use of answerMessage between observer agent and smart things 
    - when observer agent sends an "initSimulation" message, smart thing
    execute their cycles again, but this new execution does not interfer on
    results (it is ok, but why make them to execute an unecessary cycle*/
    private void answerMessage(String content) throws FIPAException {
        //"initSimulation"
        // System.out.println("OBSERVER LOOP: ANSWER MESSAGE");
        FIoTMessage msg = new FIoTMessage();
        msg.setContent(content);
        msg.setIdSender(adressToAnswer);
        
        this.observerAgent.sendLog(LogObserverAgent.Action.sendMsgToSmartThing, 
                    LogObserverAgent.TypeLog.INFO,
                    LogObserverAgent.MethodName.answerMessage,
                    "555", LogObserverAgent.Resource.smartthing, 
                    "Msg to "+adressToAnswer + " - "+ content);
                    
        this.observerAgent.getMsgController().sendFIoTMessage(msg);
       // this.observerAgent.sentMsgToSelectRecipient(this.adressToAnswer, "initSimulation");
    }
    
    
    private void printList(List<Individuo> list, int generation){
        if (list != null && !list.isEmpty()) {
                int contI = 0;
                    for (Individuo indi : list) {
                        System.out.println("Indi " + contI + ": ");
                        System.out.println("Fitness: " + indi.getFitness());
                      //  System.out.println("Energy: " + indi.getTotalenergy());
                      //  System.out.println("Trip Time: " + indi.getTotaltriptime());
                      //  System.out.println("Completed People: " + indi.getTotalpeople());
                        System.out.println("Genotype: " + Arrays.toString(indi.getCromossomo().getGenes()));
                        System.out.println("\n");
                        contI++;
                    }
                
            }
    }
    
    private void makeCopyGeneration(List<Individuo> list, int generation){
        FileWriter w2 = null;
        try {
            File f2 = new File("3105Generation" + generation+ ".txt");
            w2 = new FileWriter(f2);
            BufferedWriter bw2 = new BufferedWriter(w2);
            PrintWriter wr2 = new PrintWriter(bw2);
            wr2.append("INICIO: " + new Date(System.currentTimeMillis()) + "\r\n");
            wr2.append("INDIVIDUALS OF LAST GENERATION: " + generation);
            wr2.append("\n");
            if (list != null && !list.isEmpty()) {
                int contI = 0;
                    for (Individuo indi : list) {
                        wr2.append("Indi " + contI + ": ");
                        wr2.append("Fitness: " + indi.getFitness());
                       // wr2.append("Energy: " + indi.getTotalenergy());
                       // wr2.append("Trip Time: " + indi.getTotaltriptime());
                       // wr2.append("Completed People: " + indi.getTotalpeople());
                        wr2.append("Genotype: " + Arrays.toString(indi.getCromossomo().getGenes()));
                        wr2.append("\n");
                        contI++;
                    }
                
            }
            
            wr2.close();
            bw2.close();
        } catch (IOException ex) {
            Logger.getLogger(ObserverLoop.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                w2.close();
            } catch (IOException ex) {
                Logger.getLogger(ObserverLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
