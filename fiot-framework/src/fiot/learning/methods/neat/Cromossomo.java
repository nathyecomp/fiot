/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.learning.methods.neat;



import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
public class Cromossomo {

    private double[] genes;

    public Cromossomo(double[] genes) {
        this.genes = genes;
    }

    public int getTam(){
        return this.genes.length;
    }

    public double[] getGenes() {
        return this.genes.clone();
    }

    public double getGene(int index){
        return this.genes[index];
    }

    public String getStringGenes(){
        String genesString = String.valueOf(genes[0]);
        
        for(int c = 1; c<genes.length; c++){
            genesString+=";";
            genesString+=String.valueOf(genes[c]);
        }
        return genesString;
    }
    
    public void setGenes(double[] genes) {
        this.genes = genes;
    }

    public void setGen(int index, double gen){
        this.genes[index] = gen;
    }
}
