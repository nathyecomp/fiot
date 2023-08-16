package fiot.test.NeatControl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import fiot.learning.Simulation;
import fiot.learning.methods.neat.Individuo;
import fiot.learning.methods.neat.NeatControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nathalia
 */
public class NeatControlTest {
    NeatControl neat;
    List<Individuo> listIndividuos;
    List<Individuo> listIndividuosNewPopulation;
    List<Individuo> selectingBest;
    int numberOfPopulation = 10;
    int numgenes = 6;
    int valueMaxGenes = 5;
    int bestTobeSelected = 2;
    int numOfChildren = 4;
    int rateMutation = 10;
    Simulation sim = Simulation.getInstance();
    public NeatControlTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        sim.setNumberOfPopulation(numberOfPopulation);
        sim.setNumGenes(numgenes);
        sim.setValueMaxOfGene(valueMaxGenes);
        sim.setNumOfChildrens(numOfChildren);
        sim.setNumOfBestToBeSelected(bestTobeSelected);
        sim.setRateMutation(rateMutation);
        neat = NeatControl.getInstance();  
        listIndividuos = NeatControl.getInstance().getFirstPopulation(numberOfPopulation);
        
        double fitness = 0;
      // System.out.println(listIndividuos.get(0).getCromossomo().getStringGenes());
        System.out.println("First Population: ");
        System.out.println("-----------------------------------------------------");
        for(int c = this.numberOfPopulation-1; c>= 0; c--){
            listIndividuos.get(c).setFitness(fitness);
            System.out.println(listIndividuos.get(c).getCromossomo().getStringGenes());
            System.out.println("Fit "+ listIndividuos.get(c).getFitness());
            fitness++;
            if(fitness==Math.round(this.numberOfPopulation/2)){
                fitness = 0;
            }
        }
        System.out.println("//-------------------------------------------------------//");
        selectingBest = new ArrayList<>();
        
        Collections.sort(listIndividuos);
        System.out.println("First Population after sort: ");
         System.out.println("-----------------------------------------------------");
        for(int c = this.numberOfPopulation-1; c>= 0; c--){
            System.out.println(listIndividuos.get(c).getCromossomo().getStringGenes());
            System.out.println("Fit "+ listIndividuos.get(c).getFitness());
        }
        System.out.println("//-------------------------------------------------------//");
        
        int cont = listIndividuos.size()-1;
        for(int a = 0; a<this.bestTobeSelected; a++){
             Individuo indi = listIndividuos.get(cont);
             selectingBest.add(indi);
             cont--;
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void firstGenerationSize(){
        assertEquals(this.numberOfPopulation, listIndividuos.size());
        
        
    }
    @Test
    public void individualCromossomeSize(){
        Individuo indi1 = listIndividuos.get(0);
        assertEquals(this.numgenes, indi1.getCromossomo().getTam());
      //  System.out.println(indi1.getCromossomo().getStringGenes());
    }
    
    @Test
    public void sizeOfVectorOfBestSelected(){
         assertEquals(bestTobeSelected,selectingBest.size());
    }
    @Test
    public void rankinginIndividuals(){
        
        
        double fitnessBest = selectingBest.get(0).getFitness();
        double fitnessSecondBest = selectingBest.get(0).getFitness();
        
        boolean verify = fitnessBest>=fitnessSecondBest;
        assertTrue(verify);
//        }
//        System.out.println("Best Selected ");
//        System.out.println("------------------------------------------------------------");
//        for(int c = 0; c<selectingBest.size(); c++){
//            System.out.println("Um "+selectingBest.get(c).getFitness());
//            System.out.println(selectingBest.get(c).getCromossomo().getStringGenes());
//        }
//        System.out.println("///-----------------------------------------------------------///");
////        System.out.println("Um "+listIndividuos.get(this.numberOfPopulation-1).getFitness());
////        System.out.println(listIndividuos.get(this.numberOfPopulation-1).getCromossomo().getStringGenes());
//                 //   listIndividuos = NeatControl.getInstance().getNewPopulation(ordenadosPorFitness, numberOfPopulation, numOfBestToBeSelected);
    }
    
    @Test
    public void generateNewPopulationSize(){
        
       listIndividuosNewPopulation = NeatControl.getInstance().getNewPopulation(listIndividuos, numberOfPopulation, bestTobeSelected,1);
        assertEquals(listIndividuosNewPopulation.size(), this.numberOfPopulation);
        System.out.println("Select best size "+ this.selectingBest.size());
       
//        System.out.println("Second Population: ");
//         System.out.println("-----------------------------------------------------");
//        for(int c = 0; c< this.listIndividuosNewPopulation.size()-1; c++){
//            System.out.println(listIndividuosNewPopulation.get(c).getCromossomo().getStringGenes());
//            System.out.println("Fit "+ listIndividuosNewPopulation.get(c).getFitness());
//        }
//        System.out.println("//-------------------------------------------------------//");

    }
    
}
