/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instance.agent.behaviors;

import fiot.agents.behaviors.*;
import fiot.agents.message.FIoTMessage;
import fiot.gui.PanelControl;
import jade.core.Agent;
import jade.domain.FIPAException;

/**
 * Behavior of ObserverAgent
 * @author Nathalia
 * @version 1.0
 */
public class ObserverLoop_TrafficLight extends ObserverLoop {

    public ObserverLoop_TrafficLight(Agent a) {
        super(a);
    }

    @Override
     public double readResultSimulation() throws FIPAException {
        FIoTMessage msgFromControl = null;
        while (msgFromControl == null) {
             FIoTMessage msg = this.observerAgent.getMsgController().readFIoTMessage();
            if(PanelControl.getInstance().getPanel().isDraw())
             PanelControl.getInstance().getPanel().repaint();

            if (msg != null) {
                adressToAnswer = msg.getIdSender();
                String[] itens = msg.getContent().replaceAll(" ", "").split(";");
                double timeSimulation = Double.parseDouble(itens[1]);
                double numCompletedCars = Double.parseDouble(itens[2]);
                double numCompletedAmbulances = Double.parseDouble(itens[3]);
              
                //Giving more value for experiments that the number of completed
                //ambulances is higher
                return ((numCompletedCars-numCompletedAmbulances)+100*numCompletedAmbulances);
                //insertVector(sender.getLocalName(), msg.getContent());
            }
        }
        return 0.0;
    }

}
