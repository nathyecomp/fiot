/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class was created to help on creatio of future features for framework,
 * as to give support for heterogeneous agents. At the moment, only one controller
 * has been used
 * @version 1.0
 * @author Nathalia
 */
public class ControllerList {
   
    private static ControllerList instance;
    private final Map<String, Controller> controllerList;

    private ControllerList(){
        this.controllerList = new HashMap<>();
    }
    public static ControllerList getInstance(){
           // Verifica se a variável possui algum valor,caso não, é criada a instancia.
           if (instance == null) {
               instance = new ControllerList();
           }
           // Se a variavel possui algum valor, é retornado para quem está pedindo
           return instance;
     }
    
    public void addController(String typeAgent, Controller controller){
           if(!this.controllerList.containsKey(typeAgent)){
              this.controllerList.put(typeAgent, controller);
           }
    }
    
    public void changeController(String typeAgent, Controller controller){
        if(this.controllerList.containsKey(typeAgent)){
            this.controllerList.remove(typeAgent);
            this.controllerList.put(typeAgent, controller);
        }
    }
    
    public Controller getController(String typeAgent){
        return this.controllerList.get(typeAgent);
    }
}
