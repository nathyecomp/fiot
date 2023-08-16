/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.agents.controller.neuralnetwork;
/**
 *
 * @author thay
 */
public class Neuron {

 //   public double entradas[];
    public double pesos[];
    public double saida = 0.0;
    //private double bias;
    public Layer layer;
    public double Isaida[]; // Vetor com um valor para cada entrada do neuronio;
    public double delta;
    public int id;

    /* Construtor */
    public Neuron(int num, int id, Layer layer) {

      //  bias = -1.0;
        this.id= id;
        this.layer = layer;
        this.setPesos(num, id);
        
        
     //   entradas = new double[num];
       // pesos = new double[num+1];  // Pesos para todas as entradas mais o bias;
       // this.geraPesos(num+1);      // Preenche o vetor de pesos com valores aleatorios;
    }

    /* Funcao de ativacao SIGMOIDE */
    public double sigmoide(double num) {
        return (1/(1 + Math.exp(-0.5 * num)));
       // return num+1;
    }
    /* Funcao de ativacao Binary (degrau) */
    public double binary(double num) {
        if(num < 0){
            return 0;
        }
        else return 1;
    }
    /* Funcao de ativacao rectified linear unit (ReLU) */
    public double relu(double num) {
        if(num < 0){
            return 0;
        }
        else return num;
    }

    private void setPesos(int num, int position){
        this.pesos = new double[num];
        int numPeso = 0;
        for(int i = num*position; i<(num*position+num); i++){
            this.pesos[numPeso] = layer.getWeight(i);
            numPeso++;
        }
    }

    public double processar(String activationFunctionName) {

        double x = somatorio();
        if(activationFunctionName.contains("binary")){
            saida = binary(x);
        }
        else if(activationFunctionName.contains("relu")){
            saida = relu(x);
        }
        else{
            saida = sigmoide(x);
        }
        return saida;
    }

    public double somatorio() {
         /*Start the sum with -1? So, I can assure that if all inputs are
        zero, the sigmoid output will also be zero.*/
        double soma = 0;
// Cada indice do Isaida equivale a um valor na entrada de um neuronio;
        Isaida = new double[layer.entradas.length];  // Mais um, devido ao bias;
        for(int i = 0; i < layer.entradas.length; i++) {
     
            Isaida[i] = layer.entradas[i]*pesos[i];
            soma += layer.entradas[i] * pesos[i];
        }
//        Isaida[Isaida.length-1] = bias * pesos[pesos.length-1];
//        soma += bias * pesos[pesos.length-1];
        
        return soma;
    }

//    public void setPeso(int entrada, double valor){
//        pesos[entrada] = valor;
//    }
//
//    public double getPeso(int entrada){
//        return pesos[entrada];
//    }
//
//    private void geraPesos(int num)
//    {
//        for(int i = 0; i < num; i++)
//        {
//            this.setPeso(i, Math.random() * 0.5);
//        }
//    }
}
