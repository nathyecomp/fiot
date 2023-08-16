/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.gui;

import fiot.agents.controller.Controller;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nathalia
 */
public class PanelControl {
   
    private static PanelControl instance;
    MainPanel panel;

    private PanelControl(){
        
    }
    public static PanelControl getInstance(){
           // Verifica se a variável possui algum valor,caso não, é criada a instancia.
           if (instance == null) {
               instance = new PanelControl();
           }
           // Se a variavel possui algum valor, é retornado para quem está pedindo
           return instance;
     }

    public MainPanel getPanel() {
        return panel;
    }

    public void setPanel(MainPanel panel) {
        this.panel = panel;
    }
    
    
    
}