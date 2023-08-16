package fiot.agents;
import jade.Boot;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.HashMap;


/**
 * @author cvaladares
 *Classe para inicializar os agentes.
 */
public class InitAgent {
    
   public static int numAgents;
    public static String nameContainerOriginal;
    public static AgentContainer controllerAgentContainer;

	public InitAgent() {
            numAgents = 0;
            nameContainerOriginal="";
            controllerAgentContainer =null;
        }
	public InitAgent(Agent agent, String nameAgent, String nameContainer) {
		setAgentInContainer(agent, nameAgent, nameContainer);
	}
	
	/**
	 * MŽtodo para adicionar agente ao container.
	 */
	private void setAgentInContainer(Agent agent, String nameAgent, String nameContainer){
		
                Runtime runtime = Runtime.instance();
            if(!nameContainer.equals(nameContainerOriginal)){
                nameContainerOriginal = nameContainer;
                Profile profile = new ProfileImpl();
                profile.setParameter(Profile.CONTAINER_NAME, nameContainer);
                controllerAgentContainer = runtime.createAgentContainer(profile);
            }
		
        
        try {
            AgentController controller;
            System.out.println("\n\n Nome do agente: "+ nameAgent);
            System.out.println("\n\n Agente: "+ agent);
            controller  = controllerAgentContainer.acceptNewAgent( nameAgent, agent);
            controller.start();
        } catch (StaleProxyException ex) {
            System.out.println("Agente nao pode ser iniciado");
        }
	}

	public static void init(Agent agent, String nameAgent, String nameContainer){
		System.out.println("agentes...");
                if(numAgents==0)
		Boot.main(new String[]{"-gui"});
                numAgents++;
		
		new InitAgent(agent, nameAgent, nameContainer);
		//new InitAgent(new AgenteCriticarCardapio(), "CriticarCardapio", "poiContainer");
	}
        
}
