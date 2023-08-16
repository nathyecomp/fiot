/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiot.agents.behaviors;

import fiot.agents.AgentList;
import fiot.agents.AdaptiveAgent;
import fiot.agents.ManagerAgent;
import fiot.agents.InitAgent;
import fiot.agents.device.Device;
import fiot.agents.logs.LogManagerAgent;
import fiot.agents.message.AgentAdress;
import fiot.agents.message.FIoTMessage;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Behavior of Manager Agent
 * @author Nathalia
 * @version 1.0
 */
public class CreateNewAdaptiveAgent extends CyclicBehaviour {

    int numMsg;
    ManagerAgent manager;
    AgentList agentList;
    String previousAgent = "";

    public CreateNewAdaptiveAgent(Agent a) {
        super(a);
        manager = (ManagerAgent) a;
        agentList = AgentList.getInstance();
        this.numMsg = 0;
        
    }

    @Override
    public void action() {
        System.out.println("creating adaptive agents for devices");
        while (true) {
            try {
                this.numMsg = 0;
                this.read();
            } catch (FIPAException ex) {
                Logger.getLogger(CreateNewAdaptiveAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }

    private boolean read() throws FIPAException {
       // FIoTMessage acFromDevice = null;
        while (this.numMsg < 1) {
            FIoTMessage msg = this.manager.getMsgController().readFIoTMessage();
            if (msg != null){

                String sender = msg.getIdSender();
                
                this.manager.sendLog(LogManagerAgent.Action.receiveMsgFromSmartThing, LogManagerAgent.TypeLog.INFO,LogManagerAgent.MethodName.read,"61", LogManagerAgent.Resource.smartThing, "Received message from: " + sender);

                //System.out.println("CREATE NEW ADAPTIVE AGENT - MSG SENT FROM "+ sender);
//               if(msg.getContent().contains("Name:") || !previousAgent.equals(msg.getSender().getLocalName()))
            if(msg.getContent().contains("Name:") || !previousAgent.equals(sender))

               {
                this.numMsg++;
                previousAgent = sender;
               // System.out.println("Entrando no loop ppela "+ this.numMsg);
                //   System.out.println("Entrei 3");
               // AID sender = msg.getSender();
                // System.out.println("Entrei 4");
                // System.out.println("Sender local name "+ sender.getLocalName());
                return this.createAgent(msg);
                //insertVector(sender.getLocalName(), msg.getContent());
            }
        }
        }
        
        return true;
    }
    
    private boolean createAgent(FIoTMessage msgFromDevice) throws FIPAException{
        
        AgentAdress adress;
        String deviceLocalName;
        String controllerID;
        String deviceNameForAgent;
//        String sender = msgFromDevice.getIdSender();
        deviceLocalName = msgFromDevice.getIdSender();
        
      //  System.out.println("MESSAGE RECEIVED FROM DEVICE IS "+ msgFromDevice.getContent());
        if(msgFromDevice.getContent().contains("Name:")){
            String msg = msgFromDevice.getContent().replaceAll(" ", ""); 
            String[] pieces = msg.split(";");
            String nameA = pieces[0].replace("Name:", "");
            deviceNameForAgent= nameA;
            controllerID = pieces[1];
        }
        else{       
            controllerID = msgFromDevice.getContent().replaceAll(" ", "");
            deviceNameForAgent = deviceLocalName;
        }
        String ip = this.manager.getAdress().getIp();
        String port = "";
        String agentDeviceName=deviceNameForAgent+"Agent";
        port = agentDeviceName;
            if(!this.manager.getAdress().getPort().isEmpty()){
                port = this.agentList.getPortForAgent(ip);
                agentDeviceName = ip+"-"+port;
            }
        
    //    String agentDeviceName = deviceNameForAgent;
        String agentDeviceContainer = "AdaptiveAgent";

        if(!this.agentList.containsAdaptiveAgent(agentDeviceName)){
            adress = new AgentAdress(agentDeviceContainer, agentDeviceName, ip, port);
            Device device = new Device(msgFromDevice.getIp(), msgFromDevice.getPort(),deviceLocalName, controllerID);
            AdaptiveAgent newAgent = new AdaptiveAgent(device, this.manager.getMsgController().getType(), adress);
            
            this.manager.sendLog(LogManagerAgent.Action.createAdaptiveAgent, LogManagerAgent.TypeLog.INFO,LogManagerAgent.MethodName.createAdaptiveAgent,"125", LogManagerAgent.Resource.adaptiveAgent, "Create adaptive agent at: " + ip +"-"+port+" with controller "+ controllerID);

                            
            InitAgent.init(newAgent, agentDeviceName, agentDeviceContainer);
            this.agentList.addAgent(agentDeviceName, newAgent);
            
            FIoTMessage msgForDevice = new FIoTMessage();
            //System.out.println("name DO DEVICE QUE VAI RECEBER A MSG DE Manager "+device.getNameDevice() );
            //System.out.println("ip DO DEVICE QUE VAI RECEBER A MSG DE Manager "+device.getIp() );
            //System.out.println("port DO DEVICE QUE VAI RECEBER A MSG DE Manager "+device.getPort() );
            msgForDevice.setIp(device.getIp());
            msgForDevice.setPort(device.getPort());
            msgForDevice.setContent(agentDeviceName);
            //System.out.println("imprimindo agende "+ newAgent);
            //System.out.println("MESSAGE SEND FOR DEVICE IS "+ msgForDevice.getContent());
            
           /*NAO ESTOU CONSEGUINDO QUEBRAR A STRING NO ARDUINO, ENTAO SO PARA ESSE EXPERIMENTO
            ESTOU COLOCANDO O CONTEUDO DA MENSAGEM SO COM A PORTA*/
            String cN = port+"*";
            msgForDevice.setContent(cN);
            this.manager.getMsgController().sendFIoTMessage(msgForDevice);
            
            this.manager.sendLog(LogManagerAgent.Action.sendMsgToSmartThing, LogManagerAgent.TypeLog.INFO,LogManagerAgent.MethodName.createAdaptiveAgent,"147", LogManagerAgent.Resource.smartThing, "Content "+ msgForDevice.getContent());

           // this.manager.sentMsgToSelectRecipient(deviceLocalName, agentDeviceName);
        }
        return true;
    }
}
