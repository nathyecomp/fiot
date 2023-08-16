/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package traffic.scenario.gui;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import traffic.scenario.model.World;

/**
 *
 * @author Nathalia
 */
public class ConfigurationFile {
    
    File file;
    int numberOfPeople;
    int numberOfAmbulances;
    int maxPeopleNode;
    int maxAmbulanceLane;
    int timeOfSimulation;
    int numBrokenGlass;
    double environmentalLight;
    boolean environmentaLightIsSet;
    World world = World.getInstance();
    public ConfigurationFile(String nameFile){
      file = new File(nameFile);  
      this.init();
    }
    public ConfigurationFile(File file){
        this.file = file;
        this.init();
    }
    private void init(){                
        numberOfPeople = 0;
        numberOfAmbulances = 0;
        maxPeopleNode = 0;
        maxAmbulanceLane = 0;
        timeOfSimulation = 0; 
        environmentalLight = 0;
        environmentaLightIsSet = false;
        numBrokenGlass = 0;
    }
    
    public void setConfigurationInformations() throws FileNotFoundException, IOException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while ( ( line = bufferedReader.readLine() ) != null) {
            line = line.replaceAll(" ", "");
            if(line.contains("<NumberOfPeople>")){
                this.numberOfPeople = Integer.valueOf(line.split(":")[1]);
                this.world.setNumPeople(numberOfPeople);
            }
            else if(line.contains("<RateOfAmbulances>")){
                int rateAmbulances = Integer.valueOf(line.split(":")[1]);
                this.numberOfAmbulances = (int)((rateAmbulances*this.numberOfPeople)/100);
                this.world.setNumAmbulances(numberOfAmbulances);
            }
            else if(line.contains("<MaxPeopleNode>")){
                this.maxPeopleNode = Integer.valueOf(line.split(":")[1]);
                this.world.setMaximumNumCarPerVia(maxPeopleNode);
            }
            else if(line.contains("<RateOfMaxAmbulanceLane>")){
                int rateMaxAmb = Integer.valueOf(line.split(":")[1]);
                this.maxAmbulanceLane = (int)((rateMaxAmb*this.maxPeopleNode)/100);
                this.world.setMaximumNumAmbulancePerVia(maxAmbulanceLane);
            }
            else if(line.contains("<TimeOfSimulation>")){
                this.timeOfSimulation = Integer.valueOf(line.split(":")[1]);
                this.world.setTimeSimulation(timeOfSimulation);
            }
            else if(line.contains("<EnvironmentalLight>")){
                this.environmentalLight = Double.valueOf(line.split(":")[1]);
                this.world.streetWorld.setEnvironmentalLight(environmentalLight);
                this.environmentaLightIsSet = true;
            }
            else if(line.contains("<PercentualBrokenGlass>")){
                this.numBrokenGlass = Integer.valueOf(line.split(":")[1]);
                this.world.setBrokenGlass(numBrokenGlass);
                this.world.setSortBrokenGlass(true);
            }
            else if(line.contains("<FixedBrokenGlass>")){
                String[] listBrokenGlass = line.split(":")[1].split(",");
                this.numBrokenGlass = listBrokenGlass.length;
                this.world.setBrokenGlass(numBrokenGlass);
                this.world.setSortBrokenGlass(false);
                for(String node: listBrokenGlass){
                    String nodeName = "node"+node;
                    this.world.addNodeWithBrokenGlass(nodeName);
                }
            }
      //      System.out.println(line);
        }
 
        //liberamos o fluxo dos objetos 
        // ou fechamos o arquivo
        fileReader.close();
        bufferedReader.close();
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public int getMaxPeopleNode() {
        return maxPeopleNode;
    }

    public int getTimeOfSimulation() {
        return timeOfSimulation;
    }

    public boolean isEnvironmentaLightIsSet() {
        return environmentaLightIsSet;
    }

    public void setEnvironmentaLightIsSet(boolean environmentaLightIsSet) {
        this.environmentaLightIsSet = environmentaLightIsSet;
    }

    public int getNumBrokenGlass() {
        return numBrokenGlass;
    }

    public void setNumBrokenGlass(int numBrokenGlass) {
        this.numBrokenGlass = numBrokenGlass;
    }
    
    

   
    
    
}
