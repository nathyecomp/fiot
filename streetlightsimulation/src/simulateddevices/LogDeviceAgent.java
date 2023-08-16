/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulateddevices;

import br.pucrio.les.mas.publisher.framework.LogValues;

/**
 *
 * @author nathalianascimento
 */
public class LogDeviceAgent implements LogValues{
    
   public enum Action implements LogValues.Action {
        connect,sendMsg,receiveMsg,changePeoplePosition,readInputValue,receiveWirelessData,
        setOutputValue,changeEdgeLights,finishSimulation,
        reinitSimulation,calculateTimeTrip,calculateEnergy,calculatePeople,calculateEdgeLight,
        readLightSensor,detectLight,readMotionSensor,readPreviousListeningDecision,
        receiveNeuralNetworkCommand,switchLightON,
        switchLightOFF,sendWirelessData,setListeningDecision;
    }
    public enum TypeLog implements LogValues.TypeLog{
        INFO, WARNING, ERROR;
    }
    public enum Resource implements LogValues.Resource{
         person,node,edge,streetlight,ManagerAgent,ApdativeAgent,ObserverAgent,simulation;
    }
    public enum MethodName implements LogValues.MethodName{
        initAgent,msgManagerAgent,msgAdaptiveAgent,msgObserverAgent,calculePeople,
        receiveWirelessData,changeLightFromEdges,
        calculateTimeTrip,calculateEnergy,getInputs,readValuesFromInputSensors,
        processActuatorInformation;
    }
}
