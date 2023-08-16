
package fiot.agents.controller.neuralnetwork;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nathy
 */
public class Layer {

     public int numDeEntradas = 0;
     public double entradas[];
     public double saidas[];
     public double pesos[];
     public int numeroDeNeuronios = 0;
     public int numeroWeights = 0;
     public Neuron neuronios[];
     public String tipo = "";
     


     /*
      * Cria o vetor de neuronios da camada a partir do tipo da camada(entrada,
      * oculta, saida) e da quanidade de neutorios por camada.
      * Cada neuronio de uma camada recebe as mesmas entradas, por isso o vetor
      * de entradas eh um atributo da camada. =D
      */
    public Layer(String tipo, int num) {
        
        this.tipo = tipo;
        this.numeroDeNeuronios = num;
        this.neuronios = new Neuron[num];
        this.saidas = new double[this.numeroDeNeuronios];
       
       
    }

    public int getNumEntradas() {
        return numDeEntradas;
    }

    public double[] getLayerOutput(String activationFunctionName){
        
         for(int i = 0; i < neuronios.length; i++) {
            neuronios[i] = new Neuron(this.getNumEntradas(), i, this);
            neuronios[i].layer = this;
            this.saidas[i] = neuronios[i].processar(activationFunctionName);

        }
        return this.saidas;
    }

    /* Cria todos os neuronios da camada,
     * de acordo com a quantidade de entradas que a camada possui;
     */
    public void setNeuronios(int entradas) {

        this.numDeEntradas = entradas;
        this.numeroWeights = this.numDeEntradas*this.numeroDeNeuronios;
        for(int i = 0; i < neuronios.length; i++) {
           this.entradas = new double[entradas];
           this.saidas = new double[this.numeroDeNeuronios];
           this.pesos = new double[this.numeroDeNeuronios*entradas];
            neuronios[i] = new Neuron(entradas, i, this);
            neuronios[i].layer = this;

        }
    }

    public int getNumDeEntradas() {
        return numDeEntradas;
    }

    public void setNumDeEntradas(int numDeEntradas) {
        this.numDeEntradas = numDeEntradas;
    }

    
    public double[] getSaidas() {
        return saidas;
    }

    public void setSaidas(double[] saidas) {
        this.saidas = saidas;
    }
    

    public int getNumeroWeights() {
        return numeroWeights;
    }

    public void setNumeroWeights(int numeroWeights) {
        this.numeroWeights = numeroWeights;
    }

    
    public int getNumeroDeNeuronios() {
        return numeroDeNeuronios;
    }

    public void setNumeroDeNeuronios(int numeroDeNeuronios) {
        this.numeroDeNeuronios = numeroDeNeuronios;
    }

    public double[] getWeights() {
        return pesos;
    }
    public double getWeight(int i){
        return pesos[i];
    }

    public void setWeights(double[] weights) {
        this.pesos = weights;
    }

    public double[] getEntradas() {
        return entradas;
    }

    public void setEntradas(double[] entradas) {
        this.entradas = entradas;
    }
    
    
}
