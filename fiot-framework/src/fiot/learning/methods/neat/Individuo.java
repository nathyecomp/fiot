/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.learning.methods.neat;



/**
 *
 * @author Nathy
 */
public class Individuo implements Comparable<Individuo>{

    private Cromossomo cromossomo;
    private Cromossomo fatherCromossomo1;
    private Cromossomo fatherCromossomo2;
    
    private int bornGeneration = 0; //generation that it appeared for the first time

    private double fitness = 0;

    
    

    public Individuo(Cromossomo cromossomo) {
        this.cromossomo = cromossomo;
        
        //if fathers'cromossomos have not been set, generate zeroed ones
        int sizeGens = this.cromossomo.getTam();
        this.fatherCromossomo1 = this.gerenateZeroedCromossomo(sizeGens);
        this.fatherCromossomo2 = this.gerenateZeroedCromossomo(sizeGens);
    }
    public Individuo(Cromossomo cromossomo, Cromossomo fatherCromossomo1) {
        this.cromossomo = cromossomo;
        this.fatherCromossomo1 = fatherCromossomo1;
        int sizeGens = this.cromossomo.getTam();
        this.fatherCromossomo2 = this.gerenateZeroedCromossomo(sizeGens);
    }
    public Individuo(Cromossomo cromossomo, Cromossomo fatherCromossomo1, Cromossomo fatherCromossomo2) {
        this.cromossomo = cromossomo;
        this.fatherCromossomo1 = fatherCromossomo1;
        this.fatherCromossomo2 = fatherCromossomo2;
    }
    
    private Cromossomo gerenateZeroedCromossomo(int size){
        double[] auxGenes = new double[size];
        for (int cont = 0; cont< auxGenes.length; cont++){
                double aux = 0.0;
                auxGenes[cont] = aux;
         }

         Cromossomo x = new Cromossomo(auxGenes);
         return x;
    }

    public Cromossomo getCromossomo() {
        return cromossomo;
    }

    public Cromossomo getFatherCromossomo1() {
        return fatherCromossomo1;
    }
    
    public Cromossomo getFatherCromossomo2(){
        return fatherCromossomo2;
    }

    public int getBornGeneration() {
        return bornGeneration;
    }

    public void setBornGeneration(int bornGeneration) {
        this.bornGeneration = bornGeneration;
    }
    

    public double getFitness() {
        return fitness;
    }
    
    public void setFitness(double fit){
        this.fitness = fit;
    }

    public void setCromossomo(Cromossomo cromossomo) {
        this.cromossomo = cromossomo;
    }

    @Override
     public int compareTo(Individuo other) {
       if (this.fitness < other.fitness) {
               return -1;
        }
       if (this.fitness > other.fitness) {
            return 1;
        }

        return 0;
  }
     
     /*DELETAR..ISSO EH GAMBIARRA!!! PROVISORIO PARA ANOTAR NO ARQUIVO ESSAS INFORMACOES*/
        private double totalEnergy = 0;
            private double completedPeople = 0;

                private double totalTimeTrip = 0;

    public double getTotalenergy() {
        return totalEnergy;
    }

    public void setTotalenergy(double totalenergy) {
        this.totalEnergy = totalenergy;
    }

    public double getTotalpeople() {
        return completedPeople;
    }

    public void setTotalpeople(double totalpeople) {
        this.completedPeople = totalpeople;
    }

    public double getTotaltriptime() {
        return totalTimeTrip;
    }

    public void setTotaltriptime(double totaltriptime) {
        this.totalTimeTrip = totaltriptime;
    }
                            
//DELETAR ATE AQUI PLEASEEEE!!!
    
    
//    public double calcularFitness(World mapa) {
//        fitness = 0.0;
//        for (int cont = 0; cont < cromossomo.getGenes().size() - 1; cont++) {
//            fitness += mapa.getDistsEntrePontos()[cromossomo.getGene(cont)][cromossomo.getGene(cont + 1)];
//        }
//      return fitness;
//    }
}

