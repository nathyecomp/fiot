/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.agents.logs;

import br.pucrio.les.mas.publisher.framework.LogValues;

/**
 *
 * @author nathalianascimento
 */
public class LogObserverAgent implements LogValues{
    
   public enum Action implements LogValues.Action {
        connect, chooseAdaptationMethod,changeControllerConfiguration,
        startExecutionWithControllerConfiguration,
        startExecutionWithoutAdaptation,
        readSimulationResults,
        calculateEnergy,calculatePeople,calculateTripDuration,calculateFitness,
        achieveEnergyTarget,achievePeopleTarget,
        reseiveMsgFromSmartThing,sendMsgToSmartThing,
        startGeneticAlgorithm, generateFirstPopulation, startNewGeneration, 
        selectBestFitIndividuals, finishGeneticAlgorithm;
    }
    public enum TypeLog implements LogValues.TypeLog{
        INFO, WARNING, ERROR;
    }
    public enum Resource implements LogValues.Resource{
         solution,individual,generation,dataset,agent,smartthing;
    }
    public enum MethodName implements LogValues.MethodName{
        setup,create,observerLoop,executingNeatControl, trainingNeatControl,
        readResultSimulation,answerMessage,makeCopyGeneration;
    }
}
