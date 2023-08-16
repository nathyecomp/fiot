/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.agents.behaviors;

import fiot.agents.AdaptiveAgent;
import fiot.agents.AgentList;
import fiot.agents.controller.Controller;
import fiot.agents.controller.ControllerList;
import fiot.agents.logs.LogAdaptiveAgent;
import fiot.agents.message.FIoTMessage;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import fiot.learning.Simulation;

/**
 * Behavior of AdapgetTempive AgengetTemps
 * @author NagetTemphalia
 * @version 1.0
 */
public class ControlLoop extends CyclicBehaviour{
    AgentList agentList;
    AdaptiveAgent adaptiveAgent;
    Simulation simulation;
    int numExecutedCycles;
    public ControlLoop(Agent a){
        super(a);
        adaptiveAgent = (AdaptiveAgent)a;
        agentList = AgentList.getInstance();
        simulation = Simulation.getInstance();
        numExecutedCycles = 0;
    }
    
    @Override
    public void action() {
        while (true) {
           
            try {
               // this.numMsg = 0;
                this.read();
            } catch (FIPAException ex) {
                Logger.getLogger(CreateNewAdaptiveAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }
    
    private boolean read() throws FIPAException {
        this.adaptiveAgent.sendLog(LogAdaptiveAgent.Action.waitMsgFromSmartThing, 
                LogAdaptiveAgent.TypeLog.INFO,
                LogAdaptiveAgent.MethodName.read,
                "61",
                LogAdaptiveAgent.Resource.smartThing,
                "");
        
        FIoTMessage msg = this.adaptiveAgent.getMsgController().readFIoTMessage();
        
        
       // System.out.println("mensagem que está chegando no control loop antes do if");
        if(msg!=null && msg.getContent().length()>=3){
         //   System.out.println("Data msg: ");
           // System.out.println(msg.getDate());
       //     System.out.println(msg.getContent());
        
        }
        else System.out.println("mensagem é nula ou vazia");
//        while(numExecutedCycles!=this.simulation.getNumOfCyclesSimulation()){
//            
//        }

//        if(msg!=null){
//            System.out.println("SENDER DA MSG RECEBIDA EM CONTROL LOOP :" + msg.getIdSender());
//            System.out.println("conteudo "+ msg.getContent());
//        }
        //POR ENQUANTO- URGENTE P FUNCIONAR
        if(msg!=null && msg.getContent().length()>=3){
        //if (msg != null && adaptiveAgent.getDevice().getNameDevice().equals(msg.getIdSender())) {
         //   System.out.println("mensagem recebida pelo adaptive e "+ msg.getContent());
          //  System.out.println("Reading input ");
        //    System.out.println("Apdativre agent dentro do loop");
            this.readInputDevice(msg);
            this.simulation.incrementNumberAgentsExecution();
            numExecutedCycles++;
                //this.numMsg++;
                //previousAgent = msg.getSender().getLocalName();
               // System.out.println("Entrando no loop ppela "+ this.numMsg);
                //   System.out.println("Entrei 3");
               // AID sender = msg.getSender();
                // System.out.println("Entrei 4");
                // System.out.println("Sender local name "+ sender.getLocalName());
              //  return this.createAgent(msg);
                //insertVector(sender.getLocalName(), msg.getContent());
          //  }
        }
        
        return true;
    }

        public boolean readInputDevice(FIoTMessage msgFromDevice) throws FIPAException{
         this.adaptiveAgent.sendLog(LogAdaptiveAgent.Action.receiveInputDataFromSmartThing, 
                LogAdaptiveAgent.TypeLog.INFO,
                LogAdaptiveAgent.MethodName.readInputDevice,
                "112",
                LogAdaptiveAgent.Resource.smartThing,
                "Msg From "+ msgFromDevice.getIdSender()+" input "+ msgFromDevice.getContent());
        
        String deviceLocalName = msgFromDevice.getIdSender();
        
        String inputString[] = msgFromDevice.getContent().replaceAll(" ", "").split(";");
        double input[] = new double[inputString.length];
        for(int cont = 0; cont< inputString.length; cont++){
            input[cont] = Double.parseDouble(inputString[cont]);
           // System.out.println(cont+ ": "+ input[cont]);
        }
           
        
       // String agentDeviceName = deviceLocalName+"Agent";
        //String agentDeviceContent = "AdaptiveAgent";
        this.adaptiveAgent.setInput(input);
        
        this.adaptiveAgent.sendLog(LogAdaptiveAgent.Action.useControllerToGetOutput, 
                LogAdaptiveAgent.TypeLog.INFO,
                LogAdaptiveAgent.MethodName.readInputDevice,
                "133",
                LogAdaptiveAgent.Resource.controller,
                this.adaptiveAgent.getDevice().getControllerID());
        
        Controller control = ControllerList.getInstance().getController(this.adaptiveAgent.getDevice().getControllerID());
        
        //IF THE DEVICE DOESN'T HAVE ANY ACTUATOR, THE ADAPTIVE AGENT WILL ONLY SEND A MESSSAGE WITH "OK"
        double[] output = control.getOutput(input);
      //  double[] output = this.getOutputtodeleteafter(input);
        this.adaptiveAgent.setOutput(output);
        this.agentList.changeAgent(this.adaptiveAgent.getNameAgent(), this.adaptiveAgent);
        String messageForDevice = "";
//        if(this.adaptiveAgent.getMsgController().getType().equals("SOCKET")){
//            messageForDevice = "OUTPUT:";
//        }
//        
        for(int b = 0; b< output.length-1; b++){
             DecimalFormat df = new DecimalFormat("#.##");

             String m = df.format(output[b]);
             m= m.replaceAll(",", ".");
//                    genesList[cont2] = Double.valueOf(weight);
//            String m = String.valueOf(output[b]);
            messageForDevice+=(m+";");
        }
         String m = String.valueOf(output[output.length-1]);
         messageForDevice+=m;
         
        // this.adaptiveAgent.sentMsgToSelectRecipient(deviceLocalName, messageForDevice);
         FIoTMessage msg = new FIoTMessage();
         msg.setContent((messageForDevice+"*"));
         msg.setIdSender(deviceLocalName);
         
         
         this.adaptiveAgent.sendLog(LogAdaptiveAgent.Action.sendOutputToSmartThing, 
                LogAdaptiveAgent.TypeLog.INFO,
                LogAdaptiveAgent.MethodName.readInputDevice,
                "170",
                LogAdaptiveAgent.Resource.smartThing,
                "To "+ deviceLocalName+ " with " + msg.getContent());
         
        //    System.out.println("ADAPTIVE AGENT ENVIANDO MENSAGEM");
         //   System.out.println(msg.getContent());
          //  System.out.println(msg.getIdSender());
         //TEM QUE ALTERAR O TRAFFIC COM ESSA ADIÇÃO DE * PRA INDICAR O FINAL DA MSG
         this.adaptiveAgent.getMsgController().sendFIoTMessage(msg);

//        if(!this.agentList.containsAdaptiveAgent(agentDeviceName)){
//            Device device = new Device(deviceLocalName, deviceLocalName, controllerID);
//            AdaptiveAgent newAgent = new AdaptiveAgent(device, agentDeviceName, agentDeviceContent);
//            InitAgent.init(newAgent, agentDeviceName, agentDeviceContent);
//            this.agentList.addAgent(agentDeviceName, newAgent);
//            this.manager.sentMsgToSelectRecipient(deviceLocalName, agentDeviceName);
//        }
        return true;
    }
        
        public double[] getOutputtodeleteafter(double[] in){
            double[] valueSaida = new double[1];
            // valores ideias
            double temperature = 12;
            double humidity = 95;
            double hydrogen =100;
            double othergases = 500;
            double methane =200;
            double luminosity =5;
            double previoustimespoiled = 30;
            
            double maxValue = 30*temperature+5*humidity+hydrogen+othergases/100+methane+20*luminosity+previoustimespoiled;
            //maxvalue -1/ 
            //valueread - value saida
            double valueRead = 30*in[0]+5*in[1]+in[2]+in[3]/100+in[4]+20*in[5]+in[6];
            valueSaida[0] = valueRead/maxValue;
            return valueSaida;
        }
        
        public double getTemp(double value){
//            double temperature = (float)value * 0.01 - 40;
//            return temperature;
            return value;
          //return  value * 0.01 - 40;
        }
        public double getHum(float val, int decimalPoint){
      //  double finalValue = 0;
        float humidity = (float) (-4.0 + 0.0405 * val + -0.0000028 * val * val);
      //  finalValue = this.convertDecimalPoints(humidity, decimalPoint);
        return humidity;
    }
    
}
