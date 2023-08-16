
package fiot.gui;

import fiot.agents.ManagerAgent;
import fiot.agents.InitAgent;
import fiot.agents.ObserverAgent;
import fiot.agents.controller.ControllerList;
import fiot.agents.controller.neuralnetwork.ThreeLayerNetwork;
import fiot.general.ApplicationConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import fiot.learning.Simulation;
import instance.agents.controller.ifelse.IfElseControl;



/**
 *
 * @author  Nathy
 */
public class Interface extends javax.swing.JFrame {
    
    private MainPanel mainPanel;
    PanelControl panelControl;
    ControllerList listControl = ControllerList.getInstance();
    int numNext = 0;
    public Interface() {

        initComponents();
        panelControl = PanelControl.getInstance();
        
        this.jFileChooser1.setVisible(false);

        this.setTitle("FIoT");
        
        mainPanel = new MainPanel() {

            void mouseClicado() {
            //    if(!grafosButton.isEnabled()) grafosButton.setEnabled(true);
            //    if(!reiniciarGrafo.isEnabled()) reiniciarGrafo.setEnabled(true);

            }
        };        
        jPanel1.add(mainPanel);   
        panelControl.setPanel(mainPanel);
        this.setSize(30, 30);
        this.setLocation(20, 0);
        pack();

//        origem.removeAllItems();
//        origem.addItem("Origem");
//        destino.removeAllItems();
//        destino.addItem("Destino");


    }    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator14 = new javax.swing.JToolBar.Separator();
        agentSelected = new javax.swing.JTextField();
        selectAgentToView = new javax.swing.JButton();
        viewWeightButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        selectController = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        loadAgentsConfiguration = new javax.swing.JButton();
        selectLearningMethod = new javax.swing.JComboBox();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        loadMethodConfiguration = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        startButton = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JToolBar.Separator();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("..:: NXT Ecomp ::..");
        setResizable(false);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 25));

        agentSelected.setText("nameagent");
        agentSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agentSelectedActionPerformed(evt);
            }
        });

        selectAgentToView.setText("View Agent's Controller");
        selectAgentToView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAgentToViewActionPerformed(evt);
            }
        });

        viewWeightButton.setText("View Weight");
        viewWeightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewWeightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(viewWeightButton)
                .addGap(34, 34, 34)
                .addComponent(agentSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(selectAgentToView)
                .addGap(120, 120, 120)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 217, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectAgentToView)
                    .addComponent(agentSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewWeightButton))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);
        jToolBar1.setPreferredSize(new java.awt.Dimension(13, 30));

        selectController.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Controller Type", "Three Layer Network", "If-Else Control", "State Machine" }));
        selectController.setMinimumSize(new java.awt.Dimension(20, 20));
        selectController.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectControllerActionPerformed(evt);
            }
        });
        jToolBar1.add(selectController);
        jToolBar1.add(jSeparator1);

        loadAgentsConfiguration.setBackground(new java.awt.Color(204, 204, 255));
        loadAgentsConfiguration.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        loadAgentsConfiguration.setText("Load Agents Configuration");
        loadAgentsConfiguration.setFocusable(false);
        loadAgentsConfiguration.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadAgentsConfiguration.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadAgentsConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAgentsConfigurationActionPerformed(evt);
            }
        });
        jToolBar1.add(loadAgentsConfiguration);

        selectLearningMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Learning Method", "Genetic Algorithm", "Reinforcement Learning", "No Adaptation"}));
        selectLearningMethod.setMinimumSize(new java.awt.Dimension(20, 20));
        selectLearningMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectLearningMethodActionPerformed(evt);
            }
        });
        jToolBar1.add(selectLearningMethod);
        jToolBar1.add(jSeparator4);

        loadMethodConfiguration.setBackground(new java.awt.Color(204, 0, 204));
        loadMethodConfiguration.setText("Set Method Configuration");
        loadMethodConfiguration.setFocusable(false);
        loadMethodConfiguration.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadMethodConfiguration.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadMethodConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMethodConfigurationActionPerformed(evt);
            }
        });
        jToolBar1.add(loadMethodConfiguration);
        jToolBar1.add(jSeparator6);
        jToolBar1.add(jSeparator2);

        startButton.setBackground(new java.awt.Color(255, 204, 0));
        startButton.setText("Start");
        startButton.setFocusable(false);
        startButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(startButton);
        jToolBar1.add(jSeparator12);

        jPanel3.add(jToolBar1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadAgentsConfigurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadAgentsConfigurationActionPerformed


        String selectedController = this.selectController.getSelectedItem().toString();
        
        if(selectedController.equals("Three Layer Network")){
        try {
            System.out.println("Three Layer Selected");
            File f = this.getFile();
            ThreeLayerNetwork controlLayer = new ThreeLayerNetwork(f);
            System.out.println("Controller made");
            listControl.addController(controlLayer.getNameType(), controlLayer);
            System.out.println("Controller added");
            panelControl.getPanel().setNameController(selectedController, controlLayer.getNameType());
            System.out.println("Controller painted");
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else if(selectedController.equals("If-Else Control")){
            try {
                System.out.println("If-Else Selected");
                File f = this.getFile();
                IfElseControl controlLayer = new IfElseControl(f);
                System.out.println("Controller made");
                listControl.addController(controlLayer.getNameType(), controlLayer.getIfElseControl());
                System.out.println("Controller added");
                panelControl.getPanel().setNameController(selectedController, controlLayer.getNameType());
                System.out.println("Controller painted");
            } catch (IOException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_loadAgentsConfigurationActionPerformed

    private void selectAgentToViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAgentToViewActionPerformed
        // TODO add your handling code here:
        String nameSelectedAgent = this.agentSelected.getText();
        panelControl.getPanel().setSelectedAgent(nameSelectedAgent);
        boolean draw = panelControl.getPanel().isDraw();
        if(draw)
            panelControl.getPanel().setDraw(false);
        else panelControl.getPanel().setDraw(true);
    }//GEN-LAST:event_selectAgentToViewActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        ApplicationConfiguration app = ApplicationConfiguration.getInstance();
         String ip = "169.254.249.202"; //"192.168.1.4";
        app.startMainAgents(ip, "ACL");
//        InitAgent.init(new ManagerAgent(), "MANAGER", "MANAGER");
//        InitAgent.init(new ObserverAgent(), "OBSERVER", "OBSERVER");
    }//GEN-LAST:event_startButtonActionPerformed

    private void viewWeightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewWeightButtonActionPerformed
        // TODO add your handling code here:
        panelControl.getPanel().turnViewWeight();
        
    }//GEN-LAST:event_viewWeightButtonActionPerformed

    private void loadMethodConfigurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMethodConfigurationActionPerformed
        try {
            // TODO add your handling code here:
            File fil = this.getFile();

            if(fil.getName().contains("Configuration")){
                Simulation.getInstance().setOnAdaptation(true);
                Simulation.getInstance().setSimulationConfiguration(selectLearningMethod.getSelectedItem().toString(), panelControl.getPanel().nameActivatedController,fil);
            }
            else{
             Simulation.getInstance().setOnAdaptation(false);
             Simulation.getInstance().setSimulationConfiguration(selectLearningMethod.getSelectedItem().toString(), panelControl.getPanel().nameActivatedController,fil);

             //Simulation.getInstance().setConfigurationExecution(fil);
            }
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }//GEN-LAST:event_loadMethodConfigurationActionPerformed

    private void selectLearningMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectLearningMethodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectLearningMethodActionPerformed

    private void agentSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agentSelectedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_agentSelectedActionPerformed

    private void selectControllerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectControllerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectControllerActionPerformed
        
    public File getFile() {  
//        String actualPath = "/home/txnathy/Dropbox/PUC-RIO/"
//                + "MESTRADO/PROJETO SMA E PSS/30-07";
        String actualPath = System.getProperty("user.dir"); 
    JFileChooser salvandoArquivo = new JFileChooser(actualPath);  
    int resultado = salvandoArquivo.showSaveDialog(this);  
    if (resultado != JFileChooser.APPROVE_OPTION) {  
        return null;  
    }  
  
    File selectedFile = salvandoArquivo.getSelectedFile();  
    
    return selectedFile;
}  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField agentSelected;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator14;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton loadAgentsConfiguration;
    private javax.swing.JButton loadMethodConfiguration;
    private javax.swing.JButton selectAgentToView;
    private javax.swing.JComboBox selectController;
    private javax.swing.JComboBox selectLearningMethod;
    private javax.swing.JButton startButton;
    private javax.swing.JButton viewWeightButton;
    // End of variables declaration//GEN-END:variables
    
}
