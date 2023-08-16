/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiot.test.connection;

import fiot.agents.message.quote.QuoteClient;
import fiot.general.ApplicationConfiguration;
import java.io.IOException;
import java.net.UnknownHostException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nathalia
 */
public class ConnectionTest {
    
    String namecontroller;
    String ip;
    ApplicationConfiguration configuration;
    public ConnectionTest() {
        
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        configuration = ApplicationConfiguration.getInstance();
        this.namecontroller = "Three Layer Network";
        this.configuration.setNameController(namecontroller, namecontroller);
        ip = "192.168.1.155";//"169.254.249.202"; //
        this.configuration.startMainAgents(ip, "SOCKET");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void startManagerAgent(){
      //  this.configuration.startMainAgents(ip, "SOCKET");
        String ipManager = this.configuration.getIpManager();
        assertEquals(ip, ipManager);
    }
    
    @Test
    public void startObserverAgent(){
       // this.configuration.startMainAgents(ip, "SOCKET");
        String ipObserver = this.configuration.getIpObserver();
        assertEquals(ip, ipObserver);
    }
    
    @Test
    public void detectNewDevice() throws UnknownHostException, IOException{
        String ipManager = this.configuration.getIpManager();
        int portManager = Integer.valueOf(this.configuration.getPortManager());
        QuoteClient quo = new QuoteClient();
        quo.start(ipManager, portManager);
        
        int numAdaptiveAgents = this.configuration.getAdaptiveAgentList().size();
        assertEquals(1, numAdaptiveAgents);
    }
//    @Test
//    public void createNewAdaptiveAgent(){
//        
//    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
