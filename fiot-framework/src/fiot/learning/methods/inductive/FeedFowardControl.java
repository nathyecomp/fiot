/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.learning.methods.inductive;

import fiot.agents.controller.Controller;
import fiot.learning.AdaptationMethod;
import fiot.learning.Simulation;

/**
 *
 * @author Nathalia
 */
public class FeedFowardControl implements AdaptationMethod {
    double eqm_atual = 1.0;
    double eqm_anterior = 987654321987654.0;
    private static FeedFowardControl instance;
    Simulation simulation;

    private FeedFowardControl(){
        simulation = Simulation.getInstance();
    }
             // Método público estático de acesso único ao objeto!
     public static FeedFowardControl getInstance(){
           // Verifica se a variável possui algum valor,caso não, é criada a instancia.
           if (instance == null) {
               instance = new FeedFowardControl();
           }
           // Se a variavel possui algum valor, é retornado para quem está pedindo
           return instance;
     }
     
//     public void executeStandart(double[] inputValues, double expectedValue){
//         //Define saida desejada
//               double yDesejado = expectedValue;
//             //   System.out.println("Saida"+yDesejado);
//
//                for (int cont2 = 0; cont2 < camadas.length; cont2++) {
//                    if (cont2 == 0) {
//                        for (int cont = 0; cont < camadas[0].entradas.length; cont++) {
//                            camadas[0].entradas[cont] = arquivo.conjuntoDeDados.get(padrao)[cont];
//                        }
//                    } else
//                        camadas[cont2].entradas = camadas[(cont2 - 1)].saidas;
//
//                    for (int cont = 0; cont < camadas[cont2].getNumeroDeNeuronios(); cont++) {
//
//                        camadas[cont2].neuronios[cont].processar();
//                        camadas[cont2].saidas[cont] = camadas[cont2].neuronios[cont].saida;
//                    }
//                }
//     }
    @Override
    public Controller getNewController(Controller oldController) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
