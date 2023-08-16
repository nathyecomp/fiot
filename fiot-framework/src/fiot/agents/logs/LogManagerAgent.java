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
public class LogManagerAgent implements LogValues{
    
   public enum Action implements LogValues.Action {
        connect, sendMsgToSmartThing,receiveMsgFromSmartThing,
        createAdaptiveAgent;
    }
    public enum TypeLog implements LogValues.TypeLog{
        INFO, WARNING, ERROR;
    }
    public enum Resource implements LogValues.Resource{
         adaptiveAgent, controller, smartThing, agent;
    }
    public enum MethodName implements LogValues.MethodName{
        setup,create,read,createAdaptiveAgent;
    }
}
