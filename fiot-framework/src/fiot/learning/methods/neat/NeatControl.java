/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.learning.methods.neat;

import fiot.agents.controller.Controller;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fiot.learning.AdaptationMethod;
import fiot.learning.Simulation;

/**
 *
 * @author nathi_000
 */
public class NeatControl implements AdaptationMethod {
    
    Map<String, double[]> genesPorTipoAgente;
    double[] genes;
    private static NeatControl instance;
    long timeSimulation;
    double fitnessSimulation;
    Simulation simulation;
//    int numGenes = 500;
//    double valueMaxOfGene = 5.0;
//    double rateMutation = 10;
    private NeatControl(){
        simulation = Simulation.getInstance();
        genesPorTipoAgente = new HashMap<>();
        genes = new double[simulation.getNumGenes()];
    }
         // Método público estático de acesso único ao objeto!
     public static NeatControl getInstance(){
           // Verifica se a variável possui algum valor,caso não, é criada a instancia.
           if (instance == null) {
               instance = new NeatControl();
           }
           // Se a variavel possui algum valor, é retornado para quem está pedindo
           return instance;
     }
     
     public double[] getGenes(String type){
//         double[] weight = new double[10000];
//            for(int cont = 0; cont<10000; cont++){
//                weight[cont] = Math.random() * 0.5;
//            }
//          return weight;  
        // return genesPorTipoAgente.get(type);
         return this.genes;
     }
     
     public void setGenes(double[]genes){
         this.genes = genes;
     }
     
     public void setGenesType(String type, double[]genes){
         genesPorTipoAgente.put(type, genes);
     }
     
     public double[] getGenesType(String type){
        return genesPorTipoAgente.get(type);
     }

    public long getTimeSimulation() {
        return timeSimulation;
    }

    public void setTimeSimulation(long timeSimulation) {
        this.timeSimulation = timeSimulation;
    }

    public double getFitnessSimulation() {
        return fitnessSimulation;
    }

    public void setFitnessSimulation(double fitnessSimulation) {
        this.fitnessSimulation = fitnessSimulation;
    }
    
    public double getRandomWeight(){
        double weight = 0;
        double auxmat = Math.random();
        double maxValue = this.simulation.getValueMaxOfGene();
        //considering negative weights. For example, if maxValue is 5
        //0 to 5 can be considered -5 to 0. And (5 to 10) -> (0 to +5)
        double auxSize = maxValue*2;
        
        double sortedValue = auxmat * auxSize;  // 0 -> auxSize
        if (sortedValue <= maxValue){ //0 -> auxSize/2
            weight = sortedValue;
        }
        else{ //auxSize/2 -> auxSize
            weight = -(sortedValue-maxValue); // if maxValue is 2.0 and 
                                                //sortedValue is 2.1, then weight = -0.1
        }    
        // feature selection? if the weight is low, we are considering that the 
        //feature was not selected, so must be zero. (the connections among
        //neurons can remove  features)
        double forceSelectionPercentage = this.simulation.getForceSelection();
        if(forceSelectionPercentage>0){
            if (weight>0 && weight < forceSelectionPercentage*(maxValue)/100){
                weight = 0;
            }
            else if (weight<0 && weight > -(forceSelectionPercentage*(maxValue)/100)){
                weight = 0;
            }
        }
        
        DecimalFormat df = new DecimalFormat("#.#");

        String weightDecimal = df.format(Double.valueOf(weight));
        weightDecimal = weightDecimal.replaceAll(",", ".");
        weight = Double.valueOf(weightDecimal);
        return weight;
    }


    public List<Individuo> getFirstPopulation(int sizeOfPopulation) {
        List<Individuo> populacaoInicial = new ArrayList<>();
        for (int i = 0; i < sizeOfPopulation; i++) {
            double[] auxGenes = new double[simulation.getNumGenes()];
            int w = 0;
            int random = 0;
            for (int cont = 0; cont< auxGenes.length; cont++){
                    //double auxmat = Math.random();
                    //double auxSize = this.simulation.getValueMaxOfGene();
                    //double sortedValue = auxmat * auxSize; 
                    double aux = this.getRandomWeight();
                    auxGenes[cont] = aux;
            }

            Cromossomo x = new Cromossomo(auxGenes);
            Individuo individuo = new Individuo(x);
            individuo.setBornGeneration(0);
            populacaoInicial.add(individuo);
        }
       return populacaoInicial;
    }

    public List<Individuo> getNewPopulation(List<Individuo> ordenadosPorFitness, int sizeOfPopulation, int numOfBestToBeSelected, int currentGeneration) {
     //   ArrayList<Individuo> list = new ArrayList<>(ordenadosPorFitness);
        List<Individuo> selectingBest = new ArrayList<>();
        List<Individuo> newPopulation = new ArrayList<>();
        int num = 0;
        int cont = ordenadosPorFitness.size()-1;
        for(int a = 0; a<numOfBestToBeSelected; a++){
           Individuo indi = ordenadosPorFitness.get(cont);
           selectingBest.add(indi);
           cont--;
           newPopulation.add(num,indi);
           num++;
        }
      //  System.out.println("BEST SELECTED ");
      //  this.printList(selectingBest);
        //int cont = ordenadosPorFitness.size()-1;
      //  while(selectingBest.size()<numOfBestToBeSelected){
         //   Individuo indi = ordenadosPorFitness.get(cont);
       //     selectingBest.add(indi);
            
          //  cont--;
        //}
     //   int nChildrenCruz = numOfBestToBeSelected;
        
        int numChildren = ((sizeOfPopulation-numOfBestToBeSelected)/numOfBestToBeSelected);
//        for(int a = 0; a<numOfBestToBeSelected; a++){
//            newPopulation.add(selectingBest.get(a));
//        }
//        for (Individuo indi : selectingBest){
//            newPopulation.add(num,indi);
//            num++;
//        }
//        for(int i = 0; i<selectingBest.size(); i++){
         for (Individuo indi : selectingBest){
         //   newPopulation.add(num,indi);
          //  num++;
            int numGenesToMutate = (int) ((indi.getCromossomo().getTam()*this.simulation.getRateMutation())/100);
            Individuo father1 = indi;
            for(int son = 0; son<Math.round(numChildren/3); son++){
                int randBest = (int) (Math.random() * (selectingBest.size()));
                Individuo father2 = selectingBest.get(randBest);
                Individuo children1 = cruzamento(father1,father2);
                children1.setBornGeneration(currentGeneration);
                newPopulation.add(num,children1);
                num++;
            }
            for(int son = Math.round(numChildren/3); son<numChildren; son++){
                Individuo children2 =mutacaoPorInsercao(father1, numGenesToMutate);
                children2.setBornGeneration(currentGeneration);
                newPopulation.add(num,children2);
                num++;
            }
        }
        
        return newPopulation;
    }
    
     public Individuo mutacaoPorInsercao(Individuo indiOld, int quantPontos) {
        Cromossomo cromNew = new Cromossomo(indiOld.getCromossomo().getGenes());
        
        for(int i = 0; i<quantPontos; i++){
            int index = (int) (Math.random() * (cromNew.getTam()));
            double value = this.getRandomWeight();
            //double value = (Math.random() * (this.simulation.getValueMaxOfGene()));
            cromNew.setGen(index, value);
        }

        return new Individuo(new Cromossomo(cromNew.getGenes()), 
                indiOld.getCromossomo());
    }
     

    @Override
    public Controller getNewController(Controller oldController) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
   
    
  public Individuo cruzamento(Individuo pai1, Individuo pai2) {
      //  ArrayList<Individuo> filhos = new ArrayList<>();

        Cromossomo cromPai1 = new Cromossomo(pai1.getCromossomo().getGenes());
        Cromossomo cromPai2 = new Cromossomo(pai2.getCromossomo().getGenes());
        int index[] = getIndexsFirstIntersection(cromPai1, cromPai2);

        //Se nao ha genes em comum com os pais::
        if (index[0] == -1) {

            if (cromPai1.getTam() == 2) {
                index[0] = 0;
            } //Ponto de corte aleatorio
            else {
                index[0] = (int) (Math.random() * (cromPai1.getTam() - 2));
            }

            if (cromPai2.getTam() == 2) {
                index[1] = 0;
            } else {
                index[1] = (int) (Math.random() * (cromPai2.getTam() - 2));
            }
        }

        Cromossomo cromFilho1 = new Cromossomo(concateCromossomo(cromPai1, cromPai2, index[0]).getGenes());

        Individuo filho1 = new Individuo(cromFilho1, cromPai1, cromPai2);
//        filhos.add(0, filho1);
//        Cromossomo cromFilho2 = new Cromossomo(concateCromossomo(cromPai2,cromPai1, index[1]).getGenes());
//
//        Individuo filho2 = new Individuo(cromFilho2);
//        filhos.add(1, filho2);

        return filho1;
    }

    //COM EXCESSAO DO PRIMEIRO E ULTIMO
    public int[] getIndexsFirstIntersection(Cromossomo crom1, Cromossomo crom2) {

        int index[] = {-1, -1};
        for (int cont = 1; cont < crom1.getTam() - 1; cont++) {

            for (int cont2 = 0; cont2 < crom2.getTam(); cont2++) {

                if (crom1.getGene(cont) == crom2.getGene(cont2)) {
                    index[0] = cont;
                    index[1] = cont2;
                    return index;
                }
            }
        }
        return index;
    }

    public Cromossomo concateCromossomo(Cromossomo crom1, Cromossomo crom2, int index) {
        Cromossomo cromResult = new Cromossomo(crom2.getGenes());
         for (int cont = 0; cont <= index; cont++) {
            cromResult.setGen(cont, crom1.getGene(cont));
        }
        return cromResult;
    }

//    public Cromossomo getGenesBeforeIndex(Cromossomo crom1, int index) {
//        Cromossomo cromResult = new Cromossomo(crom1.getGenes());
//        for (int cont = index; cont < crom1.getTam(); cont++) {
//            //remove
//            cromResult.setGen(cont, -1);
//        }
//        return cromResult;
//    }

//    public Cromossomo getGenesAfterIndex(Cromossomo crom1, int index) {
//        Cromossomo cromResult = new Cromossomo(crom1.getGenes());
//        for (int cont = 0; cont <= index; cont++) {
//            cromResult.setGen(cont, -1);
//        }
//        return cromResult;
//    }

    
    //     public ArrayList<Individuo> cruzamento(Individuo pai1, Individuo pai2) {
//        ArrayList<Individuo> filhos = new ArrayList<Individuo>();
//
//        Cromossomo cromPai1 = pai1.getCromossomo();
//        Cromossomo cromPai2 = pai2.getCromossomo();
//        int index[] = getIndexsFirstIntersection(cromPai1, cromPai2);
//
//        //Se nao ha genes em comum com os pais::
//        if (index[0] == -1) {
//
//            if (cromPai1.getTam() == 2) {
//                index[0] = 0;
//            } //Ponto de corte aleatorio
//            else {
//                index[0] = (int) (Math.random() * (cromPai1.getTam() - 2));
//            }
//
//            if (cromPai2.getTam() == 2) {
//                index[1] = 0;
//            } else {
//                index[1] = (int) (Math.random() * (cromPai2.getTam() - 2));
//            }
//        }
//
//        Cromossomo beforeCromPai1 = new Cromossomo(getGenesBeforeIndex(cromPai1, index[0]).getGenes());
//        beforeCromPai1.setGen(beforeCromPai1.getTam(), cromPai1.getGene(index[0]));
//        Cromossomo afterCromPai2 = new Cromossomo(getGenesAfterIndex(cromPai2, index[1]).getGenes());
//        Cromossomo cromFilho1 = new Cromossomo(concateCromossomo(beforeCromPai1, afterCromPai2).getGenes());
//
//        Individuo filho1 = new Individuo(cromFilho1);
//        filhos.add(0, filho1);
//
//        Cromossomo beforeCromPai2 = new Cromossomo(getGenesBeforeIndex(cromPai2, index[1]).getGenes());
//        beforeCromPai2.setGen(beforeCromPai2.getTam(), cromPai2.getGene(index[1]));
//        Cromossomo afterCromPai1 = new Cromossomo(getGenesAfterIndex(cromPai1, index[0]).getGenes());
//        Cromossomo cromFilho2 = new Cromossomo(concateCromossomo(beforeCromPai2, afterCromPai1).getGenes());
//
//        Individuo filho2 = new Individuo(cromFilho2);
//        filhos.add(1, filho2);
//
//        return filhos;
//    }
    
    private void printList(List<Individuo> list){
        if (list != null && !list.isEmpty()) {
                int contI = 0;
                    for (Individuo indi : list) {
                        System.out.println("Indi " + contI + ": ");
                        System.out.println("Fitness: " + indi.getFitness());
                        System.out.println("Genotype: " + Arrays.toString(indi.getCromossomo().getGenes()));
                        System.out.println("\n");
                        contI++;
                    }
                
            }
    }
     
}
