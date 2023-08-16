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
public class ObserverLoop_MerryChristmas extends ObserverLoop {

    public ObserverLoop_MerryChristmas(Agent a) {
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
                int timeSimulation = Integer.parseInt(itens[1]);
              //  double completedPeople = Double.parseDouble(itens[2]);
              //  double totalTimeTrip = Double.parseDouble(itens[3]);
                double totalEnergy = Double.parseDouble(itens[2]);
              //  double totalPeople = Double.parseDouble(itens[5]);
             //   double totalSmartLights = Double.parseDouble(itens[6]);
                
              //  double percentualPeople = (completedPeople* 100)/totalPeople;
                //double percentualEnergy = (totalEnergy *100)/(timeSimulation*totalSmartLights);
                //adicionei o gasto de fala ao de energia
             //   double percentualEnergy = (totalEnergy *100)/((timeSimulation*totalSmartLights)+(timeSimulation*totalSmartLights/10));
            //    double percentualTrip = (totalTimeTrip*100)/((timeSimulation+timeSimulation/2)*totalPeople);
                //double percentualTrip = ((totalTimeTrip*100)/((3*timeSimulation)*totalPeople));
                //A seguranca das pessoas é o mais importante
                double fitnessCalculated =  totalEnergy;
                
                
                
                
                
                
                
                
                
                
                /*APAGAR - PROVISORIO!!!*/
               // this.setCompletedPeople(completedPeople);
              //  this.setTotaltriptime(totalTimeTrip);
                this.setTotalenergy(totalEnergy);
                //APAGAR ATE AQUI
                
                //Giving more value for experiments that the number of completed
                //ambulances is higher
                //Quanto maior o fitness, melhor
                //> numCompletedPeople
                //< totalTimeTrip
                //< totalEnergy
              //  return ((numCompletedCars-numCompletedAmbulances)+100*numCompletedAmbulances);
              return fitnessCalculated;
                //insertVector(sender.getLocalName(), msg.getContent());
            }
        }
        return 0;
    }

}
