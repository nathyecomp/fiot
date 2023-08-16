/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulateddevices;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nathalia
 */
public class World {
    
    //Os agentes mapeiam objetos no cenário
    Map<String, Device>  listDevice;

    private static World instance;
    
    private World(){
        listDevice = new HashMap<>();
    }
     public static World getInstance(){
           // Verifica se a variável possui algum valor,caso não, é criada a instancia.
           if (instance == null) {
               instance = new World();
           }
           // Se a variavel possui algum valor, é retornado para quem está pedindo
           return instance;
     }

     
     public void addElement(String nameAgent, Device device){
       //  Device indi = new Device();
         listDevice.put(nameAgent, device);
     }
     public Device getElement(String nameAgent){
         return listDevice.get(nameAgent);
     }
     
     
}
