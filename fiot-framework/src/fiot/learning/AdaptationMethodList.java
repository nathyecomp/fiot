/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.learning;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nathalia
 */
public class AdaptationMethodList {
    private static AdaptationMethodList instance;
    private final Map<String, AdaptationMethod> adaptationList;

    private AdaptationMethodList(){
        this.adaptationList = new HashMap<>();
    }
    public static AdaptationMethodList getInstance(){
           // Verifica se a variável possui algum valor,caso não, é criada a instancia.
           if (instance == null) {
               instance = new AdaptationMethodList();
           }
           // Se a variavel possui algum valor, é retornado para quem está pedindo
           return instance;
     }
    
    public void addMethod(String typeMethod, AdaptationMethod method){
           if(!this.adaptationList.containsKey(typeMethod)){
              this.adaptationList.put(typeMethod, method);
           }
    }
    
    public void changeMethod(String typeMethod, AdaptationMethod method){
        if(this.adaptationList.containsKey(typeMethod)){
            this.adaptationList.remove(typeMethod);
            this.adaptationList.put(typeMethod, method);
        }
    }
    
    public AdaptationMethod getMethod(String typeMethod){
        return this.adaptationList.get(typeMethod);
    }
}
